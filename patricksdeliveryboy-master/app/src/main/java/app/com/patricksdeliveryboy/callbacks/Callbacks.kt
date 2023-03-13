package app.com.patricksdeliveryboy.callbacks

import app.com.patricksdeliveryboy.models.DataItem
import com.google.android.gms.maps.model.LatLng

interface ItemClickListener{
    fun onItemClick(data : DataItem)
}
interface onLocationChanged{
    fun onGpsLocations(latitude : Double, longitude: Double)
}
