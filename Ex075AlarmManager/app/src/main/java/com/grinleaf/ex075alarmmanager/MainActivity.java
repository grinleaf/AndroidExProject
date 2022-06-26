package com.grinleaf.ex075alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1-5. 10초 후에 발동하는 알람 설정 --> 알람 매니저 소환하여 알람 서비스 get 해오기
        alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        findViewById(R.id.btn).setOnClickListener(v->{

            //0. 10초 후에 알람이 울리도록 설정 : 알람시간이 되었을 때 Activity 실행 / Service 실행 / BroadcastReceiver 실행
            //버전업에 따라 알람 --> Activity 가 바로 실행되는 것을 제한하는 중. 이제 팝업(Notification)으로 나오는 추세

            //1. BroadcastReceiver 실행시키기
            //1-1. 알람에 의해 실행될 Receiver 를 실행시켜주는 Intent 생성
            Intent intent= new Intent(this, AlarmReceiver.class);  //두번째 파라미터로 쓰일 알람리시버 클래스 생성

            //1-4. 알람에 의해 잠시 보류(pending)되어 있어야하는 Intent 로 만들기
            PendingIntent pendingIntent= PendingIntent.getBroadcast(this,10,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
            //메인액티비티의 특정 인텐트 객체에 식별자를 붙이고 속성값 부여

            //1-6. 알람 매니저 설정 --> android 12 버전부터 Manifest.xml 퍼미션 추가 필수
            //M(마시멜로우)버전부터는 Doz(낮잠)모드(=Idle)가 생겨서, 이를 깨우고 알람을 울리도록 설정됨
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);
            //첫번째 파라미터 : RTC (세계 표준 시 기준으로 시간 설정)
            //두번째 파라미터 : triggerAtMillis (1970년 1월 1일 0시 0분 기준으로 시간 설정)
                          // ex) 1000 = 1970.01.01 0시 0분 1초 --> 너무 복잡해서 자바 클래스 메소드 사용 (현재시간 기준으로 시간 설정)
            //세번째 파라미터 : PendingIntent 객체
        });

        findViewById(R.id.btn2).setOnClickListener(v->{
            //2. 특정 날짜와 시간을 선택하는 다이얼로그 보이기
            //2-1. 날짜를 선택하는 다이얼로그
            Calendar calendar= Calendar.getInstance();  //getInstance() 작업을 하는 순간의 시간을 저장
            year= calendar.get(Calendar.YEAR);   //파라미터 field : 멤버변수!
            month= calendar.get(Calendar.MONTH);
            day= calendar.get(Calendar.DATE);
            DatePickerDialog dialog= new DatePickerDialog(this, dateSetListener, year,month,day);   //캘린더에서 날짜 선택이 가능한 다이얼로그가 보여짐!
            dialog.show();
        });
    }//onCreate method
    
    //2-2. 특정 날짜와 시간을 저장하는 멤버변수
    int year, month, day;
    int hour, minute;  //second 는 다이얼로그에서 인식을 못한댕

    //2-3. 날짜 선택을 듣는 리스너
    DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            //파라미터 i/i1/i2 : 선택한 연/월/일
            year=i;
            month=i1;
            day=i2;

            //시간 선택 다이얼로그 보이기
            Calendar calendar= Calendar.getInstance();
            hour= calendar.get(Calendar.HOUR_OF_DAY);  //HOUR : 12시간 / HOUR_OF_DAY : 24시간
            minute= calendar.get(Calendar.MINUTE);
            new TimePickerDialog(MainActivity.this, timeSetListener,hour,minute,true).show();
        }
    };
    
    //2-4. 시간 선택 완료 리스너
    TimePickerDialog.OnTimeSetListener timeSetListener= new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            //파라미터 i/i1 : 선택한 시/분
            hour= i;
            minute= i1;

            //2-5. 선택된 날짜와 시간으로 알람을 설정하기 위해 캘린더 객체 생성
            Calendar calendar= Calendar.getInstance();
            calendar.set(year, month, day, hour, minute, 0);    //캘린더의 현재 시간 설정 가능!

            //2-7. 알람 시에 실행할 컴포넌트 (Service or BroadcastReceiver)
            Intent intent= new Intent(MainActivity.this, AlarmService.class);
            PendingIntent pendingIntent= null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                pendingIntent= PendingIntent.getForegroundService(MainActivity.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);  //뒤로가기 눌러도 꺼지지 않음
            }else{
                pendingIntent= PendingIntent.getService(MainActivity.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
            }

            //2-6. 알람 설정
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            //두번째 파라미터 : calendar 객체를 만든 이유~! triggerAtMillis 를 간단하게 처리
        }
    };
    
}