package com.example.homeworktime

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class SaveActivity : AppCompatActivity() {

    private lateinit var txtTitle:EditText
    private lateinit var txtDescription:EditText
    private lateinit var txtCourse:EditText
    private lateinit var txtDate:TextView
    private lateinit var calendar:ImageButton
    private lateinit var colorMatter:ImageView
    private lateinit var photo:ImageButton
    private var idReminder:Int? = null
    private var colorMatterString: String = "#000000"
    private var imageReminder: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)
        init()
    }

    private val database = AppDatabase.getDatabase(this)

    private fun init(){
        txtTitle = findViewById(R.id.txtTitle)
        txtDescription = findViewById(R.id.txtDescription)
        txtCourse = findViewById(R.id.txtCourse)
        colorMatter = findViewById(R.id.imgColor)
        txtDate = findViewById(R.id.txtDate)
        calendar = findViewById(R.id.btnDate)
        photo = findViewById(R.id.btnPhoto)
        findViewById<Button>(R.id.btnSave).setOnClickListener {mSaveData()}
        findViewById<Button>(R.id.btnCancel).setOnClickListener {finish()}
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {finish()}
        txtDate.setOnClickListener { showDialogDatePicker() }
        calendar.setOnClickListener { showDialogDatePicker() }
        colorMatter.setOnClickListener{showColorDialog()}
        photo.setOnClickListener{requestPermission()}
        loadData()
    }

    private fun loadData(){

        if(intent.hasExtra("reminder")){
            findViewById<TextView>(R.id.actionTitle).text = "Editar tarea"
            val reminder = intent.extras?.getSerializable("reminder") as Reminder
            txtTitle.setText(reminder.title)
            txtCourse.setText(reminder.course)
            colorMatter.setColorFilter(Color.parseColor(reminder.colorMatter))
            txtDate.text = reminder.date
            txtDescription.setText(reminder.description)
            colorMatterString = reminder.colorMatter
            idReminder = reminder.idReminder
            photo.setImageURI(
                PhotoController().getImageUri(this, reminder.idReminder.toLong(), photo))
        }
    }

    private fun showColorDialog(){
        ColorPickerDialog {
            colorMatterString = it
            colorMatter.setColorFilter(Color.parseColor(colorMatterString),
                PorterDuff.Mode.SRC_IN)}.show(supportFragmentManager, "customDialog")
    }

    private fun showDialogDatePicker(){
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day:Int, month:Int, year:Int){
        txtDate.text = "$day/$month/$year"
    }

    private fun requestPermission(){

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                selectPicture()
            }
            else -> requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){
            selectPicture()
        }else{
            Toast.makeText(this, "Se necesita habilitar los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    private val startActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->

        if(result.resultCode == Activity.RESULT_OK){
            imageReminder = result.data?.data
            photo.setImageURI(imageReminder)
            photo.setPadding(0)
            photo.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun selectPicture(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityGallery.launch(intent)
    }

    private fun saveImage(uri: Uri, id:Long){
        val file = File(this.filesDir, id.toString())
        val bytes = this.contentResolver.openInputStream(uri)?.readBytes()!!
        file.writeBytes(bytes)
    }

    private fun mSaveData(){

        val oReminder = Reminder(
            txtTitle.text.toString(),
            txtCourse.text.toString(),
            colorMatterString,
            txtDescription.text.toString(),
            txtDate.text.toString(),
            0)

        if(idReminder != null){
            CoroutineScope(Dispatchers.IO).launch {
                oReminder.idReminder = idReminder!!
                database.reminders().update(oReminder)
                imageReminder?.let { saveImage(it, oReminder.idReminder.toLong()) }
            }
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                val id = database.reminders().insertAll(oReminder)[0]
                imageReminder?.let { saveImage(it, id) }
            }
        }

        this@SaveActivity.finish()
    }
}