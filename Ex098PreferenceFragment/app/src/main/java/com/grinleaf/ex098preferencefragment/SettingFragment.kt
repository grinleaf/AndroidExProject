package com.grinleaf.ex098preferencefragment

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

//원래 PreferenceFragment 상속하여 사용했으나, 현재는 PreferenceFragmentCompat 사용 (외부 라이브러리이므로 따로 추가해줘야함!)
class SettingFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //일반적인 Fragment 와는 다르게, 설정화면을 직접 설계(=layout xml)하는 것이 아님!
        //설정항목을 별도 xml 파일로 만들어 지정하면 자동으로 화면이 만들어진다! --> ../res/xml 디렉토리 생성하여 해당 폴더 하위 파일로 만들어줄 것
        //이 프래그먼트에서 setting.xml 을 화면으로 지정하기
        setPreferencesFromResource(R.xml.setting, rootKey)   //파라미터 : (화면으로 지정할 xml 문서, 최상위 키)
        val pref= PreferenceManager.getDefaultSharedPreferences(requireContext())
        findPreference<EditTextPreference>("nickname")?.summary= pref.getString("nickname","")
    }
    val pref: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext()) } //context as Context 로 강제 형변환해도 되지만, 진짜 null 이면 앱 다운됨
    override fun onResume() {
        super.onResume()
        pref.registerOnSharedPreferenceChangeListener(listener)     //설정화면이 눈에 보일 때 리스너붙이고,
    }

    override fun onPause() {
        super.onPause()
        pref.unregisterOnSharedPreferenceChangeListener(listener)   //설정화면이 가려졌을 때 리스너를 떼어내기
    }

    //리스너는 인터페이스! 인터페이스를 익명객체로 만들기 위해 object 에 implement 시키기 ★★
    val listener= object :SharedPreferences.OnSharedPreferenceChangeListener{   //추상메소드 하나이므로 람다식 가능!
        override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {   //p1 = xml 문서에서 설정한 key 값들
            //두번째 파라미터 p1 : 변경된 항목의 key 속성값
            val buffer= StringBuffer()
            when(p1){
                "message","vibration" -> buffer.append(p1+" : "+pref.getBoolean(p1,false))
                "nickname" -> {
                    val s= pref.getString(p1,"")
                    buffer.append("$p1 : $s")
                    //해당 항목의 summary 에 값을 설정하기 : 해당설정항목 객체 찾아오기
                    val etPref= findPreference<EditTextPreference>(p1)
                    etPref?.summary = s
                }
                "sound" -> {
                    val s= pref.getString(p1, "")
                    buffer.append("$p1 : $s")
                    findPreference<ListPreference>(p1)?.summary= s
                }
                "favor" -> {
                    val datas:MutableSet<String>? = pref.getStringSet(p1, mutableSetOf<String>())
//                    for(s in datas!!) buffer.append(s)
                    datas?.forEach { buffer.append(it) }
                }
            }
            AlertDialog.Builder(requireContext()).setMessage(buffer.toString()).create().show()
        }

    }


}