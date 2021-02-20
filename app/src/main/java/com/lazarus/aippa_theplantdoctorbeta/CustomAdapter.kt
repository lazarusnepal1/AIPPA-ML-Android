package com.lazarus.aippa_theplantdoctorbeta

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
        private var context: Context,
        private var diseaseNameJSON: ArrayList<String>,
        private var diseaseNameString: String,
        private var confPerString: String,
        private var causativeAgentJSON: ArrayList<String>,
        private var causeJSON: ArrayList<String>,
        private var symptoms1JSON: ArrayList<String>,
        private var symptoms2JSON: ArrayList<String>,
        private var symptoms3JSON: ArrayList<String>,
        private var symptoms4JSON: ArrayList<String>,
        private var symptoms5JSON: ArrayList<String>,

        private var comments1JSON: ArrayList<String>,
        private var comments2JSON: ArrayList<String>,

        private var management1JSON: ArrayList<String>,
        private var management2JSON: ArrayList<String>,
        private var management3JSON: ArrayList<String>,
        private var management4JSON: ArrayList<String>
):
RecyclerView.Adapter<CustomAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomAdapter.MyViewHolder, position: Int) {
        Log.d("if condition:", "\n prediction: $diseaseNameString \n JSON:" +diseaseNameJSON[position])

            holder.diseaseNameTV.text = diseaseNameJSON[position].toUpperCase()
            holder.diseaseN.text = diseaseNameString.toUpperCase()
            holder.confPer.text = confPerString
            holder.causativeAgentTV.text = causativeAgentJSON[position]
            holder.causeTV.text = causeJSON[position]

            holder.symptoms1TV.text = symptoms1JSON[position]
            holder.symptoms2TV.text = symptoms2JSON[position]
            holder.symptoms3TV.text = symptoms3JSON[position]
            holder.symptoms4TV.text = symptoms4JSON[position]
            holder.symptoms5TV.text = symptoms5JSON[position]

            holder.comments1TV.text = comments1JSON[position]
            holder.comments2TV.text = comments2JSON[position]

            holder.management1TV.text = management1JSON[position]
            holder.management2TV.text = management2JSON[position]
            holder.management3TV.text = management3JSON[position]
            holder.management4TV.text = management4JSON[position]

            holder.itemView.setOnClickListener(){
                Toast.makeText(context, diseaseNameJSON[position], Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return diseaseNameJSON.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var diseaseNameTV: TextView = itemView.findViewById<View>(R.id.tvDiseaseName) as TextView
        var diseaseN: TextView = itemView.findViewById<View>(R.id.tvDiseaseName) as TextView
        var confPer: TextView = itemView.findViewById<View>(R.id.tvConfidencePer) as TextView
        var causativeAgentTV: TextView = itemView.findViewById<View>(R.id.tvCausativeAgent) as TextView
        var causeTV: TextView = itemView.findViewById<View>(R.id.tvCause) as TextView
        var symptoms1TV: TextView = itemView.findViewById<View>(R.id.tvSymptoms1) as TextView
        var symptoms2TV: TextView = itemView.findViewById<View>(R.id.tvSymptoms2) as TextView
        var symptoms3TV: TextView = itemView.findViewById<View>(R.id.tvSymptoms3) as TextView
        var symptoms4TV: TextView = itemView.findViewById<View>(R.id.tvSymptoms4) as TextView
        var symptoms5TV: TextView = itemView.findViewById<View>(R.id.tvSymptoms5) as TextView
        var comments1TV: TextView = itemView.findViewById<View>(R.id.tvComments1) as TextView
        var comments2TV: TextView = itemView.findViewById<View>(R.id.tvComments2) as TextView
        var management1TV: TextView = itemView.findViewById<View>(R.id.tvManagement1) as TextView
        var management2TV: TextView = itemView.findViewById<View>(R.id.tvManagement2) as TextView
        var management3TV: TextView = itemView.findViewById<View>(R.id.tvManagement3) as TextView
        var management4TV: TextView = itemView.findViewById<View>(R.id.tvManagement4) as TextView

    }

}