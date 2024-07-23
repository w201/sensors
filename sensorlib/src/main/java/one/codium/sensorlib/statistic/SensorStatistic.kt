package one.codium.sensorlib.statistic

import one.codium.sensorlib.repo.data.SensorValue

internal interface SensorStatistic {
    fun getMedian(data: List<SensorValue>, dimension: Int): FloatArray
    fun getAvg(data: List<SensorValue>, dimension: Int): FloatArray
}
