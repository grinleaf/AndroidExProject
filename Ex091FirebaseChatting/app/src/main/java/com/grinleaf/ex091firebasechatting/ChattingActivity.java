package com.grinleaf.ex091firebasechatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.grinleaf.ex091firebasechatting.databinding.ActivityChattingBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {

    ActivityChattingBinding binding;

    String chattingRoomName= "chat";
    ArrayList<MessageItem> messageItems= new ArrayList<>();
    MyAdapter adapter;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //9. 제목줄에 채팅방 이름과 닉네임 보이기 (실제 앱에서는 상대방 이름이 나옴-chattingActivity 전환 전에 리스트에서 상대방 이름(=chattingRoomName)을 선택할 수 있어야함)
        getSupportActionBar().setTitle(chattingRoomName);
        getSupportActionBar().setSubtitle(G.nickname);

        adapter= new MyAdapter(this, messageItems);
        binding.recycler.setAdapter(adapter);

        binding.btnSend.setOnClickListener(v->clickSend());

        firebaseFirestore= FirebaseFirestore.getInstance();

        //14. 'chat' 컬렉션의 데이터에 변화가 생기는 것을 감지
        CollectionReference chatRef= firebaseFirestore.collection(chattingRoomName);
        chatRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                //16. QuerySnapshot 은 데이터 요청 시, 기존에 DB 에 있는 모든 Document 를 가져다주기 때문에, 이전에 작성한 메시지가 중복되어 나타난다
                //데이터가 변경된 document 만 요청해야함! --> for 문 조건부 수정
                for (DocumentChange documentChange:value.getDocumentChanges()){ //변경된 document 만 찾아옴
                    DocumentSnapshot snapshot= documentChange.getDocument();    //변경된 document 안의 snapshot 을 얻어옴

                    Map<String, Object> msg= snapshot.getData();
                    String name= msg.get("name").toString();
                    String message= msg.get("message").toString();
                    String time= msg.get("time").toString();
                    String profileUrl= msg.get("profileUrl").toString();

//                for (DocumentSnapshot snapshot:value.getDocuments()){   //document 가 생성될 때마다 있는 snapshot
//                Map<String, Object> msg= snapshot.getData();
//                String name= msg.get("name").toString();
//                String message= msg.get("message").toString();
//                String time= msg.get("time").toString();
//                String profileUrl= msg.get("profileUrl").toString();

                    //15. 읽어들인 메세지를 리사이클러뷰가 보여주는 messageItems 에 추가하기 위해 객체 생성
                    messageItems.add(new MessageItem(name, message, time, profileUrl));
                    adapter.notifyItemInserted(messageItems.size()-1);  //제일 마지막 위치에 Item 이 삽입되었음을 알림

                    //17. 리사이클러뷰의 스크롤 위치를 마지막 position 으로 이동
//                    binding.recycler.scrollToPosition(messageItems.size()-1);
                    binding.recycler.scrollToPosition(adapter.getItemCount()-1);    //메세지 별로 숨김, 차단 등의 기능을 썼을 때, 남은 메시지들 중 마지막 position 찾기
                }
            }
        });
    }

    void clickSend(){
        String checkSpace= binding.et.getText().toString().replace(" ","");
        if(checkSpace.equals("")) return;
        //12. 닉네임, 프로필이미지, 메시지, 작성시간을 저장
        String nickName= G.nickname;
        String message= binding.et.getText().toString();
        String profileUrl= G.profileUrl;
        Calendar calendar= Calendar.getInstance();
        String time= calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);

        //13-1. firebasefirestore DB 에 저장할 데이터들을 멤버로 가지는 MessageItem 객체를 통으로 저장하기
        MessageItem messageItem= new MessageItem(nickName, message,time,profileUrl);

        //13-2. 'chat' 이라는 채팅방이름으로 Collection 을 만들어서 MessageItem 객체를 통으로 저장하기
        //단, Document 이름이 랜덤하면 저장순서가 바뀔수 있으므로, document 명을 시간으로 설정
        firebaseFirestore.collection(chattingRoomName).document("Msg_"+System.currentTimeMillis()).set(messageItem);

        //13-3. 메시지 전송 후 다음 입력을 위해 Edittext 에 써있는 글씨를 삭제
        binding.et.setText("");

        //13-4. 메시지 전송 후 소프트키패드 화면아래로 내리기
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //Activity 자체가 Context 의 상수들을 가지고 있긴 하지만, 실무상으로 가독성을 위해 Context.INPUT_METHOD_SERVICE 처럼 명시해주는 편이 좋음
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        //Token 은 액티비티 내에서 포커스를 가진 한 뷰에만 있고, 해당 포커스를 가진 위치를 알아내서 getCurrentFocus() / 토큰을 가져옴 getWindowToken()
    }
}