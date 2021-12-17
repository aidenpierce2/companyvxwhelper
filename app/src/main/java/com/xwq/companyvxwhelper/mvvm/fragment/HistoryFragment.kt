package com.xwq.companyvxwhelper.mvvm.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseFragment
import com.xwq.companyvxwhelper.bean.Enum.EncryOrDecryEnum
import com.xwq.companyvxwhelper.bean.NormalBean.HistoryDataBeanLinked
import com.xwq.companyvxwhelper.bean.RequestBean.HistoryReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.HistoryResBean
import com.xwq.companyvxwhelper.mvvm.model.fragment.HistoryModel
import com.xwq.companyvxwhelper.mvvm.view.fragment.HistoryView
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryDataBean
import com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment.HistoryMenuDialog
import com.xwq.companyvxwhelper.utils.HistoryChooseTimeUtils
import com.xwq.companyvxwhelper.utils.SignKeyUtils
import com.xwq.companyvxwhelper.bean.dataBindingBean.DialogHistoryMenuBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryItemBean
import com.xwq.companyvxwhelper.databinding.FragmentHistoryBinding
import com.xwq.companyvxwhelper.mvvm.adapter.HistoryAdapter

class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryView, HistoryModel>(), HistoryView{

    val limitNum : Int = 10
    lateinit var mainSRFL : SmartRefreshLayout
    lateinit var mainRCY : RecyclerView
    lateinit var menuCSTL : ConstraintLayout
    lateinit var emptyACIV : AppCompatImageView
    lateinit var emptyACTV : AppCompatTextView

    var historyReqBean : HistoryReqBean = HistoryReqBean()
    var startTimeStr : String = ""
    var endTimeStr : String = ""
    var chooStatus : Int = 0
    var pageNum : Int = 1
    var dialogHistoryMenuBean : DialogHistoryMenuBean? = null
    var dataList : List<HistoryItemBean> = arrayListOf()
    var historyAdapter : HistoryAdapter? = null
    var isRefresh : Boolean = true

    override fun onClick(p0: View?) {

    }

    override fun retryRequest() {

    }

    override fun onResume() {
        super.onResume()
        LogUtil.log(TAG, "HistoryFragment onResume()")
    }

    override fun initView(savedInstanceState: Bundle?) {
        mainSRFL = getBinding().fragmentHistorySrfl
        mainRCY = getBinding().fragmentHistoryRcyMain
        menuCSTL = getBinding().fragmentHistoryCstlMenu
        emptyACIV = getBinding().fragmentHistoryAcivEmpty
        emptyACTV = getBinding().fragmentHistoryActvEmpty

        mainSRFL.setRefreshHeader(MaterialHeader(context))
        mainSRFL.setEnableRefresh(true)
        mainSRFL.setEnableLoadMore(true)
        mainSRFL.setEnableAutoLoadMore(true)
        mainSRFL.setEnableLoadMoreWhenContentNotFull(true)
        mainSRFL.setEnableOverScrollBounce(true)
        mainSRFL.setEnableOverScrollDrag(true)

        mainSRFL.setOnRefreshListener{
            isRefresh = true
            requestHistoryReqBean(false)
        }
        mainSRFL.setOnLoadMoreListener {
            isRefresh = false
            pageNum ++
            requestHistoryReqBean(false)
        }
    }

    override fun init() {
        startTimeStr = HistoryChooseTimeUtils.getStartDay()
        endTimeStr = HistoryChooseTimeUtils.getEndDay()
        getBinding().setVariable(BR.HistoryFragment, this)
        getBinding().setVariable(BR.HistoryDataBean, HistoryDataBean(resources.getDrawable(R.mipmap.menu)))
    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_history
    }

    fun clickMenu() {
        HistoryMenuDialog.getSingleton().build((context as RxAppCompatActivity).supportFragmentManager, true)
        registerDialogMenuListener()
        dispatchMenuVisible(false)
    }

