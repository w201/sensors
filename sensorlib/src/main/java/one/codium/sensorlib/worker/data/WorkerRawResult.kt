package one.codium.sensorlib.worker.data

import one.codium.sensorlib.data.SensorType
import one.codium.sensorlib.repo.data.SensorValue

internal data class WorkerRawResult(
    val sensorType: SensorType,
    val rawData: List<SensorValue>
)
