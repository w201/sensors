package one.codium.sensorlib.repo

import one.codium.sensorlib.repo.data.SensorResult

internal interface SensorRepository {
    fun addValue(timestamp: Long, values: FloatArray)
    fun startEvent(currentTimestamp: Long)
    fun stopEvent(): SensorResult
}
