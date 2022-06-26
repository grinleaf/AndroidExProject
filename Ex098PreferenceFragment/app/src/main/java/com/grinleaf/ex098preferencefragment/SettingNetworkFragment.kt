package com.grinleaf.ex098preferencefragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingNetworkFragment:PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_network,rootKey)
    }
}