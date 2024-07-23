package one.codium.sensorlib

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.SensorManager
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import one.codium.sensorlib.data.SensorType
import one.codium.sensorlib.worker.SensorWorker
import one.codium.sensorlib.worker.SensorWorkerFactory
import one.codium.sensorlib.worker.data.WorkerResult

class SensorLib(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    private val view: View,
    private val errorCallback: (String) -> Unit,
    private val result: (WorkerResult) -> Unit
) : DefaultLifecycleObserver {

    private var sensorManager: SensorManager

    private val workers = mutableListOf<SensorWorker>()

    private var owner: LifecycleOwner? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val factory = SensorWorkerFactory(sensorManager)

        val magnetWorker = factory.build(SensorType.MAGNET) {
            result.invoke(WorkerResult(SensorType.MAGNET.sensorName, it))
        }

        if (magnetWorker != null) {
            workers.add(magnetWorker)
        } else {
            // notify host app that this sensor is unavailable if need such kind of functionality
            errorCallback.invoke("Magnet sensor not found")
        }

        factory.build(SensorType.AXEL) {
            result.invoke(WorkerResult(SensorType.AXEL.sensorName, it))
        }?.let {
            workers.add(it)
        }

        factory.build(SensorType.GRAVITY) {
            result.invoke(WorkerResult(SensorType.GRAVITY.sensorName, it))
        }?.let {
            workers.add(it)
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        this.owner = owner
        start()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        this.owner = null
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun start() {
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_CANCEL -> {
                    workers.forEach { it.actionStarted() }
                }

                MotionEvent.ACTION_UP -> {
                    workers.forEach { it.actionStopped() }
                }
            }
            false
        }
        workers.forEach { it.start() }
    }

    private fun stop() {
        workers.forEach { it.stop() }
        view.setOnTouchListener(null)
    }

}
