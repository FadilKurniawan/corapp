package com.corap.feature.member

import android.annotation.SuppressLint
import android.view.View
import com.corap.base.ui.adapter.viewholder.BaseItemViewHolder
import com.corap.data.model.UserMembers
import kotlinx.android.synthetic.main.item_member.view.*

/**
 * Created by DODYDMW19 on 1/30/2018.
 */
class MemberItemView(itemView: View) : BaseItemViewHolder<UserMembers>(itemView) {

    private var mActionListener: OnActionListener? = null
    private var userMembers: UserMembers? = null

    @SuppressLint("SetTextI18n")
    override fun bind(data: UserMembers?) {
        data.let {
            // for get context = itemView.context

            this.userMembers = data
            itemView.imgMember.setImageURI(data?.avatar)
            itemView.txtMemberName.text = data?.firstName + " " + data?.lastName
            itemView.button.setOnClickListener {
                mActionListener?.onClicked(this)
            }

        }
    }

    fun getData(): UserMembers {
        return userMembers!!
    }

    fun setOnActionListener(listener: OnActionListener) {
        mActionListener = listener
    }

    interface OnActionListener {
        fun onClicked(view: MemberItemView?)
    }
}