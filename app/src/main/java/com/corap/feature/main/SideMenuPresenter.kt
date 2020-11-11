package com.corap.feature.main

import android.content.Context
import com.corap.base.presenter.BasePresenter
import com.corap.data.model.SideMenu
import com.corap.helper.CommonUtils
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job

/**
 * Created by dodydmw19 on 1/3/19.
 */

class SideMenuPresenter(var context: Context) : BasePresenter<SideMenuView> {

    private var mvpView: SideMenuView? = null
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    override var job: Job = Job()

    fun getMenuFromFile(){
        val response = CommonUtils.loadJSONFromAsset("menus.json", context)
        val gSon = Gson()
        val arrayJson = gSon.fromJson(response, Array<SideMenu>::class.java)
        val listJson = arrayJson.toList()
        mvpView?.onSideMenuLoaded(dataSelection(listJson))
    }

    private fun dataSelection(data: List<SideMenu>?) : List<SideMenu>{
        val dataFiltering: MutableList<SideMenu> = mutableListOf()
        data?.forEach { item ->
            if(item.status != 0){
                dataFiltering.add(item)
            }
        }
        return dataFiltering
    }



    override fun onDestroy() {
        detachView()
    }

    override fun attachView(view: SideMenuView) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
        mCompositeDisposable.let { mCompositeDisposable?.clear() }
    }

}