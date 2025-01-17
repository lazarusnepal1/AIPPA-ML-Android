package com.lazarus.aippa_theplantdoctorbeta

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap

    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2

    private val mInputSize = 224
    private val mModelPath = "plant_disease_model.tflite"
    private val mLabelPath = "plant_labels.txt"
    private val mSamplePath = "test_leaf.jpg"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        resources.assets.open(mSamplePath).use {
            mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            leafImageView.setImageBitmap(mBitmap)
        }
//        functional buttons
        fab_camera.setOnClickListener{
//            Log.d("\n camera!", "Camera button was clicked!\n")
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(callCameraIntent, mCameraRequestCode)
        }
        fab_gallery.setOnClickListener{
//            Log.d("\n clicked!", "Gallery button was clicked!\n")
            val callGalleryIntent = Intent(Intent.ACTION_PICK)
            callGalleryIntent.type = "image/*"
            startActivityForResult(callGalleryIntent, mGalleryRequestCode)
        }
        detect_btn.setOnClickListener{
//            Log.d("\n clicked!", "Detect button was clicked!\n")
            val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
//            predictedTextView.text= "Name: "+results?.title+"\n Confidence: "+confidencePer
//            Log.d("predicted Result", "\n Disease Name: " + results.title + "\n Confidence: " + confidencePer + " %")

//            if prediction result is empty process  could not be processed
            if (results != null) {
                val confidencePer = 100* results?.confidence!!
                val intentDetailsActivity = Intent(this, DetailsActivity::class.java).apply{
                    putExtra("titleN", "Prediction Result")
                    putExtra("diseaseName", results.title)
                    putExtra("prediction_confidence", "(Prediction Confidence $confidencePer %)")
                //  putExtra("pictureCapture", bitmapData)
                }
                Toast.makeText(this, "Predicting started", Toast.LENGTH_SHORT).show()
                startActivity(intentDetailsActivity)
            } else{
                val toast = Toast.makeText(this, "leaf not found", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }

        fab_help.setOnClickListener(){
            val intent = Intent(this, AboutActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == mCameraRequestCode){
            // Consider the case of the canceled camera
            if(resultCode == Activity.RESULT_OK && data != null) {
                mBitmap = data.extras!!.get("data") as Bitmap
                mBitmap = scaleImage(mBitmap)
                val toast = Toast.makeText(
                    this,
                    ("Image crop to: w= ${mBitmap.width} h= ${mBitmap.height}"),
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.BOTTOM, 0, 20)
                toast.show()
                leafImageView.setImageBitmap(mBitmap)
                predictedTextView.text= "Your photo image set now."
            } else {
                Toast.makeText(this, "Camera cancel..", Toast.LENGTH_LONG).show()
            }
        } else if(requestCode == mGalleryRequestCode) {
            if (data != null) {
                val uri = data.data

                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                println("Success!!!")
                mBitmap = scaleImage(mBitmap)
                leafImageView.setImageBitmap(mBitmap)
            }
        } else {
            Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_LONG).show()
        }
    }

    fun scaleImage(bitmap: Bitmap?): Bitmap {
        val orignalWidth = bitmap!!.width
        val originalHeight = bitmap.height
        val scaleWidth = mInputSize.toFloat() / orignalWidth
        val scaleHeight = mInputSize.toFloat() / originalHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, orignalWidth, originalHeight, matrix, true)
    }
}