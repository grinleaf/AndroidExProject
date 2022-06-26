package com.grinleaf.ex052xmlresourceparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(view -> {loadData();} );   //onCreate() 가 복잡해보이지 않도록 코드를 메소드로 분리하면 좋음!
    }//onCreate method

    //XML 데이터를 읽어와서 분석하여 화면에 보여주는 작업 기능메소드
    void loadData(){
        //Resource 폴더에 있는 xml 문서를 읽어서 분석(parse)하는 작업 수행 + res 폴더 창고관리자 객체 소환
        Resources res= getResources();

        //창고관리자로부터 분석가(parser) 객체 얻어오기
        XmlResourceParser xrp= res.getXml(R.xml.movies);
        
        //xml 파서(parser)에게 분석 작업 요청 : Stream 을 쓰고 있기 때문에 반드시 예외처리 해줘야함
        try {
            xrp.next();
            int eventType= xrp.getEventType();

            StringBuffer buffer= new StringBuffer();    //문자열을 모아두는 버퍼
            String tagName;     //태그 이름 저장
            String text;        //텍스트값

            while(eventType!=XmlResourceParser.END_DOCUMENT) {      //XmlResourceParser 가 END_DOCUMENT 가 아니면 반복, END_DOCUMENT 이면 종룔
                switch (eventType) {
                    case XmlResourceParser.START_DOCUMENT:
                        buffer.append("xml 파싱을 시작합니다.\n\n");
                        break;
                    case XmlResourceParser.START_TAG:
                        tagName= xrp.getName();      //태그 이름 추출
                        if(tagName.equals("item")){

                        }else if(tagName.equals("no")){
                            buffer.append("번호:");
                        }else if(tagName.equals("title")) {
                            buffer.append("제목:");
                        }else if(tagName.equals("genre")) {
                            buffer.append("장르:");
                        }
                        break;
                    case XmlResourceParser.TEXT:
                        text= xrp.getText();
                        buffer.append(text);
                        break;
                    case XmlResourceParser.END_TAG:
                        tagName= xrp.getName();
                        if(tagName.equals("item")){
                            buffer.append("--------------------\n\n");
                        }else if(tagName.equals("no")){
                            buffer.append("\n");
                        }else if(tagName.equals("title")) {
                            buffer.append("\n");
                        }else if(tagName.equals("genre")) {
                            buffer.append("\n");
                        }
                          break;
                    case XmlResourceParser.END_DOCUMENT:
                        break;
                }//switch

                eventType= xrp.next();
                //eventType= xrp.getEventType();
            }//while

            //반복이 끝나면 파싱이 완료된 것임 --> 사용자에게 파싱 완료를 안내해주기
            buffer.append("\n\n 파싱을 완료했습니다.\n");
            tv.setText(buffer.toString());

        } catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
    }
}