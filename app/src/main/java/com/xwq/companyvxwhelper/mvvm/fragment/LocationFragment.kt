package com.xwq.companyvxwhelper.mvvm.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.LocationSource.OnLocationChangedListener
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.MyLocationStyle
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseFragment
import com.xwq.companyvxwhelper.bean.DataBaseBean.CompanyDBBean
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserClockInDBBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.DialogMainGuideBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryItemBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.LocationFragmentBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.SearchDialogItemBean
import com.xwq.companyvxwhelper.const.Const
import com.xwq.companyvxwhelper.databaseCenter.DatabaseManager
import com.xwq.companyvxwhelper.databinding.FragmentHistoryBinding
import com.xwq.companyvxwhelper.databinding.FragmentLocationBinding
import com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment.MainGuideDialog
import com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment.SearchLocationDialog
import com.xwq.companyvxwhelper.mvvm.model.fragment.LocationModel
import com.xwq.companyvxwhelper.mvvm.view.fragment.LocationView
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.PackageUtils
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.widget.HomeInputEditText


class LocationFragment : BaseFragment<FragmentLocationBinding, LocationView, LocationModel>(),
    BaseFragment.iLocationInterface,LocationSource, AMapLocationListener,PoiSearch.OnPoiSearchListener, LocationView{

    val STROKE_COLOR = Color.argb(0, 255, 255, 255)
    val FILL_COLOR = Color.argb(0, 255, 255, 255);

    lateinit var mapView : MapView
    lateinit var aMap : AMap
    var mainHIET : HomeInputEditText? = null
    private var mListener: OnLocationChangedListener? = null
    private var mlocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null

    private lateinit var hiet : HomeInputEditText
    private var locationCity : String? = ""
    private var locationStr : String? = ""

    private var curLatitude : Double? = 0.00
    private var curLontitude : Double? = 0.00

    // ??????????????????
    private var companyLatitude : Double = 0.00
    private var companyLontitude : Double = 0.00

    override fun retryRequest() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        if (!this::mapView.isInitialized) {
            mapView = getBinding().fragmentLocationMvMap
            mapView.onCreate(savedInstanceState)
        }
        if (!this::aMap.isInitialized) {
            aMap = mapView.map
        }
        if (!this::hiet.isInitialized) {
            hiet = getBinding().fragmentLocationHiet
        }

    }

    override fun init() {
        initPython()
//        startPythonCode()
        initCurrentLocation()

        if (!hasQIYEWXInstalled()) {
            showNoInstallDialog()
        }

        //???????????????????????????
        var userPhoneNum : String = SharePreferenceUtil.instance.getData(Const.USER_PHONENUM)
        userPhoneNum.let {
            if (userPhoneNum.isNullOrEmpty()) {
                // ?????? ????????????
                LogUtil.log(TAG, "showSetCompanyLocation 1")
                showSetCompanyLocation()
                return
            }
        }
        var userClockInDBBean : UserClockInDBBean? = DatabaseManager.getUserInfoInstance().getUserClockInByPhoneNum(
            userPhoneNum
        )
        userClockInDBBean.let {
            if (it == null) {
                // ??????  ????????????
                LogUtil.log(TAG, "showSetCompanyLocation 2")
                showSetCompanyLocation()
                return
            }
        }
        var companyDBBean : CompanyDBBean? = DatabaseManager.getCompanyInstance().getBeanByUuid(
            userClockInDBBean!!.userUUid
        )
        companyDBBean.let {
            if (it == null) {
                // ??????  ????????????
                LogUtil.log(TAG, "showSetCompanyLocation 3")
                showSetCompanyLocation()
                return
            }
        }

        //?????? ??????marker
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()

        mainHIET?.setOnButtonClickListener(object :
            HomeInputEditText.onButtonClickListener {
            override fun onButtonClick() {

                SearchLocationDialog.getSingleton()
                    .initContent(locationStr!!)
                    .initSearchCity(locationCity!!)
                    .initLaAndLon(curLatitude!!, curLontitude!!)
                    .setOnChooseListener(object : SearchLocationDialog.onChooseListener {

                        override fun onItemChoosed(itemBean: SearchDialogItemBean) {

                            ensureCompanyLocation(itemBean)
                        }
                    })
                    .build(context as RxAppCompatActivity, true)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        deactivate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        if (null != mlocationClient) {
            mlocationClient!!.onDestroy()
        }
    }

    override fun needLocation(): Boolean {
        return true
    }

    override fun notifyLocation(Lon: Double, Lat: Double) {
//        LogUtil.log(TAG, "Lon: " + Lon + "Lat: " + Lat)
        lateinit var latlon : LatLng
        if (Lon.toInt() == 0 || Lat.toInt() == 0) {
            latlon = LatLng(Const.DEAFULT_LATITUDE, Const.DEAFULT_LONTITUDE)
        } else {
            latlon = LatLng(Lat, Lon)
        }
    }

    override fun activate(listener: OnLocationChangedListener?) {
        LogUtil.log(TAG, "activate execute!")
        mListener = listener
        if (mlocationClient == null) {
            mlocationClient = AMapLocationClient(context)
            mLocationOption = AMapLocationClientOption()
            //??????????????????
            mlocationClient!!.setLocationListener(this)
            //??????????????????????????????
            mLocationOption!!.setLocationMode(AMapLocationMode.Hight_Accuracy)
            //??????????????????
            mlocationClient!!.setLocationOption(mLocationOption)
            // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
            // ??????????????????????????????????????????????????????????????????2000ms?????????????????????????????????stopLocation()???????????????????????????
            // ???????????????????????????????????????????????????onDestroy()??????
            // ?????????????????????????????????????????????????????????????????????stopLocation()???????????????????????????sdk???????????????
            mlocationClient!!.startLocation()
        }
    }

    override fun deactivate() {
        LogUtil.log(TAG, "deactivate execute!")
        mListener = null
        if (mlocationClient != null) {
            mlocationClient!!.stopLocation()
            mlocationClient!!.onDestroy()
        }
        mlocationClient = null
    }

    override fun onLocationChanged(p0: AMapLocation?) {
//        LogUtil.log(TAG, "onLocationChanged execute!")

        if (mListener != null && p0!!.errorCode == 0) {
            mListener!!.onLocationChanged(p0) // ?????????????????????
            aMap.moveCamera(CameraUpdateFactory.zoomTo(18f))

            curLatitude = p0.latitude
            curLontitude = p0.longitude

            locationCity = p0.city
            locationStr = p0.aoiName
            setLocation(p0.aoiName)
        }
    }

    override fun onPoiSearched(p0: PoiResult?, p1: Int) {
        LogUtil.log(p0!!.pois.toString(), " " + p1)
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    fun initCurrentLocation() {
        aMap.setLocationSource(this) // ??????????????????
        aMap.uiSettings.isMyLocationButtonEnabled = false // ????????????????????????????????????
        aMap.isMyLocationEnabled = true

        // ???????????????????????????
        var myLocationStyle = MyLocationStyle()
        // ???????????????????????????
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.userlocation))
        // ??????????????????????????????????????????
        myLocationStyle.strokeColor(STROKE_COLOR)
        //??????????????????????????????????????????

        myLocationStyle.strokeWidth(0f)
        // ???????????????????????????
        myLocationStyle.radiusFillColor(FILL_COLOR)
        // ??????????????? myLocationStyle ????????????????????????
        aMap.setMyLocationStyle(myLocationStyle)
    }

    fun setLocation(locationNickName: String) {
        var curBean : LocationFragmentBean = LocationFragmentBean()
        curBean.locationNickName = locationNickName
        getBinding().setVariable(BR.location, curBean)
    }

    // ?????????????????????????????????
    fun hasQIYEWXInstalled() : Boolean{
        var allInstallPackage = PackageUtils.getAllInstallPackage()

        for ((index, e) in allInstallPackage.withIndex()) {
            var packageName = e.activityInfo.packageName
            if (packageName.equals(Const.TARGET_PACKAGE_NAME)) {
                return true
            }
        }
        return false
    }

    // ????????????dialog
    fun showNoInstallDialog() {
        // ???????????????
        LogUtil.log(TAG, "1")
        var bean : DialogMainGuideBean = DialogMainGuideBean(
            R.string.not_install_app_content,
            R.string.not_install_install_now,
            R.string.not_install_install_later
        )

        MainGuideDialog.getSingleInstance().initContent(bean)
            .addClickCallBack(object : MainGuideDialog.IClickInterface {
                override fun onLeftChoosed(dialog: MainGuideDialog) {
                    dialog.disMiss()
                    downloadQiYEWX()
                }

                override fun onRightChoosed(dialog: MainGuideDialog) {
                    dialog.disMiss()
                }
            })
            .allowCancelAble(false)
            .show((context as RxAppCompatActivity).supportFragmentManager, true)
    }

    // ?????????????????????
    fun downloadQiYEWX() {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val content_url: Uri = Uri.parse(Const.DOWNLOAD_PATH)
        intent.data = content_url
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity")
        (context as RxAppCompatActivity).startActivity(intent)
    }

    // ?????????python
    fun initPython() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(MyApplication.app))
        }
    }

    fun startPythonCode() {
        var instance = Python.getInstance()
        instance.getModule("running").callAttr("__main__")
    }

    // ??????????????????
    fun showSetCompanyLocation() {
        // ???????????????
        LogUtil.log(TAG, "2")
        var bean : DialogMainGuideBean = DialogMainGuideBean(
            R.string.set_company_location_content,
            R.string.set_company_location_now,
            R.string.set_company_location_later
        )
        MainGuideDialog.getSingleInstance().initContent(bean)
            .addClickCallBack(object : MainGuideDialog.IClickInterface {
                override fun onLeftChoosed(dialog: MainGuideDialog) {
                    dialog.disMiss()
                    hiet.centerACET.isFocusable = true
                    hiet.centerACET.isFocusableInTouchMode = true
                    hiet.centerACET.requestFocus()
                    activity!!.getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }

                override fun onRightChoosed(dialog: MainGuideDialog) {
                    dialog.disMiss()
                }
            })
            .allowCancelAble(false)
            .show((context as RxAppCompatActivity).supportFragmentManager, true)
    }

    // ???????????????????????????
    fun ensureCompanyLocation(itemBean: SearchDialogItemBean) {

        var title = StringBuffer().append(resources.getString(R.string.pre_ensure_company_location)).append(itemBean.detailAddress).append(resources.getString(R.string.post_ensure_company_location)).toString()
        var bean : DialogMainGuideBean = DialogMainGuideBean(
            title,
            R.string.sure,
            R.string.cancel
        )
        MainGuideDialog.getSingleInstance().initContent(bean)
            .addClickCallBack(object : MainGuideDialog.IClickInterface {
                override fun onLeftChoosed(dialog: MainGuideDialog) {
                    dialog.disMiss()
                    // ???????????? ??????
                    var markerOption = MarkerOptions().icon(
                        BitmapDescriptorFactory
                            .fromView(layoutInflater.inflate(R.layout.company_icon, null))
                    )
                        .position(LatLng(itemBean.latitude, itemBean.lontitude))
                        .draggable(false)
                    aMap.addMarker(markerOption)
                }

                override fun onRightChoosed(dialog: MainGuideDialog) {
                    dialog.disMiss()
                }
            })
            .allowCancelAble(false)
            .show((context as RxAppCompatActivity).supportFragmentManager, true)
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_location
    }

    override fun netWorkOperation() {

    }

}