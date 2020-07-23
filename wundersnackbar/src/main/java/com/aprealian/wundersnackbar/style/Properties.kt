package com.aprealian.wundersnackbar.style

internal data class Properties (
    var title: String? = null,
    var description: String? = null,
    var position: Int? = Position.BOTTOM,
    var margin: Margin = Margin()
)

object Position {
    val BOTTOM = 0
    val TOP = 1
}

data class Margin(val bottom: Int = 0, val left: Int = 0, val right: Int = 0, val top: Int = 0){

}



