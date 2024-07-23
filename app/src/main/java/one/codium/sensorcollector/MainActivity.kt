package one.codium.sensorcollector

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import one.codium.sensorcollector.databinding.ActivityMainBinding
import one.codium.sensorlib.SensorLib

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btnClickMe.setOnClickListener {
            Toast.makeText(applicationContext, "Hello World!", Toast.LENGTH_LONG).show()
        }
        SensorLib(this, this, binding.btnClickMe) {}
    }
}
