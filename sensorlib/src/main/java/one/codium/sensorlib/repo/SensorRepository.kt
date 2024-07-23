package one.codium.sensorlib.repo

import one.codium.sensorlib.repo.data.SensorResult
import one.codium.sensorlib.repo.data.SensorValue

internal interface SensorRepository {
    fun addValue(timestamp: Long, values: FloatArray)
    fun startEvent(currentTimestamp: Long)
    fun stopEvent(): List<SensorValue>
}
