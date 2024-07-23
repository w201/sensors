package one.codium.sensorlib.repo

import one.codium.sensorlib.beforeEventRecordingDuration
import one.codium.sensorlib.repo.data.SensorResult
import one.codium.sensorlib.repo.data.SensorValue
import one.codium.sensorlib.trimDataCounterInitialValue
import java.util.concurrent.atomic.AtomicBoolean

internal class SensorRepositoryImpl(private val dimension: Int) : SensorRepository {

    private var isEventStarted = AtomicBoolean(false)

    private val data = ArrayList<SensorValue>(500)

    private var trimCounter = trimDataCounterInitialValue

    override fun addValue(timestamp: Long, values: FloatArray) {
        synchronized(data) {
            val newSensorValue = SensorValue(timestamp, values.clone())
            if (isEventStarted.get()) {
                data.add(newSensorValue.copy())
            } else {
                if (trimCounter <= 1) {
                    trimCounter = trimDataCounterInitialValue
                    trimData(timestamp)
                }
                trimCounter--
                data.add(newSensorValue)
            }
        }
    }

    private fun trimData(currentTimestamp: Long) {
        data.removeIf { currentTimestamp - it.timestamp > beforeEventRecordingDuration }
    }

    override fun startEvent(currentTimestamp: Long) {
        synchronized(data) {
            trimData(currentTimestamp)
            isEventStarted.set(true)
        }
    }

    override fun stopEvent(): List<SensorValue> {
        synchronized(data) {
            val result = data.toList()
            resetData()
            return result
        }
    }

    private fun resetData() {
        data.clear()
        trimCounter = trimDataCounterInitialValue
        isEventStarted = AtomicBoolean(false)
    }

}
