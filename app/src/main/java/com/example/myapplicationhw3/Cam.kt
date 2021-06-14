package com.example.myapplicationhw3

<<<<<<< HEAD
data class Cam(val description: String, private val image: String, var type: String, var coords: DoubleArray ) {

    private val baseUrl: Map<String, String> = mapOf(
        "sdot" to "https://www.seattle.gov/trafficcams/images/",
        "wsdot" to "https://images.wsdot.wa.gov/nw/"
    )

    fun imageUrl() : String {
        return baseUrl[this.type] + this.image
    }

    companion object {
        var camUrl = " https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"

=======
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

data class Cam(val description: String, private val image: String, var type: String, var coords: DoubleArray) : AppCompatActivity() {
    private val baseUrl: Map<String, String> = mapOf(
        "sdot" to "https://www.seattle.gov/trafficcams/images",
        "wsdot" to "https://images.wsdot.wa.gov/nw/"
    )


    fun imageUrl(): String {
        return baseUrl[this.type] + this.image
    }
    companion object {
        var camUrl = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"
>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50


    }
}
<<<<<<< HEAD
=======

>>>>>>> 3e81fa41aec16433c9ef06c15239f898f6b6ac50
