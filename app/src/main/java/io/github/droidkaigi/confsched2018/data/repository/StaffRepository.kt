package io.github.droidkaigi.confsched2018.data.repository

import android.support.annotation.CheckResult
import io.github.droidkaigi.confsched2018.model.Staff
import kotlinx.coroutines.experimental.channels.ReceiveChannel

interface StaffRepository {
    val staff: ReceiveChannel<List<Staff>>
    fun loadStaff()
}
