package com.grinleaf.ex068googlemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    //Google Map API 적용하기 --> '주요플랫폼 api' 메모장 필기 꼬옥 참조
    //1) developer.google.com 개발자 사이트의 가이드문서에 따라 프로젝트에 라이브러리 추가 
    //2) 키 발급 받기
    //3) 가이드 문서를 참조하여 지도 개발

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml 문서에 추가한 fragment 를 찾아와서 참조하기
        FragmentManager fragmentManager = getSupportFragmentManager();   //프래그먼트를 find 하기 위한 manager 객체 생성
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);

        //비동기 방식(=여러 작업을 동시에 수행하는 방식 = 별도 Thread 방식)으로 구글 서버에서 맵의 데이터를 읽어오도록 하기
        mapFragment.getMapAsync(new OnMapReadyCallback() {  //Async(비동기)
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {  //맵의 준비가 끝났을 때 실행되는 콜백 메소드
                //아무 코드 작성하지 않아도 기본적으로 지도는 화면에 나타나야 함!(위도 0,경도 0) --> 안 나타날 경우 API Key 발급 과정에서 오류발생 가능성 有

                //가이드 참고하여 지도 관련 여러 설정들 사용 가능!
                //1. 지도의 특정 좌표로 카메라 이동 및 줌인하는 설정
                LatLng seoul = new LatLng(37.5609, 127.0347);      //구글 맵 왕십리 좌표 ^_^ 알아서 알아와라
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 18));  //zoom : 1~25 까지 줌인 정도가 있음

                //2. 지도의 특정 좌표에 마커를 추가하는 설정
                MarkerOptions marker = new MarkerOptions();
                marker.title("미래IT캠퍼스");    //마커에 붙을 이름
                marker.snippet("왕십리역에 있는 미래IT캠퍼스"); //이름 아래 서브타이틀(설명문)
                marker.position(seoul); //왕십리 좌표를 가진 seoul 객체 위치에 놓기
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_shop));
                //주의! 벡터이미지 X, .png, .jpg 이미지만 가능함! --> image asset 으로 아이콘 만들고, 자동으로 만들어지는 .xml 문서 제거하기(벡터이미지임 ㅠ)
                marker.anchor(0.5f, 1.0f);
                //x축, y축 (디폴트 이미지 배치는 좌표의 오른쪽 하단으로 늘어뜨려짐 --> 그림이 붙을 좌표 위치 설정 가능. 좌측 맨끝 0.0, 우측 맨끝 1.0)
                //x축이 0.5f 이면, 좌표가 그림의 가운데로 옴 / y축이 1.0f 이면, 좌표가 그림의 최하단으로 옴(끝)

                googleMap.addMarker(marker);    //구글맵 객체에 마커 객체 붙이기

                //2-1. 마커 클릭 시 보여지는 infoWindow(툴팁박스)를 클릭 시에 반응하기
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        //클릭한 marker 객체의 설정한 정보를 기반으로 원하는 작업 수행
                        //특정 웹페이지 열기 - 크롬 브라우저 앱 실행되도록 설정하기
                        //Intent intent= new Intent(Intent.ACTION_VIEW);
                        //Uri uri= Uri.parse("https://www.naver.com");
                        //intent.setData(uri);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com"));   //위의 세줄 한줄 컷
                        startActivity(intent);
                    }
                });

                //* 지도의 대표적인 설정들 (자바에서도 가능,xml 에서도 가능)
                //* 1. 오른쪽 하단 줌(+,-) 버튼 추가
                UiSettings settings = googleMap.getUiSettings();  //줌컨트롤러를 쓰기 전에 UiSetting 관련 객체를 가져와야함
                settings.setZoomControlsEnabled(true);

                //* 2. 내위치 표시버튼을 보여주기 : 반드시 위치검색 퍼미션이 등록되어 있어야만 사용할 수 있음!!
                settings.setMyLocationButtonEnabled(true);

                //내 위치 표시하기
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);


            }
        });
        //동적퍼미션 작업
        String[] permissions= new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if(checkSelfPermission(permissions[0])==PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions,100);
        }

    }//onCreate method
    
    //동적퍼미션 요청 결과 받기

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if(requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "내위치 기능 사용가능", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "내위치 기능 사용불가", Toast.LENGTH_SHORT).show();
        }
    }
}