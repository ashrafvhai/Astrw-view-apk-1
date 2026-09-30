package com.example.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.atan2

data class DeviceOrientationAngles(
    val yawRad: Float = 0f,
    val pitchRad: Float = 0f,
    val rollRad: Float = 0f
)

/**
 * Tracks device motion in 3D physical space using Rotation Vector / Accelerometer sensors.
 * Enables users to hold up their phone to look around the 3D celestial sphere.
 */
class CosmicOrientationSensor(context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
    private val rotationSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        ?: sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION)

    private val _orientationAngles = MutableStateFlow(DeviceOrientationAngles())
    val orientationAngles: StateFlow<DeviceOrientationAngles> = _orientationAngles.asStateFlow()

    private val rotationMatrix = FloatArray(9)
    private val adjustedMatrix = FloatArray(9)
    private val orientationOutput = FloatArray(3)

    var isTracking: Boolean = false
        private set

    fun startTracking(): Boolean {
        if (sensorManager == null || rotationSensor == null) return false
        if (isTracking) return true
        val registered = sensorManager.registerListener(
            this,
            rotationSensor,
            SensorManager.SENSOR_DELAY_GAME
        )
        isTracking = registered
        return isTracking
    }

    fun stopTracking() {
        if (!isTracking) return
        sensorManager?.unregisterListener(this)
        isTracking = false
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            SensorManager.remapCoordinateSystem(
                rotationMatrix,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                adjustedMatrix
            )
            SensorManager.getOrientation(adjustedMatrix, orientationOutput)

            val yaw = orientationOutput[0] // Azimuth
            val pitch = orientationOutput[1] // Pitch
            val roll = orientationOutput[2] // Roll

            _orientationAngles.value = DeviceOrientationAngles(
                yawRad = yaw,
                pitchRad = pitch.coerceIn(-1.5f, 1.5f),
                rollRad = roll
            )
        } else if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
            val yaw = Math.toRadians(event.values[0].toDouble()).toFloat()
            val pitch = Math.toRadians(event.values[1].toDouble()).toFloat()
            _orientationAngles.value = DeviceOrientationAngles(
                yawRad = yaw,
                pitchRad = pitch.coerceIn(-1.5f, 1.5f)
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No-op
    }
}
