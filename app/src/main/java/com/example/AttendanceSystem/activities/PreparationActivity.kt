package com.example.AttendanceSystem.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.AttendanceSystem.R
import com.example.AttendanceSystem.models.Attendance
import com.example.AttendanceSystem.utils.BaseActivity
import com.example.AttendanceSystem.utils.HelperClass
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_preparation.*
import kotlinx.android.synthetic.main.attendance_view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList

@RequiresApi(Build.VERSION_CODES.M)
class PreparationActivity : AppCompatActivity() {

    var pageHeight = 1120
    var pagewidth = 792
    lateinit var bmp: Bitmap
    var scaledbmp: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparation)

        bt_launch_scanners.setOnClickListener {
            if(validateAttendance()){
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("course",static_spinner.selectedItem.toString())
                startActivity(intent)
            }
        }
        val staticAdapter = ArrayAdapter
            .createFromResource(this, R.array.courses_array, android.R.layout.simple_spinner_item
            )
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        static_spinner.adapter = staticAdapter


        static_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                position: Int, id: Long
            ) {
                Toast.makeText(applicationContext,parent.getItemAtPosition(position) as String,Toast.LENGTH_SHORT).show()
                Log.v("item", parent.getItemAtPosition(position) as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { // TODO Auto-generated method stub
            }
        }

        /*bmp = BitmapFactory.decodeResource(resources, android.R.drawable.ic_input_add)
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)
*/
        // below code is used for
        // checking our permissions.
        if (!checkPermission())
            requestPermission()
        fab_generate_pdf.setOnClickListener{
            val intent = Intent(this,HelperClass::class.java)
            startActivity(intent)
            //generatePDF()
        }
    }
    private fun validateAttendance() : Boolean {
        return when {
            static_spinner.selectedItem.toString() == "Select" ->{
                Snackbar.make(static_spinner,"Select a course",Snackbar.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
    private fun generatePDF() {
        Toast.makeText(this,"generating pdf",Toast.LENGTH_SHORT).show()
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val title = Paint()

        val mypageInfo = PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create()
        val myPage = pdfDocument.startPage(mypageInfo)
        val canvas = myPage.canvas
        canvas.drawBitmap(scaledbmp!!, 56f, 40f, paint)

        title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        title.textSize = 15f
        title.color = ContextCompat.getColor(this, android.R.color.black)
        canvas.drawText("A portal for IT professionals.", 209f, 100f, title)
        canvas.drawText("Geeks for Geeks", 209f, 80f, title)

        title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        title.color = ContextCompat.getColor(this, android.R.color.black)
        title.textSize = 15f

        title.textAlign = Paint.Align.CENTER
        canvas.drawText("This is sample document which we have created.", 396f, 560f, title)
        pdfDocument.finishPage(myPage)
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "GFG.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(
                this,
                "PDF file generated successfully.",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {

            e.printStackTrace()
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()
    }

    private fun checkPermission(): Boolean {
        // checking of permissions.
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }


    companion object {
        private const val PERMISSION_REQUEST_CODE = 200
    }
}