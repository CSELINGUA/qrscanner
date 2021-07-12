package com.example.AttendanceSystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Student (
    var id:String = "",
    var first_name : String = "",
    val last_name : String = "",
    val matric_number : String = "",
    val full_matric_number :String ="",
    val level : String = "",
    val dept : String = "",
    val gender : String = ""
) : Parcelable
