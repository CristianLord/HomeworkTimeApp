package com.example.homeworktime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class ColorPickerDialog(private val listener: (colorHex:String) -> Unit) : DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.rounded_corners)
        return inflater.inflate(R.layout.color_picker, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog!!.window?.setLayout(width,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.findViewById<ImageView>(R.id.imgColor1).setOnClickListener{changeColor("#FFEB3B")}
        dialog!!.findViewById<ImageView>(R.id.imgColor2).setOnClickListener{ changeColor("#FFFF4444") }
        dialog!!.findViewById<ImageView>(R.id.imgColor3).setOnClickListener{ changeColor("#FFAA66CC") }
        dialog!!.findViewById<ImageView>(R.id.imgColor4).setOnClickListener{ changeColor("#FF0099CC") }
        dialog!!.findViewById<ImageView>(R.id.imgColor5).setOnClickListener{ changeColor("#3FB45D") }
        dialog!!.findViewById<ImageView>(R.id.imgColor6).setOnClickListener{ changeColor("#5D4037") }
        dialog!!.findViewById<ImageView>(R.id.imgColor7).setOnClickListener{ changeColor("#FFFF8800") }
        dialog!!.findViewById<ImageView>(R.id.imgColor8).setOnClickListener{ changeColor("#FF4081") }

    }

    private fun changeColor(color:String){
        listener.invoke(color)
        dismiss()
    }
}