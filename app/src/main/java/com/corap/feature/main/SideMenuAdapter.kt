package com.corap.feature.main

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.corap.R
import com.corap.base.ui.adapter.BaseRecyclerAdapter
import com.corap.data.model.SideMenu

/**
 * Created by dodydmw19 on 1/3/19.
 */

class SideMenuAdapter(var context: Context?) : BaseRecyclerAdapter<SideMenu, SideMenuItemView>() {

    private var mOnActionListener: SideMenuItemView.OnActionListener? = null

    fun setOnActionListener(onActionListener: SideMenuItemView.OnActionListener) {
        mOnActionListener = onActionListener
    }

    var selectedItem = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemResourceLayout(): Int = R.layout.item_side_menu

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SideMenuItemView {
        val view = SideMenuItemView(getView(parent))
        mOnActionListener?.let { view.setOnActionListener(it) }
        return view
    }

    override fun onBindViewHolder(holder: SideMenuItemView, position: Int) {
        context?.let { ctx ->
            if (position == selectedItem) {
                holder.getTitleView().setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
            } else {
                holder.getTitleView().setTextColor(ContextCompat.getColor(ctx, R.color.black))
            }
        }
        super.onBindViewHolder(holder, position)
    }
}