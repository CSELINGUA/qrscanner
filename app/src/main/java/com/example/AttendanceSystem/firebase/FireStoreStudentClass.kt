package com.example.AttendanceSystem.firebase

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.AttendanceSystem.activities.FullDetailsActivity
import com.example.AttendanceSystem.activities.PreparationActivity
import com.example.AttendanceSystem.models.Attendance
import com.example.AttendanceSystem.models.Student
import com.example.AttendanceSystem.utils.Constants
import com.example.AttendanceSystem.utils.HelperClass
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreStudentClass {

    private val mFireStoreStudentClass = FirebaseFirestore.getInstance()

    fun addStudent(activity: /*AddStudentActivity*/FullDetailsActivity, student: Student) {
        mFireStoreStudentClass.collection(Constants.STUDENTS).document()
            .set(student, SetOptions.merge()).addOnSuccessListener {
                activity.addStudentSuccess()
            }.addOnFailureListener { e ->
                activity.addStudentFailure(e.message.toString())
            }
    }

    fun fetchStudent(activity: Activity, matric: String) {
        mFireStoreStudentClass.collection(Constants.STUDENTS)
            .whereEqualTo(Constants.MATRIC_NUMBER, matric).get()
            .addOnSuccessListener { snapshot ->
                val docs = snapshot.documents
                val students = ArrayList<Student>()
                for (doc in docs) {
                    val student = doc.toObject(Student::class.java)
                    student!!.id = doc.id
                    students.add(student)
                }
                if(students.isNotEmpty()){
                    when (activity) {
                        is FullDetailsActivity -> {
                            activity.fetchStudentSuccess(students[0]!!)
                        }
                    }

                }
                else{
                    when (activity) {
                        is FullDetailsActivity -> {
                            activity.fetchStudentFailure("No such student exits")
                        }
                    }
                }

            }.addOnFailureListener { e ->
                when (activity) {
                    is FullDetailsActivity -> {
                        activity.fetchStudentFailure(e.message.toString())
                    }
                }
            }
    }

    fun markAttendance(activity: FullDetailsActivity, attendance: Attendance) {
        mFireStoreStudentClass.collection(Constants.ATTENDANCE).document()
            .set(attendance, SetOptions.merge()).addOnSuccessListener {
                activity.addAttendanceSuccess()
            }.addOnFailureListener { e ->
                activity.addAttendanceFailure(e.message.toString())
            }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun fetchAttendance(activity: Activity){
        mFireStoreStudentClass.collection(Constants.ATTENDANCE).get()
            .addOnSuccessListener {
                val docs =it.documents
                val attendance = ArrayList<Attendance>()
                if (docs.isNotEmpty())
                for (doc in docs){
                    val att = doc.toObject(Attendance::class.java)
                    attendance.add(att!!)
                }
                if (attendance.isNotEmpty())
                when(activity){
                    is HelperClass -> {
                        activity.fetchAttendanceSuccess(attendance)
                    }
                }
                else
                    when(activity){
                        is HelperClass -> {
                            activity.fetchAttendanceFailure("ATTENDANCE DATABASE IS EMPTY")
                        }
                    }

            }
            .addOnFailureListener{
                when(activity){
                    is HelperClass -> {
                        activity.fetchAttendanceFailure(it.message.toString())
                    }
                }
            }
    }
}