package com.aprealian.wundersnackbar.style

import android.view.View
import android.view.animation.TranslateAnimation
import com.google.android.material.snackbar.Snackbar

object Animation {

    fun animateIn(snackBar: Snackbar?){
        var slide = TranslateAnimation(0F, 0F, 0F, (- snackBar?.view?.height!!).toFloat())
        slide.duration = 300
        slide.fillAfter = true
        snackBar.view.startAnimation(slide)
        slide = TranslateAnimation(0F, 0F, 100F, 0F) // seems you have a problem with 3rd param

        slide.duration = 1000
        slide.fillAfter = true
        snackBar.view.startAnimation(slide)
    }

    fun animateOut(snackBar: Snackbar?) {
        var slide = TranslateAnimation(0F, 0F, 0F, 0F)
        slide.duration = 300
        slide.fillAfter = true
        snackBar?.view?.startAnimation(slide)
        slide = TranslateAnimation(0F, 0F, -100F, 0F) // seems you have a problem with 3rd param

        slide.duration = 1000
        slide.fillAfter = true
        snackBar?.view?.startAnimation(slide)
    }
}