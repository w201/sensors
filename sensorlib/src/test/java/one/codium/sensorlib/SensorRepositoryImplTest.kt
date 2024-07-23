package one.codium.sensorlib

import one.codium.sensorlib.repo.SensorRepositoryImpl
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class SensorRepositoryImplTest {

    private val repo = SensorRepositoryImpl(2)
    @Before
    fun setUp() {
        repo.addValue(5L,floatArrayOf(3.5f, 5.6f))
        repo.addValue(6L,floatArrayOf(3.6f, 0.8f))
        repo.addValue(7L,floatArrayOf(3.7f, 1.8f))
        repo.addValue(17L,floatArrayOf(3.9f, 2.8f))
        repo.addValue(27L,floatArrayOf(4.2f, 5.3f))
        repo.addValue(54L,floatArrayOf(6.3f, 1.2f))
    }

    @Test
    fun test() {
        repo.startEvent(67)
        repo.addValue(68, floatArrayOf(4.3f, 5.2f))
        val result = repo.stopEvent()
        assertEquals(4.25f, result.medianResults[0])
        assertEquals(4f, result.medianResults[1])
        assertEquals(4.675f, result.averageResults[0])
        assertEquals(3.625f, result.averageResults[1])
    }
}
