package one.codium.sensorlib.worker

import android.hardware.SensorManager
import one.codium.sensorlib.repo.data.SensorResult
import one.codium.sensorlib.data.SensorType
import one.codium.sensorlib.repo.SensorRepositoryImpl

internal class SensorWorkerFactory(private val sensorManager: SensorManager) {

    fun build(sensorType: SensorType): SensorWorker? {
        val sensor = sensorManager.getDefaultSensor(sensorType.sensorId) ?: return null
        return SensorWorkerImpl(sensorManager, sensor, sensorType, SensorRepositoryImpl(sensorType.dimension))
    }

}
