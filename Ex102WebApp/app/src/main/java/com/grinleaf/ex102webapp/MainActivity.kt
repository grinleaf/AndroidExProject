package com.grinleaf.ex102webapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {

    val wv: WebView by lazy { findViewById(R.id.wv) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //** 보안 문제 **
        //기본적으로 WebView 는 JavaScript 실행을 허용하지 않는 상태! --> 웹뷰 설정 객체를 통해 JS 사용을 허용하도록 변경해야함! (안 하면 기능 실행이 안됨..)
        wv.settings.javaScriptEnabled= true

        //** 웹뷰 사용 시 필수 설정 값 2가지 (현재는 1가지) **
        //1) (디바이스 버전이 낮은 기종인 경우 해당) 새로운 웹문서를 열 때 무조건 이 웹뷰를 사용하지 않고, 디바이스에 설치되어 있는 웹브라우저앱(크롬 등)이 실행되면서 열렸음
        //--> 이 앱의 웹뷰 안에서 열리도록 설정하기
        //wv.webViewClient= null  //새로운 웹브라우저로 웹문서 열기(이전의 default 값. 지금은 null 값이 아니래)
        wv.webViewClient= WebViewClient()

        //2) alert() 이나 새로운 다이얼로그, 팝업들이 보이도록 하기 위한 설정
        wv.webChromeClient= WebChromeClient()   //팝업 관리자

        //웹뷰가 보여줄 웹문서(.html) 로드하기 --> 오프라인 상태일 경우 호스팅 서버와의 연결 불가 (== 웹문서와 연결 불가)
        //wv.loadUrl("")
        //* 오프라인 상태일 때도 화면에 보이고 싶을 경우, html 문서가 해당 프로젝트 폴더 안에 위치해야함!
        //--> res 폴더는 웹 기반 용도가 아니므로, asset 폴더 안에 웹 문서를 저장해줘야 함~
        wv.loadUrl("file:///android_asset/index.html") //asset 폴더 경로
        //* 별도 서버 호스트의 html 문서 역시 볼 수 있음 (단, 인터넷 퍼미션 미리 작성해줘야함~)
        wv.loadUrl("http://grinleaf.dothome.co.kr/")
    }

    //* 뒤로가기 버튼을 눌렀을 때 : 앱은 사실상 액티비티 하나 안에서 화면 내용이 변경되는 것. 그냥 뒤로가기 누르면 이전 페이지로 돌아가지 않고 앱이 꺼짐
    override fun onBackPressed() {
        if(wv.canGoBack()) wv.goBack()  //웹뷰에게 이전페이지가 있는지 묻고, 있으면 되돌아가도록 설정
        else super.onBackPressed()
    }
}