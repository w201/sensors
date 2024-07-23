package one.codium.sensorlib.worker

internal interface SensorWorker {
    fun start()
    fun stop()
    fun actionStarted()
    fun actionStopped()
}
