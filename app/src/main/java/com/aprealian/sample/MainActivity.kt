package com.aprealian.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aprealian.wundersnackbar.`interface`.IWunderSnackBar
import com.aprealian.wundersnackbar.core.WunderSnackBar
import com.aprealian.wundersnackbar.style.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testSnackBar()
    }

    private fun testSnackBar() {
        WunderSnackBar(this).make(
            coordinatorLayout,
            Icon.ERROR,
            "WiFi change detected; updating information...",
            buttonText = "OKAY",
            position = Position.BOTTOM,
            duration = Duration.INFINITE,
            listenerNeutral = {
                Toast.makeText(this, "dismiss", Toast.LENGTH_LONG).show()
            }).also {
            it.show()
        }
    }
}
