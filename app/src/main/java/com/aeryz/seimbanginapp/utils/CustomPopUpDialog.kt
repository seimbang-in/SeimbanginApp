package com.aeryz.seimbanginapp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.aeryz.seimbanginapp.R
import com.airbnb.lottie.LottieAnimationView

fun customPopupDialog(
    context: Context,
    type: Int,
    successMessage: String?,
    errorMessage: String?,
    onContinueClick: ((Dialog) -> Unit)? = null
) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(R.layout.custom_layout_popup)
    val window = dialog.window
    val layoutParams = window?.attributes
    layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
    layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
    window?.attributes = layoutParams
    val animationView = dialog.findViewById<LottieAnimationView>(R.id.animation_view)
    val title = dialog.findViewById<TextView>(R.id.tv_title)
    val description = dialog.findViewById<TextView>(R.id.tv_description)
    val button = dialog.findViewById<Button>(R.id.btn_continue)
    if (type == 1) {
        animationView.setAnimation(R.raw.animation_success)
        title.text = context.getString(R.string.text_success)
        description.text = successMessage
        button.setOnClickListener {
            dialog.dismiss()
            onContinueClick?.invoke(dialog)
        }
    } else {
        animationView.setAnimation(R.raw.animation_error)
        title.text = context.getString(R.string.text_failed)
        title.setTextColor(ContextCompat.getColor(context, R.color.error_500))
        description.text = errorMessage
        button.backgroundTintList =
            ContextCompat.getColorStateList(context, R.color.error_500)
        button.setOnClickListener {
            dialog.dismiss()
        }
    }
    dialog.show()
}
