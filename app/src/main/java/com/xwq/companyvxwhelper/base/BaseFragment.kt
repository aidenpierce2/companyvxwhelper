package com.xwq.companyvxwhelper.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.bean.dataBindingBean.LocationBean
import com.xwq.companyvxwhelper.callbackListener.RetryListener
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment.LoadingDialog
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.ToastUtil
import java.lang.reflect.Constructor
import java.lang.reflect.ParameterizedType
import java.util.*


abstract class BaseFragment<VB : ViewBinding, T : IBaseView, M : BaseFragmentModel<VB, T>> : RxFragment() , NoDoubleClickListener, RetryListener ,IBaseView{

    var TAG : String = this::class.java.simpleName.toString()

    lateinit var mFContainer : View
    lateinit var locationObserver : LocationObserver
    lateinit var locationObservable : MyApplication.LocationObservable
    lateinit var locationInterface : iLocationInterface
    private var selfView : T? = null
    private lateinit var dataBinding: VB
    private var selfModel : M? = null
    private var isDefault : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.log(TAG, "onCreate execute!")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtil.log(TAG, "onCreateView execute!")
        dataBinding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false)
        mFContainer = dataBinding!!.root
        selfView = this as T
        var tClass : Class<T> =
            (javaClass.genericSuperclass as ParameterizedType).getActualTypeArguments().get(1) as (Class<T>)
        var mClass : Class<M> =
            (javaClass.genericSuperclass as ParameterizedType).getActualTypeArguments().get(2) as (Class<M>)
        var constructor : Constructor<*> = mClass.getConstructor(tClass)
        selfModel = constructor.newInstance(selfView) as M;
        return mFContainer
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        LogUtil.log(TAG, "onActivityCreated execute!")
        super.onActivityCreated(savedInstanceState)

        initView(savedInstanceState)
        if (needLocation()) {
            locationInterface = this as iLocationInterface
            initLocation()
        }
        init()
    }

    override fun onStart() {
        LogUtil.log(TAG, "onStart execute!")
        super.onStart()
    }

    override fun onResume() {
        LogUtil.log(TAG, "onResume execute!")
        super.onResume()
    }

    override fun onPause() {
        LogUtil.log(TAG, "onPause execute!")
        super.onPause()
    }

    override fun onStop() {
        LogUtil.log(TAG, "onStop execute!")
        super.onStop()
    }

    override fun onDestroyView() {
        LogUtil.log(TAG, "onDestroyView execute!")
        super.onDestroyView()
        if (needLocation()) {

        }
    }

    override fun onDestroy() {
        LogUtil.log(TAG, "onDestroy execute!")
        super.onDestroy()
    }

    override fun onDetach() {
        LogUtil.log(TAG, "onDetach execute!")
        super.onDetach()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        LogUtil.log(TAG, "setUserVisibleHint: " + isVisibleToUser)
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        LogUtil.log(TAG, "onHiddenChanged: " + hidden)
        super.onHiddenChanged(hidden)
        //??????????????????????????????
        if (!hidden) {
            netWorkOperation()
            isDefault = false
        }

    }

    /**
     * ???????????????id
     */
    abstract fun getContentViewId() : Int

    /**
     * ?????????view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * ?????????data
     */
    abstract fun init()

    /**
     *  ????????????
     */
    abstract fun netWorkOperation()

    /**
     * ????????????????????????
     */
    abstract fun needLocation() : Boolean

    /**
     * ???????????????????????????
     */
    interface iLocationInterface{
        fun notifyLocation(Lon: Double, Lat: Double)
    }

    fun preInitLocation() : Boolean {
        var tag : Boolean = false
        if (!this@BaseFragment::locationObserver.isInitialized) {
            locationObserver = LocationObserver()
            tag =  false
        }
        if (!this@BaseFragment::locationObservable.isInitialized) {
            locationObservable = MyApplication.app.getLocationObserver()
            tag =  false
        }
        tag =  true
        return tag
    }

    fun initLocation() {
        preInitLocation()

        locationObservable.addImpl(TAG, locationObserver)
    }

    fun removeLocation() {
        if (preInitLocation()) {
            locationObservable.removeImpl(TAG)
        }
    }

    fun getBinding() : VB{
        return dataBinding!!
    }

    // ???????????????????????????????????????
    protected open fun forbidFullScreen() {
        var statusBarHeight2 = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            var newInstance = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height")
                .get(newInstance).toString())
            statusBarHeight2 = resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var contentView = (context as RxAppCompatActivity).window.decorView.findViewById<View>(android.R.id.content)
        if (contentView != null) {
            val childAt = (contentView as ViewGroup).getChildAt(0)
            if (childAt is DrawerLayout) {
                // ???????????? ??????????????????????????????
                val childAt1 = (childAt as ViewGroup).getChildAt(0)
                childAt1.setPadding(0, statusBarHeight2, 0, 0)
                return
            }
            childAt.setPadding(0, statusBarHeight2, 0, 0)
        }
    }

    fun setAndroidNativeLightStatusBar(context: Context?, dark: Boolean) {
        if (context == null) return
        val decor = (context as RxAppCompatActivity).window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    override fun showToast(value: String) {
        // ????????????????????? ????????????????????????
        ToastUtil.showToast(value)
    }

    fun getSelfModel() : M {
        return selfModel!!
    }
    override fun showLoading() {
        // ????????????????????? ????????????????????????
        LoadingDialog.getSingleton().build(this.childFragmentManager, false)
    }

    override fun hideLoading() {
        // ????????????????????? ????????????????????????
        LoadingDialog.getSingleton().disMiss()
    }

    inner class LocationObserver : Observer {

        override fun update(o: Observable?, arg: Any?) {
            if (!(arg is LocationBean)) {
                return
            }

            if (locationInterface != null) {
                locationInterface.notifyLocation(arg.lontitude, arg.latitude)
            }
        }
    }
}