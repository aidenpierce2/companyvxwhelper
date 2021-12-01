package com.xwq.companyvxwhelper.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.bean.dataBindingBean.LocationBean
import com.xwq.companyvxwhelper.callbackListener.RetryListener
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.utils.LogUtil
import java.util.*

abstract class BaseFragment<VB : ViewBinding, T : IBaseView, M : BaseModel<VB, T>> : RxFragment() , NoDoubleClickListener, RetryListener {

    var TAG : String = this::class.java.simpleName.toString()

    lateinit var mContext : RxAppCompatActivity
    lateinit var mFContainer : View
    lateinit var locationObserver : LocationObserver
    lateinit var locationObservable : MyApplication.LocationObservable
    lateinit var locationInterface : iLocationInterface
    private lateinit var dataBinding: VB

    override fun onAttach(activity: Activity) {
        LogUtil.log(TAG, "onAttach execute!")
        super.onAttach(activity)
    }

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
        dataBinding = DataBindingUtil.inflate(inflater, getContentViewId(), container,false)
        mFContainer = dataBinding!!.root
        return mFContainer
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        LogUtil.log(TAG, "onActivityCreated execute!")
        super.onActivityCreated(savedInstanceState)
        mContext = (activity as RxAppCompatActivity?)!!

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

    /**
     * 获取根布局id
     */
    abstract fun getContentViewId() : Int

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化data
     */
    abstract fun init()

    /**
     * 需不需要获取地址
     */
    abstract fun needLocation() : Boolean

    /**
     * 获取当前地址的接口
     */
    interface iLocationInterface{
        fun notifyLocation(Lon : Double, Lat : Double)
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