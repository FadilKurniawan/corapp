package com.corap.feature.main

import com.corap.base.presenter.MvpView
import com.corap.data.model.SideMenu

/**
 * Created by dodydmw19 on 1/3/19.
 */

interface SideMenuView : MvpView {

    fun onSideMenuLoaded(sideMenus: List<SideMenu>?)

    fun onFailedLoadSideMenu(message: String?)

}