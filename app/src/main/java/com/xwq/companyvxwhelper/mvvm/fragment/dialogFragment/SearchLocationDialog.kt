package com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseDialog
import com.xwq.companyvxwhelper.bean.dataBindingBean.SearchDialogBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.SearchDialogItemBean
import com.xwq.companyvxwhelper.databinding.DialogSearchLocationBinding
import com.xwq.companyvxwhelper.mvvm.adapter.SearchAdapter
import com.xwq.companyvxwhelper.mvvm.adapter.decoration.RevenueSummaryItemDecoration
import com.xwq.companyvxwhelper.utils.DistanceUtils
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.ToastUtil
import java.util.ArrayList

class SearchLocationDialog : BaseDialog<DialogSearchLocationBinding>(), OnPoiSearchListener {

    var defSearchContent : String = ""
    var searchContent : String = ""
    var defLontitude : Double? = 0.00
    var defLatitude : Double? = 0.00
    var cityStr : String = ""
    lateinit var onChooseListenr : onChooseListener
    var mPoiSearchQuery : PoiSearch.Query? = null
    private var searchAdapter : SearchAdapter? = null
    var searchACET : AppCompatEditText? = null
    var mainRcy : RecyclerView? = null

    companion object {
        private var singleInstance : SearchLocationDialog? = null

        @Synchronized fun getSingleton() : SearchLocationDialog{
            if (singleInstance == null) {
                this.singleInstance = SearchLocationDialog()
            }
            return singleInstance!!
        }
    }

    override fun setAnimation(dialog: Dialog) {
        dialog.window.attributes.windowAnimations = R.style.search_dialog_animation
    }

    override fun setBind() {

    }

    override fun initView() {

    }

    override fun initData() {
        getBinding().setVariable(BR.SearchLocation, singleInstance)
        var searchDialogDataBean : SearchDialogBean = SearchDialogBean(defSearchContent)
        getBinding().setVariable(BR.SearchDialogBean, searchDialogDataBean)

        LogUtil.log(TAG, "ready to show LoadingDialog")
        LoadingDialog.getSingleton()
            .build(mContextWeakRef.get()!!.supportFragmentManager, true)

        searchContent = defSearchContent
        searchLocation(searchContent)
    }

    override fun initListener() {

    }

    override fun destroy() {
        // ??????????????????
        singleInstance = null
        searchAdapter!!.viewDataBinding.unbind()
    }

    override fun dialogCancelAble(): Boolean {
        return false
    }

