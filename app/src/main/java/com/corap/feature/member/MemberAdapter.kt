package com.corap.feature.member

import android.view.ViewGroup
import com.corap.R
import com.corap.base.ui.adapter.BaseRecyclerAdapter
import com.corap.data.model.UserMembers

/**
 * Created by DODYDMW19 on 1/30/2018.
 */
class MemberAdapter : BaseRecyclerAdapter<UserMembers, MemberItemView>() {

    private var mOnActionListener: MemberItemView.OnActionListener? = null

    fun setOnActionListener(onActionListener: MemberItemView.OnActionListener) {
        mOnActionListener = onActionListener
    }

    override fun getItemResourceLayout(): Int  = R.layout.item_member

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MemberItemView{
        val view = MemberItemView(getView(parent))
        mOnActionListener?.let { view.setOnActionListener(it) }
        return view
    }

}