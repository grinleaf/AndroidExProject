package com.grinleaf.ex069kakaomap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.kakao.util.maps.helper.Utility;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity {

    //카카오 개발자 사이트의 가이드 문서를 참고하여 제작 (* 실디바이스에서만 동작함!)
    
    //1) 카카오 SDK 적용하기
    //1-1. SDK .zip 파일 다운받아 압축을 풀고 해당 위치에 파일 복사붙이기(가이드에 따라)
    //1-2. 이 프로젝트에 위 라이브러리를 적용하기
    //2) 카카오 개발자 플랫폼에 앱 등록 및 키 발급하기
    
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //맵뷰 객체 생성하기
        mapView= new MapView(this);
        //.xml 에 있는 MapView 의 컨테이너용 뷰(RelativeLayout)에 맵뷰를 추가
        RelativeLayout mapViewContainer= findViewById(R.id.container_map);
        mapViewContainer.addView(mapView);
        //요기까지 지도가 보여야 함. 단, 가상 디바이스에서는 실행 안 됨 ㅠ

        //카카오 지도앱 가이드 - 지도 중심점, 레벨(zoom) 변경 내용 복붙
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true); // 중심점 변경
        mapView.setZoomLevel(7, true); // 줌 레벨 변경
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true); // 중심점 변경 + 줌 레벨 변경
        mapView.zoomIn(true); // 줌 인
        mapView.zoomOut(true); // 줌 아웃

        //카카오 지도앱 가이드 - 마커(Marker) 추가 내용 복붙 + MapPoint 객체 직접 만들어주기
        MapPoint point= MapPoint.mapPointWithGeoCoord(37.5,127.5);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(point);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

//        //디버그용 키해시 값 얻어오기 : 로그캣에서 KEY(식별자)로 검색하면 키해시 나옴
//        String keyHash= Utility.getKeyHash(this);
//        Log.i("KEY",keyHash);

    }
}