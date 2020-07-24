package com.aprealian.wundersnackbar.core

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.aprealian.wundersnackbar.R
import com.aprealian.wundersnackbar.style.Margin
import com.aprealian.wundersnackbar.style.Position
import com.aprealian.wundersnackbar.style.Properties
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class WunderSnackBar(val activity: Activity) {

    private var snackBar: Snackbar? = null
    private var snackBarView: View? = null
    private var margin: Margin? = null
    private var position: Int? = Position.BOTTOM

    init {
        snackBarView = snackBar?.view
        //setMargin()
    }

    fun make(view: View,
             text: CharSequence,
             position: Int = Position.BOTTOM,
             duration: Int = Snackbar.LENGTH_SHORT
    ): WunderSnackBar {

        this.position = position


        snackBar = Snackbar.make(view, text, duration)


        //this code will resolve animation issue, thanks to: https://stackoverflow.com/questions/31746300/how-to-show-snackbar-at-top-of-the-screen/58183630#58183630
        snackBar?.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

        //background
        //snackBar?.view?.background = ContextCompat.getDrawable(activity, R.drawable.snackbar_shape) // min API 16
        snackBar?.view?.background = null //remove background

        //custom layout
        customLayout(position)

        //set margin
        setMargin()

        return this
    }

    private fun customLayout(position: Int) {
        // Get the Snackbar's layout view
        val layout = snackBar?.view as (Snackbar.SnackbarLayout)? ?: return

        // Hide the text
        val textView = layout.findViewById<View>(R.id.snackbar_text) as TextView
        textView.visibility = View.INVISIBLE

        // Inflate our custom view
        val mInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val snackView: View = mInflater.inflate(R.layout.my_snackbar, null)
        val imageView: ImageView = snackView.findViewById<View>(R.id.image) as ImageView
        imageView.setImageResource(android.R.drawable.ic_btn_speak_now)
        val textViewTop = snackView.findViewById<View>(R.id.text) as TextView

        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0, 0, 0, 0)

        // Add the view to the Snackbar's layout
        val snackLayout: Snackbar.SnackbarLayout = snackBar?.view as Snackbar.SnackbarLayout
        val params  = snackLayout.layoutParams as FrameLayout.LayoutParams
        //params.height = 500
        //params.width = 500

        if (position == Position.TOP){
            params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        } else {
            params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        }


        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0, params)
    }

    private fun setMargin(){

        margin?.let {

        }

        val cardView = snackBar?.view?.findViewById<CardView>(R.id.cardView)
        cardView?.apply {
            val params = layoutParams as ViewGroup.MarginLayoutParams
            //params.setMargins(10, 10, 10, 100)
            cardView.requestLayout()
        }

    }

    fun show(){
        snackBar?.show()

        if (position == Position.TOP){
            animationOut()
        } else {
            animationIn()
        }
    }

    private fun animationIn() {
        var slide = TranslateAnimation(0F, 0F, 0F, (- snackBar?.view?.height!!).toFloat())
        slide.duration = 300
        slide.fillAfter = true
        snackBar?.view?.startAnimation(slide)
        slide = TranslateAnimation(0F, 0F, 100F, 0F) // seems you have a problem with 3rd param

        slide.duration = 1000
        slide.fillAfter = true
        snackBar?.view?.startAnimation(slide)
    }

    private fun animationOut() {
        var slide = TranslateAnimation(0F, 0F, 0F, 0F)
        slide.duration = 300
        slide.fillAfter = true
        snackBar?.view?.startAnimation(slide)
        slide = TranslateAnimation(0F, 0F, -100F, 0F) // seems you have a problem with 3rd param

        slide.duration = 1000
        slide.fillAfter = true
        snackBar?.view?.startAnimation(slide)
    }

    fun dismiss() {
        snackBar?.dismiss()
    }

    companion object {
        internal fun instance(activity: Activity, props: Properties) =
            WunderSnackBar(activity).apply {
                margin = props.margin
            }
    }

}