package com.example.AttendanceSystem.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.AttendanceSystem.R
import com.example.AttendanceSystem.activities.FullDetailsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BarcodeResultBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_barcode_data, container, false)



    fun updateMatricNumber(matricNumber: String,course:String,bottomSheet: BarcodeResultBottomSheet) {
        //fetchUrlMetaData(url) { title, desc ->
            //view?.apply {s
        view?.findViewById<TextView>(R.id.text_view_title)?.text = "Matric"
        view?.findViewById<TextView>(R.id.text_view_desc)?.text = "number"
        view?.findViewById<TextView>(R.id.text_view_link)?.text = matricNumber

        view?.findViewById<TextView>(R.id.text_view_visit_link)?.setOnClickListener { _ ->
            bottomSheet.dismiss()
            val intent = Intent (context, FullDetailsActivity::class.java)
            intent.putExtra("matric",matricNumber)
            intent.putExtra("course",course)
            startActivity(intent)

            /*Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse(matricNumber)
                startActivity(it)*/
        }
    //                }

    }

}


//    private fun fetchUrlMetaData(
//        url: String,
//        callback: (title: String, desc: String) -> Unit
//    ) {
//        val executor = Executors.newSingleThreadExecutor()
//        val handler = Handler(Looper.getMainLooper())
//        executor.execute {
//            val doc = Jsoup.connect(url).get()
//            val desc = doc.select("meta[name=description]")[0].attr("content")
//            handler.post {
//                callback(doc.title(), desc)
//            }
//        }
//    }

