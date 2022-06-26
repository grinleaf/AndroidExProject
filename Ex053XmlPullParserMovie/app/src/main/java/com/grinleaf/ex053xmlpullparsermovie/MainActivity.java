package com.grinleaf.ex053xmlpullparsermovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //영화진흥위원회 open api : 주간 박스오피스 정보 이용하여 앱 개발하기
    //1. 영화진흥위원회 api 사이트에서 발급받은 key 값을 미리 String 변수로 만들기 (선행해두기)
    String apiKey="40324cb6ff4237d479c6baddc3cd3b44";

    ArrayList<Item> items= new ArrayList<>();
    RecyclerView recyclerView;
    MovieAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //테스트용 가상 데이터 추가
        //items.add(new Item("1","aaa","bbb","ccc"));
        findViewById(R.id.btn).setOnClickListener(view -> {
            loadData();
        });

        recyclerView= findViewById(R.id.recycler);
        adapter= new MovieAdapter(this, items);
        recyclerView.setAdapter(adapter);

    }

    //2. 네트워크 서버에서 데이터(.xml 문서)를 읽어오는 작업을 수행하는 기능 메소드 추가하기!
        //3. 네트워크 작업 시에는 반드시 퍼미션(허가)이 필요하다! --> AndroidManifest.xml
        //네트워크 작업은 MainThread 가 건드릴 수 없음. 별도의 Thread 가 반드시 필요함 + 화면 변경은 MainThread 만 가능함
    void loadData(){
        Thread t= new Thread(){     //4. 해당 생성된 스레드가 할 일을 {} 안에 적어주기 + 필수 메소드 run()
            @Override
            public void run() {
                //19-2-a. 검색하고자 하는 날짜를 저장하는 변수 + 오늘 날짜의 전날로 자동 계산 되도록
                Date date= new Date();  //객체가 생성될 때의 순간의 날짜/시간을 값으로 가짐
//                int year= date.getYear();   //2022
//                int month= date.getMonth(); //2 //Month 만 유독,,, 0~11 월달로 침,,,
//                int day= date.getDay();
                //이거 너무 구린방식이야~~~~~~~~~~

                //19-2-c. 어제 날짜로 설정하기! : [ date.getTime() ]     // 1970년 1월 1일 0시 0분 0초부터 1ms 마다 카운트가 증가된 값임. 즉, 하루치 만큼의 초수를 빼면 하루가 빠짐!
                date.setTime(date.getTime()-(1000*60*60*24));   //20220311 -> 20220310 으로 바뀐 값~!

                //19-2-b. 날짜를 특정 포맷으로 만들어주는 클래스 객체가 존재한다~! [ SimpleDateFormat ]
                SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd"); //날짜와 시간을 같이 표기할 때, month(M) 와 minute(m) 이니셜이 겹쳐서 대문자로 쓴댕 ㅇㅅㅇ
                //String dateStr= "20220310";
                String dateStr= sdf.format(date);

                //5. 영화진흥위원회 open api 의 주소 url 을 저장
//                String address= "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml?key=f5eef3421c602c6cb7ea224104795888&targetDt=20220311";
                String address= "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml"
                                +"?key="+apiKey         //19-1. 내가 발급받은 키값(1번)으로 교체해넣기 가능!
                                +"&targetDt="+dateStr   //19-3. 대상 데이터가 속한 날짜를 변수화(19-2번)
                                +"&itemPerPage=10";        //19-4. 해당 웹페이지 URL 의 요청변수를 추가하기도 가능! (요건 사이트마다 다르니까 가이드 잘 참조해서 가져올 것!)
                //6. 해당 앱과 네트워크 서버는 서로 멀리 떨어져있는 환경(영역) 이므로, 데이터를 주고받기 위한 연결다리(Stream)가 필요함 --> Stream 을 만드는 URL 객체 필요
                try {
                    URL url= new URL(address);      //7. 네트워크 작업은 예외처리 필수
                    //8. Stream 은 일방향이므로, input / output Stream 이 각각 따로 필요함! + 필수 예외처리
                    InputStream is= url.openStream();                   //바이트 스트림 : 이미지 불러오는 데 쓰임
                    InputStreamReader isr= new InputStreamReader(is);   //문자 스트림 : 문자 특화. 한글자씩 읽어들임 = Reader

                    //리더(isr)를 통해 한 문자씩 읽어들여 분석하는 작업은 너무 복잡하기 때문에, Reader 를 통해 읽어들인 xml 을 분석(parse)해주는 객체 : [ XmlPullParser ]
                    XmlPullParserFactory factory= XmlPullParserFactory.newInstance();   //9. PullParser 를 만드는 공장을 세움
                    XmlPullParser xpp= factory.newPullParser(); //10. XmlPullParser 생성
                    xpp.setInput(isr);  //PullParser 를 통해 stream reader 설정 ** Resource Parser 와는 다르게, setInput 시 startDocument 안쪽으로 커서가 들어와서 시작함!

                    //11. xpp 를 이용하여 설정된 서버에 xml 문서를 분석하는 작업 수행
                    int eventType= xpp.getEventType();          //eventType : START_DOCUMENT ~ END_DOCUMENT 까지 총 5 가지 중 하나의 타입을 가짐
                    Item item= null;  //14-1. Item 객체를 참조할 변수 만들어두기
                    //12. xml 문서의 끝까지 반복적으로 읽어와서 분석하는 구문
                    while(eventType!=XmlPullParser.END_DOCUMENT){
                        //eventType 의 상황에 따라 행해져야하는 작업코드 수행
                        switch (eventType){
                            case XmlPullParser.START_DOCUMENT:  //13. 시작 시 토스트 띄우기
                                runOnUiThread(new Runnable() {  //Toast 는 UI 작업. 화면 변경은 MainThread 만 할 수 있음. MainThread 의 위임장을 Runnable() 에게 주는 구문
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "파싱 시작", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;

                            case XmlPullParser.START_TAG:       //14-2. 가져올 데이터를 만날 때마다 수행할 코드 작성
                                String tagName= xpp.getName();
                                if(tagName.equals("dailyBoxOffice")){
                                    item= new Item();
                                }else if(tagName.equals("rank")){       //15. rank 의 스타트 태그에 커서 위치 --> 다음으로 넘어갔을 때 있는 text 값을 가져오기 (나머지 else 문 동일)
                                    xpp.next();
                                    if(item!=null) item.rank= xpp.getText();
                                }else if(tagName.equals("movieNm")){
                                    xpp.next();
                                    if(item!=null) item.name= xpp.getText();
                                }else if(tagName.equals("openDt")){
                                    xpp.next();
                                    if(item!=null) item.openDt= xpp.getText();
                                }else if(tagName.equals("audiAcc")){
                                    xpp.next();
                                    if(item!=null) item.audiAcc= xpp.getText();
                                }
                                break;

                            case XmlPullParser.END_TAG:
                                String tagName2= xpp.getName();
                                if(tagName2.equals("dailyBoxOffice")){
                                    //16. 일일 박스오피스의 영화 1개당 List Item 한개가 되도록, RecyclerView 에서 보여줄 대량의 데이터들(items)을 리스트(ArrayList)에 추가하기
                                    if(item!=null) items.add(item);
                                }
                                break;

                            //17. 아래 두 case 문은 필요가 없으니 안써두 됨~!
//                            case XmlPullParser.TEXT:
//                                break;

//                            case XmlPullParser.END_DOCUMENT:
//                                break;
                        }

                        eventType= xpp.next();
                    }
                    //18. RecyclerView 가 보여줄 뷰들을 만들어내는 Adapter 가 대량의 데이터들(items)에 데이터가 새로이 추가된 것을 인식해야 화면이 변경됨!
                        //이 때, adapter 에 인식시키는 문장도 화면을 변경하는 문장이므로 UI Thread 가 실행해야함 주의 ★★
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }
}