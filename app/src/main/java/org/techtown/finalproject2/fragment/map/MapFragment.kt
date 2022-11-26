package org.techtown.finalproject2.fragment.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationSource
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.util.FusedLocationSource
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.techtown.finalproject2.API.PostData
import org.techtown.finalproject2.API.Stadm
import org.techtown.finalproject2.PostDetail
import org.techtown.finalproject2.R
import org.techtown.finalproject2.adapter.ListAdapter
import org.techtown.finalproject2.databinding.FragmentMapBinding
import org.techtown.finalproject2.databinding.RecyclerViewItemBinding
import org.techtown.finalproject2.fragment.home.HomeFragment

class MapFragment : Fragment(),OnMapReadyCallback {

    private var _binding : FragmentMapBinding? = null
    val binding get() = _binding!!
    private val ACCESS_FINE_LOCATION = 1000
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapViewModel: MapViewModel by viewModel()
    private lateinit var callback: OnBackPressedCallback

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater,container,false)
        binding.slidingPanel.isTouchEnabled = true
        locationSource = FusedLocationSource(this,ACCESS_FINE_LOCATION)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.mapView, it).commit()
            }
        mapFragment.getMapAsync(this)
        mapViewModel.getMarker()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.slidingPanel.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) binding.slidingPanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                else onDetach()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(p0: NaverMap) {

        this.naverMap = p0
        this.naverMap.locationSource = locationSource
        this.naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.addOnLocationChangeListener { location ->
            this.naverMap.locationOverlay.position = LatLng(location.latitude, location.longitude)
        }
        this.naverMap.uiSettings.isLocationButtonEnabled = true

        mapViewModel.markerLiveData.observe(viewLifecycleOwner){
            it.forEach {

                val infoWindow = InfoWindow()
                infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(requireContext()){
                    override fun getText(p0: InfoWindow): CharSequence {
                        Log.d("태그", "getText: ${mapViewModel.api.stadmList[it.tag.toString().toInt()-1].stadm_NM}")
                        return mapViewModel.api.stadmList[it.tag.toString().toInt()-1].stadm_NM
                    }
                }

                it.setOnClickListener {
                    Log.d("태그", "setMarker: 마커 클릭 ${binding.slidingPanel.panelState}")
                    openSlidingLayout()
                    setListView(it.tag.toString().toInt())
                    true
                }
                it.map = naverMap
                infoWindow.open(it)
            }
        }
    }
    private fun openSlidingLayout(){
        if (binding.slidingPanel.panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            binding.slidingPanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }
    }
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
    private fun setListView(position : Int){

        val listItem = mapViewModel.api.postDataList.value?.filter {
            it.stadm_NO_FK == position
        }
        val adapter = ListAdapter(listItem as ArrayList<PostData>)
        adapter.setStadmList(mapViewModel.api.stadmList)
        adapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener{
            override fun onItemClick(b: RecyclerViewItemBinding, post: PostData, positon: Int) {
                Log.d("태그", "onItemClick: call")
                Intent(context,PostDetail::class.java).apply {
                    putExtra("post",post)
                }.run { startActivity(this) }
            }
        })
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        if(binding.recycler.itemDecorationCount==0) binding.recycler.addItemDecoration(HomeFragment.VerticalItemDecorator(30))
    }
}