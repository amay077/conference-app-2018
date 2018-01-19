package io.github.droidkaigi.confsched2018.presentation.common.pref

import com.chibatching.kotpref.KotprefModel
import io.github.droidkaigi.confsched2018.R

object Prefs : KotprefModel() {
    public override val kotprefName: String = "droidkaigi_prefs"
    var enableLocalTime: Boolean by booleanPref(false, R.string.pref_key_enable_local_time)
    var enableNotification: Boolean by booleanPref(true, R.string.pref_key_enable_notification)
}
