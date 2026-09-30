package com.example.engine3d

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vector3D(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f
) {
    operator fun plus(other: Vector3D) = Vector3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3D) = Vector3D(x - other.x, y - other.y, z - other.z)
    operator fun unaryMinus() = Vector3D(-x, -y, -z)
    operator fun times(scalar: Float) = Vector3D(x * scalar, y * scalar, z * scalar)
    operator fun div(scalar: Float) = Vector3D(x / scalar, y / scalar, z / scalar)

    fun length(): Float = sqrt(x * x + y * y + z * z)

    fun normalized(): Vector3D {
        val len = length()
        return if (len > 0.00001f) this / len else Vector3D(0f, 0f, 1f)
    }

    fun dot(other: Vector3D): Float = x * other.x + y * other.y + z * other.z

    fun rotateX(angleRad: Float): Vector3D {
        val c = cos(angleRad)
        val s = sin(angleRad)
        return Vector3D(
            x = x,
            y = y * c - z * s,
            z = y * s + z * c
        )
    }

    fun rotateY(angleRad: Float): Vector3D {
        val c = cos(angleRad)
        val s = sin(angleRad)
        return Vector3D(
            x = x * c + z * s,
            y = y,
            z = -x * s + z * c
        )
    }

    fun rotateZ(angleRad: Float): Vector3D {
        val c = cos(angleRad)
        val s = sin(angleRad)
        return Vector3D(
            x = x * c - y * s,
            y = x * s + y * c,
            z = z
        )
    }

    fun rotateEuler(pitch: Float, yaw: Float, roll: Float = 0f): Vector3D {
        var v = this
        if (roll != 0f) v = v.rotateZ(roll)
        if (pitch != 0f) v = v.rotateX(pitch)
        if (yaw != 0f) v = v.rotateY(yaw)
        return v
    }
}

data class ProjectedPoint(
    val screenX: Float,
    val screenY: Float,
    val depthZ: Float, // Larger value = closer to viewer, or camera space Z
    val scaleFactor: Float,
    val isVisible: Boolean
)

object ProjectionEngine {
    /**
     * Projects a 3D point in world space into 2D screen coordinates with perspective.
     * @param worldPos 3D coordinate in space
     * @param cameraPitch Camera inclination angle (radians)
     * @param cameraYaw Camera azimuth angle (radians)
     * @param cameraDistance Virtual distance of camera from origin
     * @param fov Focal perspective multiplier (e.g. 800f)
     * @param viewportWidth Screen width in px
     * @param viewportHeight Screen height in px
     * @param centerOffset 3D offset to center on (e.g. focus on planet)
     */
    fun project(
        worldPos: Vector3D,
        cameraPitch: Float,
        cameraYaw: Float,
        cameraDistance: Float,
        fov: Float = 800f,
        viewportWidth: Float,
        viewportHeight: Float,
        centerOffset: Vector3D = Vector3D(0f, 0f, 0f)
    ): ProjectedPoint {
        // Shift relative to focus target
        val shifted = worldPos - centerOffset

        // Apply camera rotation (Yaw around Y, Pitch around X)
        val rotatedYaw = shifted.rotateY(cameraYaw)
        val viewSpace = rotatedYaw.rotateX(cameraPitch)

        // Translate along Z by camera distance
        val zCam = viewSpace.z + cameraDistance

        // Perspective clipping: If behind camera or too close
        if (zCam < 50f) {
            return ProjectedPoint(0f, 0f, zCam, 0f, false)
        }

        val perspective = fov / zCam
        val screenX = (viewportWidth / 2f) + (viewSpace.x * perspective)
        val screenY = (viewportHeight / 2f) + (viewSpace.y * perspective)

        return ProjectedPoint(
            screenX = screenX,
            screenY = screenY,
            depthZ = -viewSpace.z, // Higher depth = closer to viewer
            scaleFactor = perspective,
            isVisible = true
        )
    }
}
