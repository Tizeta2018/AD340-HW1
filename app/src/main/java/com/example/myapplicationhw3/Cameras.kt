package com.example.myapplicationhw3

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.squareup.picasso.Picasso
import com.android.volley.toolbox.Volley
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest


class Cameras(val description: String, private val image: String, var type: String, var coords: DoubleArray) : AppCompatActivity()  {
    private val baseUrl: Map<String, String> = mapOf(
        "sdot" to "https://www.seattle.gov/trafficcams/images",
        "wsdot" to "https://images.wsdot.wa.gov/nw/"
    )
     fun imageUrl() : String {
        return baseUrl[this.type] +this.image
    }

    private lateinit var camList: ListView
   var camURL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomID13&type=2"
    private lateinit var camadapter: camAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_cameras)
        checkNetworkConnection()

        val camData: MutableList<Cameras> = ArrayList()

        val queue = Volley.newRequestQueue(this)
        val camadapter = camAdapter(camData)
        val tcList = findViewById<RecyclerView>(R.id.TrafficCams)
        tcList.adapter =camadapter
        tcList.layoutManager = LinearLayoutManager(applicationContext)
        tcList.setHasFixedSize(true)
        
        val jsonRequest = JsonObjectRequest(Method.GET,camURL, null, {
            response ->


            val locations= response.getJSONArray("Features")
            for (i in 0 until locations.length()){
               val point = locations.getJSONObject(i)
                val pointCoords = point.getJSONArray("PointCoordinate")
                val cameras = point.getJSONArray("Cameras").getJSONObject(0)
                val cam = Cameras (
                    cameras.getString("Destription"),
                    cameras.getString("ImageURL"),
                    cameras.getString("Type"),
                    doubleArrayOf(pointCoords.getDouble(0), pointCoords.getDouble(1))


                )
                camData.add(cam)
            }
            camadapter.notifyDataSetChanged()

        }) { error -> Log.d("JSON","Error:" + error.message)}

        queue.add(jsonRequest)
     
       

        val actionbar = supportActionBar
        actionbar!!.title = "Traffic Cameras"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun checkNetworkConnection() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val currentNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
        val linkProperties = connectivityManager.getLinkProperties(currentNetwork)
        val status = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        if (status == true) {


        } else {
            Toast.makeText(applicationContext, "No Internet Connection!", Toast.LENGTH_SHORT).show()
        }

    }
}
private class camAdapter(private var camData: MutableList<Cameras>):
    RecyclerView.Adapter<camAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var camIntersection: TextView = itemView.findViewById(R.id.intersection)
        var camPreview: ImageView = itemView.findViewById(R.id.campreview)
        val camera = camData[adapterPosition]

        val imageUrl = camera.imageUrl()






    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): camAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val camView = inflater.inflate(R.layout.traffic_list, parent, false)
        return ViewHolder(camView)
    }

    override fun onBindViewHolder(holder: camAdapter.ViewHolder, position: Int) {
        val trafficcam = camData[position]
        val intersection = holder.camIntersection
        val livecam = holder.camPreview

        intersection.text = camData[position].description
        holder.camPreview.contentDescription =intersection.text


        Picasso.get().load(imageUrl).into(livecam)


        }

    override fun getItemCount(): Int {
        return camData.count()
    }









}