    override fun onPoiSearched(p0: PoiResult?, p1: Int) {
        LogUtil.log(TAG, "onPoiSearched")

        LoadingDialog.getSingleton().disMiss()
        if (p1 == AMapException.CODE_AMAP_SUCCESS) {
            if (p0 != null && p0.query != null) {
                if (p0.query.equals(mPoiSearchQuery)) {
                    var pois = p0.pois
                    var data : Array<SearchDialogItemBean> =
                        convertPoiItemToSearchDialogItemBean(pois)

                    mainRcy?.layoutManager = LinearLayoutManager(mContextWeakRef.get(), RecyclerView.VERTICAL, false)
                    if (singleInstance!!.searchAdapter == null) {
                        searchAdapter = SearchAdapter()
                        searchAdapter!!.setData(data)
                        searchAdapter!!.setOnChooseItemListener(object : SearchAdapter.onChooseItemListener{
                            override fun onChooseItem(data: SearchDialogItemBean, postion: Int) {
                                // ???????????? ????????????
                                onChooseListenr.onItemChoosed(data)
                                disMiss()
                            }
                        })

                        mainRcy?.adapter = searchAdapter
                        mainRcy?.addItemDecoration(RevenueSummaryItemDecoration(mContextWeakRef.get()!!, RevenueSummaryItemDecoration.VERTICAL_LIST))
                    } else {
                        mainRcy?.adapter = searchAdapter
                        searchAdapter!!.setData(data)
                        searchAdapter!!.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    fun initContent(searchContent: String) : SearchLocationDialog{
        this@SearchLocationDialog.defSearchContent = searchContent
        return singleInstance!!
    }

    fun initSearchCity(cityStr: String) : SearchLocationDialog{
        this@SearchLocationDialog.cityStr = cityStr
        return singleInstance!!
    }

    fun initLaAndLon(defLatitude : Double, defLontitude: Double) : SearchLocationDialog{
        this@SearchLocationDialog.defLatitude = defLatitude
        this@SearchLocationDialog.defLontitude = defLontitude
        return singleInstance!!
    }

    fun setOnChooseListener(listener: onChooseListener) : SearchLocationDialog{
        this@SearchLocationDialog.onChooseListenr = listener

        return singleInstance!!
    }

    fun build(appCompatActivity: AppCompatActivity, allowStateLose: Boolean) {
        if (appCompatActivity == null) {
            return
        }

        if (allowStateLose) {
            showAllowStateLoss(appCompatActivity.supportFragmentManager)
        } else {
            showNotAllowStateLoss(appCompatActivity.supportFragmentManager)
        }
    }

    fun onViewClick(v: View) {
        var id = v.id
        when (id) {
            R.id.dialog_search_location_aciv_back -> {
                singleInstance == null
                disMiss()
            }
            R.id.dialog_search_location_actv_input -> {
                //????????????
                searchContent = searchACET?.text!!.trim().toString()
                if (searchContent == null || searchContent.isEmpty()) {
                    ToastUtil.showToast(R.string.need_input_search_value)
                    return
                } else if (searchContent.equals(defSearchContent)) {
                    ToastUtil.showToast(R.string.need_input_search_value)
                    return
                }
                searchLocation(searchContent)
            }
        }
    }

    fun searchLocation(searchValue : String) {
        mPoiSearchQuery = PoiSearch.Query(searchValue, "", cityStr)
        mPoiSearchQuery!!.requireSubPois(true) //true ??????????????????POI????????????; false
        mPoiSearchQuery!!.pageSize = 10
        mPoiSearchQuery!!.pageNum = 0
        val poiSearch = PoiSearch(mContextWeakRef.get(), mPoiSearchQuery)
        poiSearch.setOnPoiSearchListener(this)
        poiSearch.searchPOIAsyn()
    }

    fun convertPoiItemToSearchDialogItemBean(pois : ArrayList<PoiItem>) : Array<SearchDialogItemBean>{
        var data = arrayListOf<SearchDialogItemBean>()
        if (pois == null || pois.isEmpty()) {
            return data.toTypedArray()
        }

        for ((index, e) in pois.withIndex()) {
            var curDataBean = SearchDialogItemBean()
            if (e.title.isEmpty()) {
                continue
            }
            if (e.title.equals(searchContent)) {
                curDataBean.posIcon = resources.getDrawable(R.mipmap.search)
            } else {
                curDataBean.posIcon = resources.getDrawable(R.mipmap.location)
            }
            curDataBean.latitude = e.latLonPoint.latitude
            curDataBean.lontitude = e.latLonPoint.longitude
            curDataBean.nickName = e.title
            curDataBean.detailAddress = e.snippet
            curDataBean.locIcon = resources.getDrawable(R.mipmap.distance)
            curDataBean.facilityType = e.poiId
            curDataBean.province = e.provinceName
            curDataBean.city = e.cityName
            curDataBean.county = e.adName
            curDataBean.distance = DistanceUtils.getDistanceKm(e.latLonPoint.longitude, e.latLonPoint.latitude, defLontitude!!, defLatitude!!)

            data.add(curDataBean)
        }

        return data.toTypedArray()
    }

    interface onChooseListener {
        fun onItemChoosed(itemBean: SearchDialogItemBean)
    }

    override fun getContentViewId(): Int {
        return R.layout.adapter_search
    }

}