    fun registerDialogMenuListener() {
        HistoryMenuDialog.getSingleton().viewClickListener = object : HistoryMenuDialog.ViewClickListener {
            override fun onCancel() {
                dispatchMenuVisible(true)
            }

            override fun onEnsure( dialogHistoryMenuBean : DialogHistoryMenuBean, dataChanged : Boolean) {
                dispatchMenuVisible(true)
                this@HistoryFragment.dialogHistoryMenuBean = dialogHistoryMenuBean
                pageNum = 1
                requestHistoryReqBean(false)
            }
        }
    }

    override fun getHistoryDataSucc(data: HistoryResBean?) {
        dispatchRefreshOrLoadMore(isRefresh)
        var historyResDataBeanList = data?.historyResDataBeanList
        if (historyResDataBeanList.isNullOrEmpty()) {
            // 展示空白界面
            dispatchDataVisible(false)
            mainSRFL.finishLoadMoreWithNoMoreData()

        } else {
            dispatchDataVisible(true)
            translateListToLinked(historyResDataBeanList)

            historyAdapter = HistoryAdapter(requireContext())
            historyAdapter?.data = this.dataList
            mainRCY.adapter = historyAdapter
            mainRCY.layoutManager = LinearLayoutManager(MyApplication.app, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun getHistoryDataFail(data: String) {
        dispatchRefreshOrLoadMore(isRefresh)
        showToast(resources.getString(R.string.web_err))
        dispatchDataVisible(false)
    }

    override fun showToast(value: String) {
        super.showToast(value)
    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
    }

    fun requestHistoryReqBean(isDefault : Boolean) {
        historyReqBean.limit = limitNum
        historyReqBean.page = pageNum
        if (isDefault) {
            historyReqBean.startTimeEncrypt = SignKeyUtils.encryOrDecryValue(startTimeStr, EncryOrDecryEnum.ENCRYPTION, "")
            historyReqBean.endTimeEncrypt = SignKeyUtils.encryOrDecryValue(endTimeStr, EncryOrDecryEnum.ENCRYPTION, "")
            historyReqBean.chooseStatus = chooStatus
        } else {
            historyReqBean.startTimeEncrypt = SignKeyUtils.encryOrDecryValue(dialogHistoryMenuBean!!.startTimeValue, EncryOrDecryEnum.ENCRYPTION, "")
            historyReqBean.endTimeEncrypt = SignKeyUtils.encryOrDecryValue(dialogHistoryMenuBean!!.endTimeValue, EncryOrDecryEnum.ENCRYPTION, "")
            historyReqBean.chooseStatus = chooStatus
        }
        getSelfModel().getHistoryData(historyReqBean)
    }

    fun translateListToLinked(dataList : List<HistoryResBean.HistoryResDataBean>) {
        if (dataList.isNullOrEmpty()) {
            throw KotlinNullPointerException()
        }
        var preCurrentTimeMillis = System.currentTimeMillis()
        var resultBean : HistoryDataBeanLinked = HistoryDataBeanLinked()
        for ((index, item) in dataList.withIndex()) {
            resultBean.addInIndex(item, index)
        }
        resultBean = resultBean.dateSerialForData()
        this.dataList = resultBean.translateToList()
        var posCurrentTimeMillis = System.currentTimeMillis()
        LogUtil.log(TAG, "处理数据耗时: " + (posCurrentTimeMillis - preCurrentTimeMillis))
    }

    fun dispatchDataVisible(visible : Boolean) {
        if (visible) {
            mainRCY.visibility = View.VISIBLE
            emptyACIV.visibility = View.GONE
            emptyACTV.visibility = View.GONE
        } else {
            mainRCY.visibility = View.GONE
            emptyACIV.visibility = View.VISIBLE
            emptyACTV.visibility = View.VISIBLE
        }
    }

    fun dispatchMenuVisible(visible: Boolean) {
        if (visible) {
            menuCSTL.visibility = View.VISIBLE
        } else {
            menuCSTL.visibility = View.GONE
        }
    }

    fun dispatchRefreshOrLoadMore(isRefresh: Boolean) {
        if (isRefresh) {
            mainSRFL.finishRefresh()
        } else {
            mainSRFL.finishLoadMore()
        }
    }

    override fun netWorkOperation() {
        requestHistoryReqBean(true)
    }
}