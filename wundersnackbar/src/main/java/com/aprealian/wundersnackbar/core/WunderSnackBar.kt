package com.aprealian.wundersnackbar.core

import android.app.Activity
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.aprealian.wundersnackbar.style.Margin
import com.aprealian.wundersnackbar.style.Properties
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


class WunderSnackBar {

    private var snackBar: Snackbar? = null
    private var snackBarView: View? = null
    private var margin: Margin? = null

    init {
        snackBarView = snackBar?.view
        setMargin()
    }

    fun make(view: View,
             text: CharSequence,
             duration: Int = Snackbar.LENGTH_SHORT
    ): WunderSnackBar {
        //snackBar = Snackbar.make(view, text, duration);
        snackBar = with(Snackbar.make(view, text, duration)) {
            this
        }
        return this
    }

    private fun setMargin(){
        try {
            val params = snackBarView?.layoutParams as (CoordinatorLayout.LayoutParams)
            if (margin != null){
                margin?.let {
                    params.setMargins(
                        params.leftMargin + it.left,
                        params.topMargin + it.top,
                        params.rightMargin + it.right,
                        params.bottomMargin + it.bottom
                    )
                    snackBarView?.setLayoutParams(params)
                }
            }
        } catch (e: Exception){ }
    }

    fun show(){
        snackBar?.show()
    }

    fun dismiss() {
        snackBar?.dismiss()
    }

    companion object {
        internal fun instance(activity: Activity, props: Properties) =
            WunderSnackBar().apply {
                margin = props.margin
            }
    }

}