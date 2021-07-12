package com.example.AttendanceSystem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Attendant(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val businessName: String = "",
    val shotLocation: String = "",
    val gender: String ="",
    val phone: String ="",
    val imageUrl: String? = null,
    val profileCompleted: Boolean = false
) : Parcelable