package org.techtown.firebase_test

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.R
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MappgActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
    //    private lateinit var mapView: MapView
//    private val LOCATION_PERMISSION_REQUEST_CODE: Int = 1000
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource // 위치 반환

    private val marker1 = Marker()
    private val marker2 = Marker()
    private val marker3 = Marker()

    private val infoWindow1 = InfoWindow()
    private val infoWindow2 = InfoWindow()

    private val viewPager: ViewPager2 by lazy{
        findViewById(R.id.houseViewPager)
    }

    private val mapView: MapView by lazy {
        findViewById(R.id.mapView)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val currentLocationButton: LocationButtonView by lazy {
        findViewById(R.id.currentLocationButton)
    }

    private val bottomSheetTitleTextView: TextView by lazy {
        findViewById(R.id.bottomSheetTitleTextView)
    }

    private val viewPagerAdapter = HospitalViewPagerAdapter(itemClicked = {
        val intent = Intent()
            .apply {
                action = Intent.ACTION_SEND
//                putExtra(
//                    Intent.EXTRA_TEXT,
//                    "[서울여대 부근 동물병원] ${it.title} ${it.location} 사진보기 : ${it.imgUrl}"
//                )
                type = "text/plain"
            }
        startActivity(Intent.createChooser(intent, null))
    })

    private val recyclerAdapter = HospitalListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        viewPager.adapter = viewPagerAdapter
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val selectedHospitalModel = viewPagerAdapter.currentList[position]
                val cameraUpdate =
                    CameraUpdate.scrollTo(LatLng(selectedHospitalModel.lat, selectedHospitalModel.lng))
                        .animate(CameraAnimation.Easing)

                naverMap.moveCamera(cameraUpdate)
            }
        })

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.497885, 127.027512))
        naverMap.moveCamera(cameraUpdate)

        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false
        currentLocationButton.map = naverMap

        locationSource = FusedLocationSource(this@MainActivity, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        getHospitalListFromAPI()
    }
//        naverMap.locationSource = locationSource
//        naverMap.locationTrackingMode = LocationTrackingMode.Follow

    // 현재 위치 마커
//        marker1.position = LatLng(37.6281, 127.0905)
//        marker1.map = naverMap
//        marker1.icon = MarkerIcons.BLACK
//        marker1.iconTintColor = Color.RED

    // 장소 리스트 마커
//        marker2.position = LatLng(37.6221, 127.0864)
//        marker2.map = naverMap
//        marker2.captionText = "키움동물병원"
//        marker3.position = LatLng(37.6250, 127.0800)
//        marker3.map = naverMap
//        marker3.captionText = "미래동물병원"

    // 카메라 줌 레벨 조정
//        val cameraPosition = CameraPosition( // 카메라 위치 변경
//            LatLng(37.6281, 127.0905),  // 위치 지정
//            12.3 // 줌 레벨
//        )
//        naverMap.cameraPosition = cameraPosition // 변경된 위치 반영
//
//        // 정보창 내용 작성
//        infoWindow1.adapter = object : InfoWindow.DefaultTextAdapter(application) {
//            override fun getText(infoWindow: InfoWindow): CharSequence {
//                return "전화번호: 02-949-7582"
//            }
//        }
//
//        infoWindow2.adapter = object : InfoWindow.DefaultTextAdapter(application) {
//            override fun getText(infoWindow: InfoWindow): CharSequence {
//                return "전화번호: 02-949-5975"
//            }
//        }
//
//        // 정보창 열기
//        infoWindow1.open(marker2)
//        infoWindow2.open(marker3)
//
//        // 마커를 클릭하면
//        val listener = Overlay.OnClickListener { overlay ->
//            val markerA = overlay as Marker
//
//            // 현재 마커에 정보 창이 열려 있지 않을 경우에는 열기
//            if (markerA.infoWindow == null) {
//                infoWindow1.open(marker2)
//                infoWindow2.open(marker3)
//            } else { // 이미 현재 마커에 정보 창이 열려 있을 경우에는 닫기기
//                infoWindow1.close()
//                infoWindow2.close()
//            }
//            true
//        }
//        marker2.onClickListener = listener
//        marker3.onClickListener = listener
//    }

//    marker1.setTag("마커 1");
//    marker1.setOnClickListener(overlay -> {
//        // 마커를 클릭할 때 정보창을 엶
//        infoWindow.open(marker1);
//        return true;
//    });
//
//    marker2.setTag("마커 2");
//    marker2.setOnClickListener(overlay -> {
//        // 마커를 클릭할 때 정보창을 엶
//        infoWindow.open(marker2);
//        return true;
//    });
//
//    marker3.setTag("마커 3");
//    marker2.setOnClickListener(overlay -> {
//        // 마커를 클릭할 때 정보창을 엶
//        infoWindow.open(marker3);
//        return true;
//    });

    private fun getHospitalListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HospitalService::class.java).also {
            it.getHospitalList()
                .enqueue(object : Callback<HospitalDto> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<HospitalDto>,
                        response: Response<HospitalDto>
                    ) {
                        if (response.isSuccessful.not()) {
                            return
                        }
                        response.body()?.let { dto ->
                            updateMarker(dto.items)
                            viewPagerAdapter.submitList(dto.items)
                            recyclerAdapter.submitList(dto.items)

                            bottomSheetTitleTextView.text = "${dto.items.size}개의 동물병원이 검색되었습니다."
                        }
                    }

                    // 실패 처리리
                    override fun onFailure(call: Call<HospitalDto>, t: Throwable) {
                    }
                })
        }
    }

    // 지도에 마커 표시하기기
    private fun updateMarker(hospital: List<HospitalModel>){
        hospital.forEach{ hospital ->
            val marker = Marker()
            marker.position = LatLng(hospital.lat, hospital.lng)
            marker.onClickListener = this

            marker.map = naverMap
            marker.tag = hospital.id
            marker.icon = OverlayImage.fromResource(R.drawable.map_arrow)

//            marker.icon = MarkerIcons.BLACK
//            marker.iconTintColor = Color.RED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                // 권한 설정 거부시 위치 추적 사용하지 않음
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    // 메모리가 별로 없을 때 호출됨
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onClick(overly: Overlay): Boolean {
        val selectedModel = viewPagerAdapter.currentList.firstOrNull {
            it.id == overly.tag
        }
        selectedModel?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            viewPager.currentItem = position
        }
        return true
    }
