package com.corap.feature.member

import com.corap.base.presenter.MvpView
import com.corap.data.model.UserMembers
import io.realm.RealmResults

/**
 * Created by DODYDMW19 on 1/30/2018.
 */

interface MemberView : MvpView {

    fun onMemberCacheLoaded(members: RealmResults<UserMembers>?)

    fun onMemberLoaded(members: List<UserMembers>?)

    fun onMemberEmpty()

    fun onFailed(error: Any?)

}