package com.lazarus.aippa_theplantdoctorbeta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val jsonFileName = "disease_description.json"
    private var title = "df"
    private var diseaseName = "df"
    private var predictionConfidence = "10 %"
//    private var pictureCapture = byteArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val bundle = intent.extras
        title = bundle?.getString("titleN", "Title") ?: ""
        diseaseName = bundle?.getString("diseaseName", "Disease Name") ?: ""
        predictionConfidence = bundle?.getString("prediction_confidence", "Prediction Confidence") ?: ""
//        pictureCapture = bundle?.getByteArray("pictureCapture")!!

        Log.d("(Activity Details :: predicted Result)", "\n Name: $diseaseName \n Confidence: $predictionConfidence")
//        diseaseNameTV.text = diseaseName
        diseaseNameTV.text = diseaseName
        predictConfidenceTV.text = predictionConfidence
//        imgCaptureIV.setImageBitmap(pictureCapture)
    }
}