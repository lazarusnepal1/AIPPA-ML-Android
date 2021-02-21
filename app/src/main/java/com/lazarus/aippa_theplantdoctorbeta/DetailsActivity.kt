package com.lazarus.aippa_theplantdoctorbeta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.row.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class DetailsActivity : AppCompatActivity() {

//    JSON
    private val jsonFileName = "disease_description.json"
    var confPerStr: String = String()
    var diseaseNameStr: String = String()
    var diseaseNameJSON: ArrayList<String> = ArrayList()
    var causativeAgentJSON: ArrayList<String> = ArrayList()
    var causeJSON: ArrayList<String> = ArrayList()
    var symptoms1JSON: ArrayList<String> = ArrayList()
    var symptoms2JSON: ArrayList<String> = ArrayList()
    var symptoms3JSON: ArrayList<String> = ArrayList()
    var symptoms4JSON: ArrayList<String> = ArrayList()
    var symptoms5JSON: ArrayList<String> = ArrayList()

    var comments1JSON: ArrayList<String> = ArrayList()
    var comments2JSON: ArrayList<String> = ArrayList()

    var management1JSON: ArrayList<String> = ArrayList()
    var management2JSON: ArrayList<String> = ArrayList()
    var management3JSON: ArrayList<String> = ArrayList()
    var management4JSON: ArrayList<String> = ArrayList()

    //    ML
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
        predictionConfidence = bundle?.getString("prediction_confidence", "Unknown %") ?: ""
//        pictureCapture = bundle?.getByteArray("pictureCapture")!!

        confPerStr = predictionConfidence
        diseaseNameStr = diseaseName

//        Read JSON formatted file and write it into classified ArrayList format
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager
        try {
            val obj = JSONObject(loadJSONFromAsset())
            val diseaseArray = obj.getJSONArray("disease")
            for (i in 0 until diseaseArray.length()){
                val diseaseDetail = diseaseArray.getJSONObject(i)
                if(diseaseDetail.getString("name")==diseaseNameStr){
                    diseaseNameJSON.add(diseaseDetail.getString("name"))
                    causativeAgentJSON.add(diseaseDetail.getString("causative_agent"))
                    causeJSON.add(diseaseDetail.getString("cause"))

                    val symptomsDetails = diseaseDetail.getJSONObject("symptoms")
                    symptoms1JSON.add(symptomsDetails.getString("1"))
                    symptoms2JSON.add(symptomsDetails.getString("2"))
                    symptoms3JSON.add(symptomsDetails.getString("3"))
                    symptoms4JSON.add(symptomsDetails.getString("3"))
                    symptoms5JSON.add(symptomsDetails.getString("5"))

                    val commentsDetails = diseaseDetail.getJSONObject("comments")
                    comments1JSON.add(commentsDetails.getString("1"))
                    comments2JSON.add(commentsDetails.getString("2"))

                    val managementDetails = diseaseDetail.getJSONObject("management")
                    management1JSON.add(managementDetails.getString("1"))
                    management2JSON.add(managementDetails.getString("2"))
                    management3JSON.add(managementDetails.getString("3"))
                    management4JSON.add(managementDetails.getString("4"))
                    break
                }
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }

//        send data for dynamic view via CustomAdapter constructor
        val customAdapter = CustomAdapter(this@DetailsActivity,
                diseaseNameJSON, diseaseNameStr,  confPerStr, causativeAgentJSON, causeJSON, symptoms1JSON,
        symptoms2JSON, symptoms3JSON, symptoms4JSON, symptoms5JSON, comments1JSON, comments2JSON,
        management1JSON, management2JSON, management3JSON, management4JSON)
        recyclerView.adapter = customAdapter
    }

    private fun loadJSONFromAsset(): String{
        val json: String?
        try {
            val inputStream = assets.open(jsonFileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (ex: IOException){
            ex.printStackTrace()
            return ""
        }
        return json
    }

    fun goHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
        }
        startActivity(intent)
    }
}