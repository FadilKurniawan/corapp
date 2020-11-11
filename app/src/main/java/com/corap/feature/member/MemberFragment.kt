package com.corap.feature.member

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.corap.R
import com.corap.base.ui.BaseFragment
import com.corap.base.ui.recyclerview.BaseRecyclerView
import com.corap.data.model.ErrorCodeHelper
import com.corap.data.model.User
import com.corap.feature.member.MemberAdapter
import com.corap.feature.member.MemberItemView
import com.corap.feature.member.MemberPresenter
import com.corap.feature.member.MemberView
import com.jcodecraeer.xrecyclerview.XRecyclerView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_member.*
import kotlinx.android.synthetic.main.layout_base_shimmer.*

/**
 * Created by DODYDMW19 on 1/30/2018.
 */

class MemberFragment : BaseFragment(),
        MemberView,
        MemberItemView.OnActionListener {

    private var memberPresenter: MemberPresenter? = null
    private var currentPage: Int = 1
    private var memberAdapter: MemberAdapter? = MemberAdapter()

    companion object {
        fun newInstance(): BaseFragment? {
            return MemberFragment()
        }
    }

    override val resourceLayout: Int = com.corap.R.layout.fragment_member

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupProgressView()
        setupEmptyView()
        setupErrorView()
        setupList()
        Handler().postDelayed({ setupPresenter() }, 100)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearRecyclerView(rvMember)
    }

    private fun setupPresenter() {
        memberPresenter = MemberPresenter()
        memberPresenter?.attachView(this)
        memberPresenter?.getMemberCache()
        memberPresenter?.getMemberCoro(currentPage)
    }

    private fun setupList() {
        rvMember.apply {
            setUpAsList()
            setAdapter(memberAdapter)
            setPullToRefreshEnable(true)
            setLoadingMoreEnabled(true)
            setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onRefresh() {
                    currentPage = 1
                    loadData()
                }

                override fun onLoadMore() {
                    currentPage++
                    loadData()
                }
            })
        }
        memberAdapter?.setOnActionListener(this)
        rvMember.showShimmer()
    }

    private fun loadData(){
        memberPresenter?.getMemberCoro(currentPage)
    }

    private fun setData(data: List<User>?) {
        data?.let {
            if (currentPage == 1) {
                memberAdapter.let {
                    memberAdapter?.clear()
                }
            }
            memberAdapter?.add(it)
        }
        rvMember.stopShimmer()
        rvMember.showRecycler()
    }

    private fun setupProgressView() {
        R.layout.layout_shimmer_member.apply {
            viewStub.layoutResource = this
        }

        viewStub.inflate()
    }

    private fun setupEmptyView() {
        rvMember.setImageEmptyView(R.drawable.empty_state)
        rvMember.setTitleEmptyView(getString(R.string.txt_empty_member))
        rvMember.setContentEmptyView(getString(R.string.txt_empty_member_content))
        rvMember.setEmptyButtonListener(object : BaseRecyclerView.ReloadListener {

            override fun onClick(v: View?) {
                currentPage = 1
                loadData()
            }
        })
    }

    private fun setupErrorView() {
        rvMember.setImageErrorView(R.drawable.empty_state)
        rvMember.setTitleErrorView(getString(R.string.txt_error_no_internet_connection))
        rvMember.setContentErrorView(getString(R.string.txt_error_connection))
        rvMember.setErrorButtonListener(object : BaseRecyclerView.ReloadListener {

            override fun onClick(v: View?) {
                currentPage = 1
                loadData()
            }

        })
    }

    override fun onMemberCacheLoaded(members: RealmResults<User>?) {
        members?.let {
            if (it.isNotEmpty()) setData(members)
        }
        finishLoad(rvMember)
    }

    override fun onMemberLoaded(members: List<User>?) {
        members.let {
            if (members?.isNotEmpty()!!) setData(members)
        }
        finishLoad(rvMember)
    }

    override fun onMemberEmpty() {
        finishLoad(rvMember)
        rvMember.showEmpty()
    }

    override fun onFailed(error: Any?) {
        finishLoad(rvMember)
        rvMember.showEmpty()
        error?.let { ErrorCodeHelper.getErrorMessage(activity, it)?.let { msg -> showToast(msg) } }
    }

    override fun onClicked(view: MemberItemView?) {
        view?.getData()?.let {
            showToast(it.firstName.toString())
        }
    }


}