package com.lazarus.aippa_theplantdoctorbeta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private var title = "df"
    private var diseaseName = "df"
    private var predictionConfidence = "10 %"
//    private val pictureCapture =

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val bundle = intent.extras
        title = bundle?.getString("titleN", "Title") ?: ""
        diseaseName = bundle?.getString("diseaseName", "Disease Name") ?: ""
        predictionConfidence = bundle?.getString("prediction_confidence", "Prediction Confidence") ?: ""

        Log.d("(Activity Details :: predicted Result)", "\n Name: $diseaseName \n Confidence: $predictionConfidence")
//        diseaseNameTV.text = diseaseName
        diseaseNameTV.text = diseaseName
        predictConfidenceTV.text = predictionConfidence
    }
}