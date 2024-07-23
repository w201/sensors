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

    private fun getMedianResult(): FloatArray {
        synchronized(data) {
            val result = FloatArray(dimension)
            for (i in 0 until dimension) {
                val sortedArray = data.sortedBy { it.values[i] }
                if (sortedArray.size % 2 != 0) {
                    // median value will be middle element of sorted array
                    result[i] = sortedArray[sortedArray.size / 2].values[i]
                } else {
                    // median value calculated as average of two middle elements
                    if(sortedArray.isNotEmpty()) {
                        result[i] = ((sortedArray[sortedArray.size / 2 - 1].values[i]) +
                                sortedArray[sortedArray.size / 2].values[i]) / 2
                    }
                }
            }
            return result
        }
    }

    private fun getAverageResult(): FloatArray {
        synchronized(data) {
            val result = FloatArray(dimension)
            for (i in 0 until dimension) {
                val averageValue = data.sumOf { it.values[i].toDouble() } / data.size
                result[i] = averageValue.toFloat()
            }
            return result
        }
    }

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

    override fun stopEvent(): SensorResult {
        synchronized(data) {
            val result = SensorResult(getMedianResult(), getAverageResult())
            resetData()
            return result
        }
    }

    private fun resetData() {
        synchronized(data) {
            data.clear()
            trimCounter = trimDataCounterInitialValue
            isEventStarted = AtomicBoolean(false)
        }
    }

}
