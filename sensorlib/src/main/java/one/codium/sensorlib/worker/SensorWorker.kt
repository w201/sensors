package one.codium.sensorlib.worker

import one.codium.sensorlib.data.SensorType
import one.codium.sensorlib.repo.data.SensorValue

internal interface SensorWorker {
    val sensorType: SensorType
    fun start()
    fun stop()
    fun actionStarted()
    fun actionStopped(): List<SensorValue>
}
