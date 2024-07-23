package one.codium.sensorlib.statistic

import one.codium.sensorlib.repo.data.SensorValue

internal class SensorStatisticImpl : SensorStatistic {

    override fun getMedian(data: List<SensorValue>, dimension: Int): FloatArray {
        val result = FloatArray(dimension)
        for (i in 0 until dimension) {
            val sortedArray = data.sortedBy { it.values[i] }
            if (sortedArray.size % 2 != 0) {
                // median value will be middle element of sorted array
                result[i] = sortedArray[sortedArray.size / 2].values[i]
            } else {
                // median value calculated as average of two middle elements
                if (sortedArray.isNotEmpty()) {
                    result[i] = ((sortedArray[sortedArray.size / 2 - 1].values[i]) +
                            sortedArray[sortedArray.size / 2].values[i]) / 2
                }
            }
        }
        return result
    }

    override fun getAvg(data: List<SensorValue>, dimension: Int): FloatArray {
        val result = FloatArray(dimension)
        for (i in 0 until dimension) {
            val averageValue = data.sumOf { it.values[i].toDouble() } / data.size
            result[i] = averageValue.toFloat()
        }
        return result
    }

}
