package com.grinleaf.ex056datastorageexternal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= findViewById(R.id.et);
        tv= findViewById(R.id.tv);
        findViewById(R.id.btn_save).setOnClickListener(v-> saveData());
        findViewById(R.id.btn_load).setOnClickListener(v-> loadData());

        //동적퍼미션이 필요한 외부저장소에 저장하는 버튼
        findViewById(R.id.btn).setOnClickListener(v->permissionSaveDate());

    }

    void saveData(){
        //외부저장소 중에서 앱에게 할당된 저장공간에 파일저장하기
        //내부저장소와 마찬가지로, 앱을 삭제하면 할당된 공간의 파일 역시 삭제됨

        //** 외부메모리(SD card)가 있는지 여부부터 확인할 것 **
        String state= Environment.getExternalStorageState();


        //외장메모리상태(state)가 연결(mounted)되어있지 않은지를 확인
        if(!state.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this, "SDcard is not mounted", Toast.LENGTH_SHORT).show();
            return;
        }
        
        //저장할 데이터 얻어오기
        String data= et.getText().toString();
        et.setText("");

        
        //Data.txt 파일이 저장될 디렉토리 경로 구하기 --> 디바이스마다 경로가 다를 수 있어 고정경로를 쓸 수 없음
        File path;  //경로를 담을 참조변수
        
        //앱에게 할당된 영역(storage/emulated/0/Android/앱패키지명/files/) 폴더 안에 "MyDir" 라는 이름의 폴더 경로를 얻어올 것
        File[] dirs= getExternalFilesDirs("MyDir");    //경로를 File[] 배열로 줌
        path= dirs[0];  //배열의 첫번째 경로를 대입

        //먼저, 경로가 잘 받아졌는지 확인할 것
        tv.setText(path.getPath());

        //위 경로에 Data.txt 라는 이름의 파일명.확장자 까지 포함한 File 객체 생성
        File file= new File(path, "Data.txt");

        //파일까지 연결되는 스트림 생성 - 문자스트림으로 바로 만들기 (스트림은 try/catch 해줘야함~)
        try {
            FileWriter fw= new FileWriter(file,true);
            PrintWriter writer= new PrintWriter(fw);

            writer.println(data);
            writer.flush();
            writer.close();

            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void loadData(){
        //SD card 를 읽을 수 있는 상태인지 확인
        String state= Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)||state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){ //sd 카드가 마운트 상태/마운트(읽기전용) 상태일 경우

            File[] dirs= getExternalFilesDirs("MyDir");
            File path= dirs[0];

            File file= new File(path, "Data.txt");

            try {
                FileReader fr= new FileReader(file);
                BufferedReader reader= new BufferedReader(fr);

                StringBuffer buffer= new StringBuffer();
                while(true){
                    String line= reader.readLine();
                    if(line==null) break;
                    buffer.append(line+"\n");
                }

                tv.setText(buffer.toString());
                reader.close();
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "파일 경로가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "읽기 실패", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "외부메모리 읽을 수 없음", Toast.LENGTH_SHORT).show();
        }
    }


    //동적퍼미션이 필요한 외부저장소에 저장(앱에 할당된 경로를 뺀 나머지 모든 폴더에 저장하고자 할 때
    //해당 공간에 저장할 경우, 앱을 지우더라도 데이터가 삭제되지 않는다!
    void permissionSaveDate(){
        //외부 저장소 사용가능 여부 체크
        String state= Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this, "외부저장소 없음", Toast.LENGTH_SHORT).show();
            return;
        }

        //마시멜로우 버전(api 23)이상에서 도입된 보안강화 기능 = 동적 퍼미션 작업 (아무나 저장 데이터에 접근할 수 없도록)
        //1. AndroidManifest.xml 에 정적 퍼미션 작업
        //2. 자바코드로 퍼미션에 대한 허가여부 체크하기
        int checkResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(checkResult== PackageManager.PERMISSION_DENIED){
            //퍼미션을 요청하는 다이얼로그 보이기 : 퍼미션 요청 다이얼로그를 보이는 기능메소드가 이미 액티비티에 존재함. 호출만!
            String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, 100);

            return;
        }

        //SD card 특정 위치에 저장하기
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); //어떤 디바이스든 동일하게 가지고 있는 폴더(=publicDirectory)
        if(path!=null) tv.setText(path.getPath()); //텍스트뷰에 경로 나타내보기

        File file= new File(path,"aaa.txt");

        try {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter writer= new PrintWriter(fw);

            writer.println(et.getText().toString());
            writer.flush();
            writer.close();

            et.setText("");
            tv.setText("SAVED");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }//method
    
    //requestPermissions()에 의해 보여진 다이얼로그의 허용/거부 선택 시 자동으로 그 결과를 알려주기 위해 발동하는 콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if(requestCode==100){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){     //허용을 눌렀는지 아닌지 확인
                Toast.makeText(this, "외부저장소 쓰기 가능", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "외부저장소 쓰기 금지", Toast.LENGTH_SHORT).show();
            }
            
        }
    }
}//MainActivity class

