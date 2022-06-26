package com.grinleaf.ex103hybridapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.grinleaf.ex103hybridapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //하이브리드 앱 제작 시 기본 설정들
        binding.wv.settings.javaScriptEnabled= true     //js 실행 허용
        binding.wv.settings.allowFileAccess= true       //클라이언트(로컬)의 JS 문서에서 ajax 기술 동작 허용
        binding.wv.webViewClient= WebViewClient()       //앱 내에서 실행 (새창x)
        binding.wv.webChromeClient= WebChromeClient()   //웹 팝업 작업을 끌어오기

        //웹뷰가 보여줄 뷰를 설정
        binding.wv.loadUrl("file:///android_asset/index.html")
        
        //1) Native Android 쪽(앱)에서 WebView(웹)의 UI 제어하기 --> index.html 의 내부 스크립트로 쓰여진 함수를 kotlin 문서에서 호출
        binding.btn.setOnClickListener {
            //WebView 에서 보여주는 index.html 안에 있는 특정 함수를 호출해서 UI 제어
            //Native 에서 직접 html 의 dom(document object model == document.getElementById...) 요소는 제어 불가
            var msg:String= binding.et.text.toString()
            binding.wv.loadUrl("javascript:setMessage('$msg')")     //'현재 웹뷰가 보여주고 있는 html 문서에 연관된 javascript' 의 변수/함수 호출

            binding.et.setText("")
        }

        //2) WebView(웹)에서 Native Android(앱)를 제어하기 위한 중개인 역할 객체 생성 : .addJavascriptInterface(접근허용할 객체, "임의의 이름")
        //--> Web 에서 해당 객체를 지칭할 별명을 설정해야함! ★
        binding.wv.addJavascriptInterface(WebViewConnector(), "Droid")

    //// ※※ 하이브리드 앱 변천사 ※※    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // ※ 이 WebViewConnector 객체 안에 자주 쓰는 메소드를 작성해둔 라이브러리가 있었음. --> 웹 개발자들은 코틀린,스위프트 코드 자체를 쓸줄 모름,,
        //--> 안드로이드 스튜디오에서 작성하는 모든 코드를 자동으로 작성해주는 'phoneGap' 등장 (오픈소스) --> Android/Ios 모두 포함, 무거움, 느림.
        //--> Adobe 사에서 라이센스 사가기 전까지만 무료 버전(==cordova), 이후 유료(==Adobe)
        //--> cordova 에 폴더구조가 제멋대로인 웹 프로그램(js/css/html/image...개발자마다 제각각)을 조합하여 나온 [프레임 워크 : Ionic]
        //--> 경쟁 구도로 나온 React Native (모바일 전용 하이브리드 앱 프레임워크). Ionic 의 단점(==cordova 의 단점)인 느려터진 반응 속도를 해결!
        //--> React 에서는 웹뷰안에 넣을 html/CSS 를 사용할 필요없이 JS 만으로 웹 구성이 가능해짐! (가상 렌더링 : 코드가 각 운영체제에 맞춰 자동으로 kotlin/swift/dot 언어로 변환됨) ★★
        //--> 뒤이어 Flutter 등장 (React 와 유사. 개발 언어가 다름. 다트 언어!)
        // ※ 얘네를 [크로스 플랫폼] 이라고 지칭함
        //--> React Native : JS 언어 사용   (엄밀히는 JSX(JS+xml) 언어 사용. 새로운 언어지만, js 언어로 써도 jsx 로 변환되도록 편집기를 짜놔서 다들 js 로 짠대. 100% 호환됨~)
        //--> Flutter      : Dart 언어 사용 (구글에서 매우매우 푸시중인 언어)
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    //웹뷰의 JavaScript 와 통신을 담당할 중개인 객체 클래스 정의
    inner class WebViewConnector{

        //★ 해당 메소드가 JavaScript 에서 호출될 수 있도록 반드시 특정 어노테이션을 주기 ! : @JavascriptInterface
        @JavascriptInterface
        fun setTextView(msg:String){
            binding.tv.text= "웹뷰로부터 받은 메세지 : $msg"
        }

        //★ 디바이스의 고유 기능인 갤러리앱을 JS에서 여는 기능
        @JavascriptInterface
        fun openGalleryApp(){
            val intent= Intent(Intent.ACTION_PICK)
            intent.type= "image/*"
            startActivity(intent)
        }
    }
}