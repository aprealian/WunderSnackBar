package com.aprealian.wundersnackbar.style

import com.aprealian.wundersnackbar.R
import com.google.android.material.snackbar.Snackbar

internal data class Properties (
    var text: String? = null,
    var secondText: String? = null,
    var buttonText: String? = "OK",
    var drawable: Int? = null,
    var position: Int? = Position.BOTTOM,
    var margin: Margin = Margin(),
    var padding: Padding = Padding(),
    var size: Size? = null
)

object Position {
    val BOTTOM = 0
    val TOP = 1
}

object Duration {
    val SHORT = Snackbar.LENGTH_SHORT
    val LONG = Snackbar.LENGTH_LONG
    val INFINITE = Snackbar.LENGTH_INDEFINITE
}

object Icon {
    val ERROR = R.drawable.ic_status_error
    val WARNING = R.drawable.ic_status_warning
}

data class Size(val width:Int? = null, val height:Int? = null)

data class Margin(val bottom: Int = 10, val left: Int = 10, val right: Int = 10, val top: Int = 10)

data class Padding(val bottom: Int = 16, val left: Int = 16, val right: Int = 16, val top: Int = 16)



