package com.example.AttendanceSystem.utils

import android.app.Dialog
import android.graphics.LightingColorFilter
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.AttendanceSystem.R
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.progress_indicator.*
import java.io.File

//import com.github.doyaaaaaken.kotlincsv.dsl.csvReader


open class BaseActivity: AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var mChoiceDialog: Dialog
    lateinit var students: List<String>
    var isImported :Boolean = false

    fun showSnackBar(message: String, isError: Boolean) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        if (isError) {
            snackBarView.setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.snack_bar_error))
        } else {
            snackBarView.setBackgroundColor(ContextCompat.getColor(this@BaseActivity, R.color.snack_bar_success))
        }
        snackBar.setTextColor(ContextCompat.getColor(this@BaseActivity, R.color.white))
        snackBar.show()
    }

    fun showProgressDialog(text: String) {
        mChoiceDialog = Dialog(this)
        mChoiceDialog.window?.decorView?.background?.colorFilter = LightingColorFilter(
            0xFF000000.toInt(),
            0xFFffffff.toInt()
        )
        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mChoiceDialog.setContentView(R.layout.progress_indicator)
        mChoiceDialog.tv_progress_text.text = text

        mChoiceDialog.setCancelable(false)
        mChoiceDialog.setCanceledOnTouchOutside(false)
        mChoiceDialog.show()
    }



    fun hideProgressDialog() {
        mChoiceDialog.dismiss()
    }

    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, resources.getString(R.string.please_click_back_again_to_exit), Toast.LENGTH_SHORT).show()
        @Suppress("DEPRECATION")
        Handler().postDelayed({ this.doubleBackToExitPressedOnce = false }, 2000)
    }

    fun csvReadFile(){

        val file: File = File("C:\\Users\\CSEJOE\\Documents\\KOTLINprograms\\DATA\\student.csv")
        val rows: List<Map<String, String>> = csvReader().readAllWithHeader(file)
        println(rows) //[{a=d, b=e, c=f}]
        Toast.makeText(this,rows.toString(),Toast.LENGTH_SHORT).show()


        /*val header = csvReader.readNext()
        var line: Array<String> = csvReader.readNext()
        students.plus(line[0])

        while (line != null) {
            line = csvReader.readNext()
            students.plus(line[0])
        }
        isImported = true
*/
    }

}