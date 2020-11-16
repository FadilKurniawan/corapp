package com.corap.feature.member

import androidx.lifecycle.LifecycleOwner
import com.corap.BaseApplication
import com.corap.BuildConfig
import com.corap.R
import com.corap.base.presenter.BasePresenter
import com.corap.data.local.RealmHelper
import com.corap.data.model.UserMembers
import com.corap.data.remote.services.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by DODYDMW19 on 1/30/2018.
 */

class MemberPresenter : BasePresenter<MemberView> {

    @Inject
    lateinit var apiService: APIService
    private var mvpView: MemberView? = null
    private var mRealm: RealmHelper<UserMembers>? = RealmHelper()
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    override var job: Job = Job()

    init {
        BaseApplication.applicationComponent.inject(this)
    }

    fun getMemberCache() {
        /* from Realm Model */
        val data: RealmResults<UserMembers>? = mRealm?.getData(UserMembers::class.java)

        //if (data == null) data = emptyList()

        mvpView?.onMemberCacheLoaded(data)
    }

    fun getMember(currentPage: Int?) {
        mCompositeDisposable?.add(
                apiService.getMembers(BuildConfig.BASE_URL_MEMBER, 10, currentPage!!)
                        .map {
                            saveToCache(it.arrayData, currentPage)
                            it
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ data ->
                            if (data != null) {
                                if (currentPage == 1) {
                                    if (data.arrayData?.isNotEmpty()!!) {
                                        mvpView?.onMemberLoaded(data.arrayData!!)
                                    } else {
                                        mvpView?.onMemberEmpty()
                                    }
                                } else {
                                    mvpView?.onMemberLoaded(data.arrayData!!)
                                }
                            } else {
                                mvpView?.onFailed(R.string.txt_error_global)
                            }
                        }, {
                            mvpView?.onFailed(it)
                        })
        )
    }

    fun getMemberCoro(currentPage: Int?) = launch(Dispatchers.Main) {
        val result = runCatching {
            apiService.getMembersCoro(BuildConfig.BASE_URL_MEMBER,10,currentPage!!).await()
        }.onSuccess {
            if (it.arrayData?.isNotEmpty()!!) {
                val rest = it.arrayData
                runCatching{
                    saveToCache(it.arrayData, currentPage)
                }.onSuccess {
                    mvpView?.onMemberLoaded(rest)
                }.onFailure {
                    mvpView?.onFailed(it)
                }
            } else {
                mvpView?.onMemberEmpty()
            }
        }.onFailure {
            mvpView?.onFailed(it)
        }
    }

    private fun saveToCache(data: List<UserMembers>?, currentPage: Int?) {
        if (data != null && data.isNotEmpty()) {
            if (currentPage == 1) {
                // remove current realm data
                mRealm?.deleteData(UserMembers())
            }

            // save to realm
            mRealm?.saveList(data)
        }
    }

    override fun onDestroy() {
        detachView()
    }

    override fun attachView(view: MemberView) {
        mvpView = view
        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (mvpView is LifecycleOwner) {
            (mvpView as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    override fun detachView() {
        mvpView = null
        mCompositeDisposable.let { mCompositeDisposable?.clear() }
        job.cancel()
    }
}