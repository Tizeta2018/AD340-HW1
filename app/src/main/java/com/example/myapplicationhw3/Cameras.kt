package com.example.myapplicationhw3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.squareup.picasso.Picasso
import com.android.volley.toolbox.Volley
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import okhttp3.internal.Util
import org.json.JSONException
import java.util.ArrayList


class Cameras : AppCompatActivity(){
    var TrafficCams: ListView? = null
    var listAdapter: TrafficListAdapter? = null
    var camUrl = Cam.camUrl
    var TrafficData: MutableList<Cam> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_cameras)

        TrafficCams = findViewById(R.id.TrafficCams)
        listAdapter = TrafficListAdapter(TrafficData)
        TrafficCams?.setAdapter(listAdapter)

        val actionbar = supportActionBar
        actionbar!!.title = "Traffic Cameras"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val currentNetwork = connectivityManager.getActiveNetwork()
        val caps = connectivityManager.getNetworkCapabilities(currentNetwork)
        if (NetCap.hasNetworkConnection(this)) {
            TrafficData(camUrl)
        } else {
            Toast.makeText(applicationContext, "No Internet Connection!", Toast.LENGTH_SHORT).show()
        }








    }


inner class TrafficListAdapter(
    private val values: List<Cam>
) : ArrayAdapter<Cam?>(
    applicationContext,
    0,
    values
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
            .getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.traffic_list, parent, false)
        val description = rowView.findViewById<TextView>(R.id.intersection)
        val image = rowView.findViewById<ImageView>(R.id.campreview)
        description.text = values[position].description
        val imageUrl = values[position].imageUrl()
        if (!imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(image)
        }
        return rowView
    }
}


fun TrafficData(dataUrl: String?) {
    val queue = Volley.newRequestQueue(this)
    val jsonReq = JsonObjectRequest(Request.Method.GET, dataUrl, null, { response ->
        Log.d("CAMERAS 1", response.toString())
        try {
            val features = response.getJSONArray("Features")
            for (i in 0 until features.length()) {
                val point = features.getJSONObject(i)
                val pointCoords = point.getJSONArray("PointCoordinate")
                val coords = doubleArrayOf(pointCoords.getDouble(0), pointCoords.getDouble(1))

                val cameras = point.getJSONArray("Cameras")
                for (j in 0 until cameras.length()) {
                    val camera = point.getJSONArray("Cameras").getJSONObject(0)
                    val c = Cam(
                        camera.getString("Description"),
                        camera.getString("ImageUrl"),
                        camera.getString("Type"),
                       coords
                    )
                    TrafficData.add(c)
                }
            }


            listAdapter!!.notifyDataSetChanged()
        } catch (e: JSONException) {

            Log.d("CAMERAS error", e.message!!)
        }
    }) { error -> Log.d("JSON", "Error: " + error.message) }

    queue.add(jsonReq)
}
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}