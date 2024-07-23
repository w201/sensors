package one.codium.sensorlib.worker

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import one.codium.sensorlib.repo.data.SensorResult
import one.codium.sensorlib.repo.SensorRepositoryImpl

internal class SensorWorkerImpl(
    private val sensorManager: SensorManager,
    private val sensor: Sensor,
    val sensorRepository: SensorRepositoryImpl,
    private val resultCallback: (SensorResult) -> Unit
) : SensorWorker {

    private val listener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                sensorRepository.addValue(System.currentTimeMillis(), event.values)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // maybe need to add some handler for this case
        }

    }

    override fun start() {
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun stop() {
        sensorManager.unregisterListener(listener)
    }

    override fun actionStarted() {
        sensorRepository.startEvent(System.currentTimeMillis())
    }

    override fun actionStopped() {
        resultCallback.invoke(sensorRepository.stopEvent())
    }

}
