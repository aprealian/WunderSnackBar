package com.aprealian.wundersnackbar.core

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.aprealian.wundersnackbar.R
import com.aprealian.wundersnackbar.`interface`.IWunderSnackBar
import com.aprealian.wundersnackbar.style.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class WunderSnackBar(val activity: Activity) {

    private var callback: IWunderSnackBar? = null
    private var snackBar: Snackbar? = null
    private var snackBarView: View? = null
    private var properties: Properties = Properties()
    private var listenerNeutral: (()->Unit)? = null

    init {
        snackBarView = snackBar?.view
    }

    fun make(
        view: View,
        drawable: Int? = null,
        text: String,
        secondText: String? = null,
        buttonText: String? = "OK",
        position: Int = Position.BOTTOM,
        duration: Int = Duration.SHORT,
        size: Size? = null,
        margin: Margin = Margin(),
        padding: Padding = Padding(),
        listenerNeutral: (()->Unit)? = null,
        callback: IWunderSnackBar? = null
    ): WunderSnackBar {

        //init SnackBar
        snackBar = Snackbar.make(view, "", duration)
        this.callback = callback
        this.listenerNeutral = listenerNeutral

        // init properties
        properties.text = text
        properties.secondText = secondText
        properties.buttonText = buttonText
        properties.drawable = drawable
        properties.position = position
        properties.size = size
        properties.margin = margin
        properties.padding = padding


        //this code will resolve animation issue, thanks to: https://stackoverflow.com/questions/31746300/how-to-show-snackbar-at-top-of-the-screen/58183630#58183630
        snackBar?.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

        //background
        //snackBar?.view?.background = ContextCompat.getDrawable(activity, R.drawable.snackbar_shape) // min API 16
        snackBar?.view?.background = null //remove background

        //custom layout
        customLayout()

        //set margin
        setMargin()

        return this
    }

    private fun customLayout() {
        // Get the Snackbar's layout view
        val layout = snackBar?.view as (Snackbar.SnackbarLayout)? ?: return
        layout.setPadding(0, 0, 0, 0)

        // Hide the text
        val textViewSnack = layout.findViewById<View>(R.id.snackbar_text) as TextView
        textViewSnack.visibility = View.INVISIBLE

        // Inflate our custom view
        val mInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val snackView: View = mInflater.inflate(R.layout.my_snackbar, null)
        val imageView: ImageView = snackView.findViewById<View>(R.id.image) as ImageView
        val textView = snackView.findViewById<View>(R.id.text) as TextView
        val textViewSecond = snackView.findViewById<View>(R.id.textSecond) as TextView
        val buttonNeutral = snackView.findViewById<View>(R.id.buttonNeutral) as TextView
        val imageClose = snackView.findViewById<View>(R.id.imageClose) as ImageView

        //set text/image
        textView.text = properties.text
        buttonNeutral.text = properties.buttonText
        properties.secondText?.let {
            textViewSecond.text = properties.secondText
            textViewSecond.visibility = View.VISIBLE
        } ?:run {
            textViewSecond.visibility = View.GONE
        }
        properties.drawable?.let {
            imageView.setImageResource(it)
        }

        //listener
        buttonNeutral.setOnClickListener {
            callback?.onButtonClick()
            listenerNeutral?.run {
                dismiss()
                invoke()
            }
        }

        imageClose.setOnClickListener {
            dismiss()
        }

        // Add the view to the Snackbar's layout
        val snackLayout: Snackbar.SnackbarLayout = snackBar?.view as Snackbar.SnackbarLayout
        val params  = snackLayout.layoutParams as FrameLayout.LayoutParams
        properties.size?.let {
            params.height = it.width!!
            params.width = it.height!!
        }

        if (properties.position == Position.TOP){
            params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        } else {
            params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        }

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0, params)

        secondLayout()
    }

    private fun secondLayout() {
        //val textViewSecond = snackBar.view.findViewById<View>(R.id.textSecond) as TextView
        var isOpen: Boolean = false
        val layout = snackBar?.view?.findViewById<View>(R.id.layout2) as RelativeLayout
        val buttonOpen = snackBar?.view?.findViewById<View>(R.id.buttonOpen) as ImageView

        buttonOpen.setOnClickListener {
            isOpen = !isOpen
            buttonOpen.setImageResource(if (isOpen) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
            //layout.visibility = if (isOpen) View.VISIBLE else View.GONE

            layout.animate()
                .translationY(if (isOpen) 0f else layout.height.toFloat())
                .alpha(if (isOpen) 1.0f else 0.0f)
                .setDuration(if (isOpen) 0 else 1000)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        //layout.visibility = View.GONE
                        layout.visibility = if (isOpen) View.VISIBLE else View.GONE
                    }
                })
        }
    }

    private fun setMargin(){
        properties.margin.let {
            //val cardView = snackBar?.view?.findViewById<CardView>(R.id.cardView)
            val cardView = snackBar?.view?.findViewById<RelativeLayout>(R.id.mainLayout)
            cardView?.apply {
                val params = layoutParams as ViewGroup.MarginLayoutParams
                params.setMargins(pxFromDp(activity, it.left), pxFromDp(activity, it.top), pxFromDp(activity, it.right), pxFromDp(activity, it.bottom))
                cardView.requestLayout()
            }
        }

        properties.padding.let {
            val container = snackBar?.view?.findViewById<RelativeLayout>(R.id.container)
            container?.setPadding(pxFromDp(activity, it.left), pxFromDp(activity, it.top), pxFromDp(activity, it.right), pxFromDp(activity, it.bottom))
        }
    }

    fun show(){
        snackBar?.show()

        if (properties.position == Position.TOP){
            Animation.animateOut(snackBar)
        } else {
            Animation.animateIn(snackBar)
        }
    }

    fun dismiss() {
        if (properties.position == Position.TOP){
            Animation.animateOut(snackBar)
        } else {
            Animation.animateIn(snackBar)
        }

        snackBar?.dismiss()
    }

}