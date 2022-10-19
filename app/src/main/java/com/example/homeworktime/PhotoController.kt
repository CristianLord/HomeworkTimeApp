package com.example.homeworktime

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.core.graphics.createBitmap
import java.io.File

class PhotoController {

    fun getImageUri(context: Context, id: Long, imageView: ImageView) : Uri {
        val file = File(context.filesDir, id.toString())

        return if(file.exists()){
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setBackgroundResource(0)
            Uri.fromFile(file)
        } else {
            Uri.parse("android.resource://com.example.homeworktime/drawable/ic_icon_camera")
        }
    }

    fun deleteImage(context: Context, id: Long){
        val file = File(context.filesDir, id.toString())
        file.delete()
    }
}