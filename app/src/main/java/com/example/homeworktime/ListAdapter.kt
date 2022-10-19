package com.example.homeworktime

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ListAdapter (private val context: Context, private val elements:List<Reminder>, private val onClickListener:(Reminder) -> Unit):RecyclerView.Adapter<ListAdapter.Holder>() {

    private var reminders = elements

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.list_element, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.render(context, reminders[position], onClickListener)
    }

    override fun getItemCount(): Int = reminders.size

    fun setData(reminders:List<Reminder>){
        this.reminders = reminders
        notifyDataSetChanged()
    }

    class Holder(private val view: View):RecyclerView.ViewHolder(view){

        fun render(context: Context, element: Reminder, onClickListener:(Reminder) -> Unit){
            view.findViewById<TextView>(R.id.txtTitle).text = element.title
            view.findViewById<ImageView>(R.id.imgMatter).setColorFilter(Color.parseColor(element.colorMatter), PorterDuff.Mode.SRC_IN)
            view.findViewById<TextView>(R.id.txtMatter).text = element.course
            view.findViewById<TextView>(R.id.txtDescription).text = element.description
            view.findViewById<TextView>(R.id.txtDate).text = element.date
            val photo = view.findViewById<ImageView>(R.id.imgPhoto)
            photo.setImageURI(
                PhotoController().getImageUri(context, element.idReminder.toLong(), photo))
            /*val bitmapImage = BitmapFactory.decodeFile(
                PhotoController().getImageUri(context, element.idReminder.toLong(), photo).toString())
            val nh = (bitmapImage.height * (512.0 / bitmapImage.width)).toInt()
            val scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true)
            photo.setImageBitmap(scaled)*/
            itemView.setOnClickListener{ onClickListener(element) }
        }

    }

}