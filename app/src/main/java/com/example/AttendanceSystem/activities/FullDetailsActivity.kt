package com.example.AttendanceSystem.activities

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.AttendanceSystem.R
import com.example.AttendanceSystem.firebase.FireStoreStudentClass
import com.example.AttendanceSystem.models.Attendance
import com.example.AttendanceSystem.models.Student
import com.example.AttendanceSystem.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_full_details.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FullDetailsActivity : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_details)
        val currentDateTime = LocalDateTime.now()

        if (intent.hasExtra("matric")) {
            val matric: String = intent.extras?.get("matric").toString()!!
            val course: String = intent.extras?.get("course").toString()!!
            tv_course.text = course
            fetchStudent(matric)
            val date = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))

            bt_mark_attendance.setOnClickListener {
                markAttendance(Attendance(matric_number = matric, date = date, course = course))
            }
        }
    }

    private fun markAttendance(attendance: Attendance) {
        showProgressDialog("Recording attendance")
        FireStoreStudentClass().markAttendance(this,attendance)
    }

    private fun fetchStudent(matric: String) {
        showProgressDialog("Fetching details")
        FireStoreStudentClass().fetchStudent(this,matric)
    }
    fun fetchStudentSuccess(student: Student) {
        hideProgressDialog()
        tv_mat_number.text = student.full_matric_number
        tv_gender.text = student.gender
        tv_name.text = """${student.last_name} ${student.first_name}"""
        tv_level.text = student.level
        tv_dept.text = student.dept
    }

    fun fetchStudentFailure(error: String) {
        hideProgressDialog()
        Toast.makeText(this,error, Toast.LENGTH_LONG).show()
    }

    fun addStudentSuccess() {
        hideProgressDialog()
        bt_mark_attendance.isClickable = true
        Toast.makeText(this,"Student added successfully", Toast.LENGTH_SHORT).show()
    }

    fun addStudentFailure(toString: String) {
        hideProgressDialog()
        Toast.makeText(this,toString, Toast.LENGTH_SHORT).show()
    }

    fun addAttendanceSuccess() {
        hideProgressDialog()
        Toast.makeText(this,"Attendance successfully recorded", Toast.LENGTH_SHORT).show()
    }

    fun addAttendanceFailure(toString: String) {
        hideProgressDialog()
        Toast.makeText(this,toString, Toast.LENGTH_SHORT).show()
    }
}