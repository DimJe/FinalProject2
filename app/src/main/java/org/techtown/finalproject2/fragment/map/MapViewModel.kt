package org.techtown.finalproject2.fragment.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import org.techtown.finalproject2.API.Api

class MapViewModel(val api : Api) : ViewModel() {

    val markerLiveData = MutableLiveData<ArrayList<Marker>>()

    fun getMarker(){
        markerLiveData.value?.clear()
        val markerList = ArrayList<Marker>()
        api.stadmList.forEach {
            val marker = Marker()
            val temp = api.postDataList.value?.filter { postData ->
                postData.stadm_NO_FK==it.stadm_NO_PK
            }
            if(temp?.size != null){
                marker.icon = MarkerIcons.BLUE
                marker.position = LatLng(it.stadm_LAT.toDouble(),it.stadm_LONG.toDouble())
                marker.tag = it.stadm_NO_PK
                markerList.add(marker)
            }
        }
        markerLiveData.value = markerList
    }

}