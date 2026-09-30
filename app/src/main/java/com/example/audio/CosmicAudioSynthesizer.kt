package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.*
import kotlin.math.PI
import kotlin.math.sin

/**
 * Procedural Space Sound Synthesizer
 * Generates ambient cosmic frequencies, deep space void hum, and pulsar rhythms
 * entirely in software using AudioTrack with zero permissions and zero audio asset files.
 */
class CosmicAudioSynthesizer {

    private var audioTrack: AudioTrack? = null
    private var synthJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    var isPlaying: Boolean = false
        private set

    var volume: Float = 0.5f
        set(value) {
            field = value.coerceIn(0f, 1f)
            audioTrack?.setVolume(field)
        }

    fun start() {
        if (isPlaying) return
        isPlaying = true

        val sampleRate = 22050
        val bufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ).coerceAtLeast(4096)

        try {
            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()

            audioTrack?.setVolume(volume)
            audioTrack?.play()

            synthJob = scope.launch {
                val samples = ShortArray(1024)
                var phase1 = 0.0
                var phase2 = 0.0
                var phasePulsar = 0.0
                val freqDrone1 = 55.0 // Low A sub-bass cosmic hum
                val freqDrone2 = 110.0 // Overtone harmonic
                val samplePeriod = 1.0 / sampleRate

                var tickCount = 0
                val pulsarPeriodTicks = (sampleRate * 0.75).toInt() // Pulsar click rhythm

                while (isActive && isPlaying) {
                    for (i in samples.indices) {
                        // Ambient cosmic sine drone
                        val s1 = sin(phase1 * 2.0 * PI)
                        val s2 = sin(phase2 * 2.0 * PI) * 0.4

                        // Pulsar rhythmic chirp click
                        tickCount++
                        var pulsarClick = 0.0
                        if (tickCount >= pulsarPeriodTicks) {
                            tickCount = 0
                            phasePulsar = 0.0
                        }
                        if (phasePulsar < 0.05) {
                            pulsarClick = sin(phasePulsar * 2.0 * PI * 880.0) * (1.0 - phasePulsar / 0.05) * 0.35
                            phasePulsar += samplePeriod
                        }

                        // Combine layers with smooth cosmic reverb feel
                        val combined = (s1 * 0.45 + s2 * 0.25 + pulsarClick) * 0.4
                        samples[i] = (combined * Short.MAX_VALUE).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()

                        phase1 += freqDrone1 * samplePeriod
                        if (phase1 > 1.0) phase1 -= 1.0
                        phase2 += freqDrone2 * samplePeriod
                        if (phase2 > 1.0) phase2 -= 1.0
                    }

                    audioTrack?.write(samples, 0, samples.size)
                }
            }
        } catch (_: Exception) {
            isPlaying = false
        }
    }

    fun stop() {
        isPlaying = false
        synthJob?.cancel()
        synthJob = null
        try {
            audioTrack?.pause()
            audioTrack?.flush()
            audioTrack?.stop()
            audioTrack?.release()
        } catch (_: Exception) {}
        audioTrack = null
    }

    fun toggle(): Boolean {
        if (isPlaying) {
            stop()
        } else {
            start()
        }
        return isPlaying
    }
}
