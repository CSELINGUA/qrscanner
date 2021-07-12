package com.example.AttendanceSystem.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.fragment.app.FragmentManager
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class MyImageAnalyzer(private val fragmentManager: FragmentManager, private val course:String) : ImageAnalysis.Analyzer {
    private var bottomSheet = BarcodeResultBottomSheet()
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun analyze(imageProxy: ImageProxy) {
        scanBarcode(imageProxy)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("UnsafeExperimentalUsageError")
    private fun scanBarcode(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(inputImage)
                .addOnCanceledListener {
                    if (bottomSheet.isAdded)
                        bottomSheet.dismiss()
                }
                .addOnFailureListener {
                    if (bottomSheet.isAdded)
                        bottomSheet.dismiss()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                    if (it.isSuccessful) {
                        readBarcodeData(it.result as List<Barcode>)
                    } else {
                        it.exception?.printStackTrace()
                        if (bottomSheet.isAdded)
                            bottomSheet.dismiss()
                    }
                }
        }
    }

    private fun readBarcodeData(barcodes: List<Barcode>) {
        for (barcode in barcodes) {
            when (barcode.valueType) {
                Barcode.TYPE_URL -> {
                    if (!bottomSheet.isAdded)
                        bottomSheet.show(fragmentManager, "")
                    bottomSheet.updateMatricNumber(barcode.url?.url.toString(),course,bottomSheet)
                }
                Barcode.TYPE_TEXT -> {
                    if (!bottomSheet.isAdded)
                        bottomSheet.show(fragmentManager, "")
                    bottomSheet.updateMatricNumber(barcode.displayValue.toString(),course,bottomSheet)// .url?.url.toString())
                }
            }
        }
    }
}