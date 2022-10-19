package com.example.homeworktime

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Reminder data model.
 */
@Entity(tableName = "Reminders")
data class Reminder(
    val title:String,
    val course:String,
    val colorMatter:String,
    val description:String,
    val date:String,
    var status:Int,
    @PrimaryKey(autoGenerate = true)
    var idReminder: Int = 0
    ) : Serializable
