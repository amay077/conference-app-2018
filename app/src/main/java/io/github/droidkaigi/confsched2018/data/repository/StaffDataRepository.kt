package io.github.droidkaigi.confsched2018.data.repository

import android.content.Context
import android.support.annotation.CheckResult
import io.github.droidkaigi.confsched2018.data.local.LocalJsonParser
import io.github.droidkaigi.confsched2018.data.local.StaffJsonMapper
import io.github.droidkaigi.confsched2018.model.Staff
import io.github.droidkaigi.confsched2018.util.rx.SchedulerProvider
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.yield
import timber.log.Timber
import javax.inject.Inject

class StaffDataRepository @Inject constructor(
        private val context: Context,
        private val schedulerProvider: SchedulerProvider
) : StaffRepository {

    private val sender = ConflatedBroadcastChannel<List<Staff>>();
    override val staff: ReceiveChannel<List<Staff>> = sender.openSubscription()

    override fun loadStaff() {
        launch(CommonPool) {
            try {
                val asset = LocalJsonParser.loadJsonFromAsset(
                        this@StaffDataRepository.context, "staff.json")
                sender.offer(StaffJsonMapper.mapToStaffList(asset))
                yield()
            } catch (e: Exception) {
                Timber.e(e)
                sender.close(e)
            }
        }
    }
}
