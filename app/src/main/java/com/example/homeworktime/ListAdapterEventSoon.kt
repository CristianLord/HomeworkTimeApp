package com.example.homeworktime

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapterEventSoon(private val elements:List<Reminder>) : RecyclerView.Adapter<ListAdapterEventSoon.Holder>() {

    private var reminders = elements

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.list_element_event_soon, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(reminders[position])
    }

    override fun getItemCount(): Int = reminders.size

    class Holder(private val view: View): RecyclerView.ViewHolder(view){

        fun render(element: Reminder){
            view.findViewById<TextView>(R.id.txtTitle).text = element.title
            view.findViewById<ImageView>(R.id.imgMatter).setColorFilter(Color.parseColor(element.colorMatter), PorterDuff.Mode.SRC_IN)
            view.findViewById<TextView>(R.id.txtDate).text = element.date
            itemView.setOnClickListener{
                view.context.startActivity(Intent(view.context, InfoActivity::class.java)
                .putExtra("id",element.idReminder)) }
        }

    }

}