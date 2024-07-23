package one.codium.sensorlib.worker.data

import one.codium.sensorlib.repo.data.SensorResult

data class WorkerResult(
    val sensor: String,
    val sensorResult: SensorResult
)
