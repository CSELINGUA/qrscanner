package com.example.AttendanceSystem.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.AttendanceSystem.R
import com.example.AttendanceSystem.utils.BaseActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_preparation.*


class PreparationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparation)

        bt_launch_scanners.setOnClickListener {

//            val file = File("C:\\Users\\CSEJOE\\Documents\\KOTLINprograms\\DATA\\book.csv")
//            val rows: List<List<String>> = csvReader().readAll(file)
            //val rows: List<Map<String, String>> = csvReader().readAllWithHeader(file)


            //println(rows) //[{a=d, b=e, c=f}]
//            Toast.makeText(this,rows.toString(),Toast.LENGTH_SHORT).show()


//            csvReadFile()

//            for(doc in students){
//                Toast.makeText(this,doc,Toast.LENGTH_SHORT).show()
//
//            }
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
}
