package one.codium.sensorlib.repo.data

data class SensorResult(
    val medianResults: FloatArray,
    val averageResults: FloatArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SensorResult) return false

        if (!medianResults.contentEquals(other.medianResults)) return false
        if (!averageResults.contentEquals(other.averageResults)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = medianResults.contentHashCode()
        result = 31 * result + averageResults.contentHashCode()
        return result
    }
}
