package one.codium.sensorlib.data

import android.hardware.Sensor

internal enum class SensorType(val sensorId: Int, val sensorName: String, val dimension: Int) {
    MAGNET(Sensor.TYPE_MAGNETIC_FIELD, "Magnet Sensor", 3),
    AXEL(Sensor.TYPE_ACCELEROMETER, "Accelerometer Sensor", 3),
    GRAVITY(Sensor.TYPE_GRAVITY, "Gravity Sensor", 3),
}
