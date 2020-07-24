package com.aprealian.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aprealian.wundersnackbar.core.WunderSnackBar
import com.aprealian.wundersnackbar.style.Position
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testSnackBar()
    }

    private fun testSnackBar() {
        val wunderSnackBar = WunderSnackBar(this).make(coordinatorLayout, "WiFi change detected; updating information...", position = Position.BOTTOM)
        wunderSnackBar.show()
    }
}
