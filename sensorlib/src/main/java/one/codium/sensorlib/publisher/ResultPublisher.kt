package one.codium.sensorlib.publisher

import android.util.Log
import one.codium.sensorlib.worker.data.WorkerResult

class ResultPublisher() {

    suspend fun publishResults(data: List<WorkerResult>) {
        data.forEach {
            Log.d("w201", "Sensor: ${it.sensor}")
            Log.d("w201", "   median: ${it.sensorResult.medianResults.joinToString { it.toString() }}")
            Log.d("w201", "   average: ${it.sensorResult.averageResults.joinToString { it.toString() }}")
        }
    }

}
