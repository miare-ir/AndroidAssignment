package ir.miare.androidcodechallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.miare.androidcodechallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }
}
