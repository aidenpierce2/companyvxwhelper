package com.xwq.companyvxwhelper.base

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.Enum.PermissionArray
import com.xwq.companyvxwhelper.base.Enum.PermissionMode
import com.xwq.companyvxwhelper.callbackListener.RetryListener
import com.xwq.companyvxwhelper.databinding.ActivityBaseSettingBinding
import com.xwq.companyvxwhelper.databinding.ActivityBaseSettingBindingImpl
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.mvvm.dialog.IosAlertDialog
import com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment.LoadingDialog
import com.xwq.companyvxwhelper.permission.PermissionManager
import com.xwq.companyvxwhelper.utils.ToastUtil
import java.util.*
import kotlin.system.exitProcess


abstract class BaseActivity<VB : ViewBinding, T : IBaseView, M : BaseModel<VB, T>> : RxAppCompatActivity(),
    NoDoubleClickListener, IBaseView, RetryListener{

    val TAG : String = this::class.java.simpleName.toString()

    private var dataBinding : VB? = null
    var permissionManager : PermissionManager? = null
    // 需不需要实例化 看界面本身
    lateinit var locationObserver : iLocationObserver
    lateinit var locationObservable : MyApplication.LocationObservable
    lateinit private var curModel : M

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       dataBinding = DataBindingUtil.setContentView(this, getContentViewId())
        if (fullScreenEnable()) {
            showScreenFull(this)

            if (needSetStatusColor()) {
                setAndroidNativeLightStatusBar(this, isStatusBlack())
            }
        }

       curModel = getSelfModel()
        initBaseData()
        initView()
        initData()
        initListener()
        startRequest()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needLocation()) {
            if (locationObservable != null) {
                locationObservable.removeImpl(TAG)
            }
        }
    }


    override fun retryRequest() {

    }


    /**
     * 设置布局id
     */
    abstract fun getContentViewId() : Int

    /**
     * 是否设置全屏
     * return enable fullscreen true
     *        else false
     */
    abstract fun fullScreenEnable() : Boolean

    /**
     * 判断是否需要设置状态栏的颜色
     * return enable showDark true
     *        else false
     */
    abstract fun needSetStatusColor() : Boolean

    /**
     * 设置状态栏黑白色
     */
    abstract fun isStatusBlack() : Boolean

    /**
     * 初始化界面视图
     */
    abstract fun initView()

    /**
     * 初始化界面数据
     */
    abstract fun initData()

    /**
     * 设置点击监听
     */
    abstract fun initListener()

    /**
     * 开启网络请求
     */
    abstract fun startRequest()

    /**
     * 需不需要获取当前定位
     */
    abstract fun needLocation() : Boolean
    /**
     * 设置model
     */
    abstract fun getSelfModel() : M
    /**
     * base 方法本身的初始化
     */
    private fun initBaseData() {
        permissionManager = PermissionManager.instance
        // 取决于界面自己 需不需要获取当前定位
        if (needLocation()) {
            if ( !this@BaseActivity ::locationObserver.isInitialized) {
                locationObservable = MyApplication.app.getLocationObserver()
            }
            if ( !this@BaseActivity::locationObserver.isInitialized ) {
                locationObserver = iLocationObserver()
            }
            locationObservable.addImpl(TAG, locationObserver)
        }

    }

    /**
     * 全屏方法
     */
    private fun showScreenFull(activity: BaseActivity<VB, T, M>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                val window: Window = activity.window
                val decorView: View = window.getDecorView()
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        and View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                decorView.systemUiVisibility = option
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.setStatusBarColor(Color.TRANSPARENT)
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                val window: Window = activity.window
                val attributes: WindowManager.LayoutParams = window.getAttributes()
                val flagTranslucentStatus: Int = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                val flagTranslucentNavigation: Int = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                attributes.flags = attributes.flags or flagTranslucentStatus
                //                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes)
            }
        }
    }

    protected fun fitSystemWindow() {
        var contentView : ViewGroup = this.window.decorView.findViewById<View>(android.R.id.content) as ViewGroup
        if (contentView != null) {
            var parentView = contentView.getChildAt(0)

            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                parentView.fitsSystemWindows = true

                if(parentView is DrawerLayout) {
                    parentView.clipToPadding = false
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults == null || grantResults.size <= 0) {
            return
        }

        //存储权限对应
        if (grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
            // 通过
            permissionManager!!.notify(this, requestCode, true)
        }else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                var permissionMode : PermissionMode = permissionManager!!.checkIsNeeded(requestCode)
                var permissionName = permissionManager!!.getPermissionName(requestCode)
                var contentStr : String? = ""
                when (permissionName) {
                    PermissionArray.LOCATION -> {
                        contentStr = resources.getString(R.string.location_warning)
                    }
                    PermissionArray.MESSAGE -> {
                        contentStr = resources.getString(R.string.sms_warning)
                    }
                    PermissionArray.SDCARD -> {
                        contentStr = resources.getString(R.string.sdcard_warning)
                    }
                }
                if (permissionMode == PermissionMode.OPTIONAL) {
                    // 不管
                } else {
                    IosAlertDialog.instance
                        .setTitleACTV(resources.getString(R.string.alert_title))
                        .setContentACTV(contentStr!!)
                        .setCancelAble(false)
                        .setCancelACTV(resources.getString(R.string.cancel))
                        .setSureACTV(resources.getString(R.string.sure))
                        .observer(object : IosAlertDialog.OnSelectListener {
                            override fun onCancel(iosAlertDialog: IosAlertDialog) {
                                exit()
                            }

                            override fun onSure(iosAlertDialog: IosAlertDialog) {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri: Uri =
                                    Uri.fromParts("package", applicationContext.packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            }
                        })
                        .build(this)
                }
            } else {
                // 不通过
                permissionManager!!.notify(this, requestCode, false)
            }

        }
    }

    /**
     * 退出app
     */
    fun exit() {
        MyApplication.app!!.exit()
        exitProcess(0)
    }

    /**
     *
     */

    /**
     * 展示默认的loading activity需要可以自己重新写
     */
    fun showDefaultLoading() {

    }

    /**
     * 关闭默认的loading activity需要可以自己重新写
     */
    fun hideDefaultLoading() {

    }

    override fun showLoading() {
        LoadingDialog.getSingleton().build(this.supportFragmentManager, true)
    }

    override fun hideLoading() {
        LoadingDialog.getSingleton().disMiss()
    }

    override fun showToast(value: String) {
        ToastUtil.showToast(value)
    }

    fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
        val decor = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    fun getBinding() : VB{
        return dataBinding!!
    }

    class iLocationObserver : Observer {

        override fun update(o: Observable?, arg: Any?) {

        }

    }

    protected fun getModel() : M {
        return curModel
    }

    // 清空回退栈 到登录界面
    fun backToLogin() {

    }

}