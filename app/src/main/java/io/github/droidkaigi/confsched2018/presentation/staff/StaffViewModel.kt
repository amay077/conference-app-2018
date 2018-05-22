package io.github.droidkaigi.confsched2018.presentation.staff

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import io.github.droidkaigi.confsched2018.data.repository.StaffRepository
import io.github.droidkaigi.confsched2018.model.Staff
import io.github.droidkaigi.confsched2018.presentation.Result
import io.github.droidkaigi.confsched2018.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class StaffViewModel @Inject constructor(
        private val repository: StaffRepository,
        private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val staff: LiveData<Result<List<Staff>>> by lazy {
        val liveData = MutableLiveData<Result<List<Staff>>>()

        launch(Unconfined) {
            liveData.postValue(Result.inProgress())
            repository.staff.consumeEach {
                liveData.postValue(Result.success(it))
            }
        }
        liveData
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        repository.loadStaff()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
