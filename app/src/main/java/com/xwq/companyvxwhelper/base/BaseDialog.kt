package com.xwq.companyvxwhelper.base

import com.xwq.companyvxwhelper.R
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.xwq.companyvxwhelper.listener.NoDoubleClickListener
import com.xwq.companyvxwhelper.utils.LogUtil
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference
import java.lang.reflect.Field


abstract class BaseDialog : DialogFragment(), NoDoubleClickListener{

    val TAG : String = this::class.java.simpleName.toString()
    lateinit var mContextWeakRef : WeakReference<AppCompatActivity>
    lateinit var binding : ViewDataBinding
    lateinit var mContainerRef : WeakReference<View>
    protected var window : Window? = null
    lateinit protected var inflater : LayoutInflater
    var eventBus : EventBus? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.log(TAG, "onCreateView")
        mContextWeakRef = WeakReference<AppCompatActivity>(context as AppCompatActivity)
        this@BaseDialog.inflater = inflater

        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = DataBindingUtil.inflate(inflater, setContentId(), container,false)
        setBind()
        mContainerRef = WeakReference<View>(binding!!.root)
        return mContainerRef.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogUtil.log(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        this.dialog!!.setCancelable(dialogCancelAble())
        this.dialog!!.setCanceledOnTouchOutside(dialogCancelAble())

        eventBus = EventBus.getDefault()
        if (needEventBus()) {
            eventBus?.register(this)
        }
        initView()
        initData()
        initListener()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        LogUtil.log(TAG, "onCreateDialog")
        var dialog : Dialog = super.onCreateDialog(savedInstanceState)
        setAnimation(dialog)
        return dialog
    }

    override fun onStart() {
        LogUtil.log(TAG, "onStart")
        super.onStart()
        window = getDialog()!!.getWindow();
        var params : WindowManager.LayoutParams = window!!.getAttributes()!!;
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        window!!.setAttributes(params);
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

    }

    override fun onDestroy() {
        LogUtil.log(TAG, "onDestroy")
        super.onDestroy()
        //disMiss()
        eventBus?.unregister(this)
    }

    // 设置动画效果 可以要 可以不要
    abstract fun setAnimation(dialog : Dialog)

    abstract fun setContentId() : Int

    abstract fun setBind()

    abstract fun initView()

    abstract fun initData()

    abstract fun initListener()

    abstract fun destroy()

    abstract fun dialogCancelAble() : Boolean

    protected fun getDialogWindow() : Window{
        return mContextWeakRef.get()!!.window
    }

    abstract fun needEventBus() : Boolean

    fun showNotAllowStateLoss(manager: FragmentManager) {
        var fragmentTag = manager.findFragmentByTag(TAG)
        if (fragmentTag != null) {
            return
        }
        try {
            val c = Class.forName("androidx.fragment.app.DialogFragment")
            val con = c.getConstructor()
            val obj: Any = con.newInstance()
            val shownByMe: Field = c.getDeclaredField("mShownByMe")
            shownByMe.setAccessible(true)
            shownByMe.set(obj, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val ft: FragmentTransaction = manager.beginTransaction()

        ft.add(this, TAG)
        ft.commitNowAllowingStateLoss()
    }

    protected fun showAllowStateLoss(fragmentManager : FragmentManager) {
        var fragmentTag = fragmentManager.findFragmentByTag(TAG)
        if (fragmentTag != null) {
            return
        }
        try {
            val c = Class.forName("androidx.fragment.app.DialogFragment")
            val con = c.getConstructor()
            val obj: Any = con.newInstance()
            val shownByMe: Field = c.getDeclaredField("mShownByMe")
            shownByMe.setAccessible(true)
            shownByMe.set(obj, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.isAddToBackStackAllowed
        ft.add(this, TAG)
        ft.commitAllowingStateLoss()
    }

    fun disMiss() {
        if (this != null && !this.isRemoving) {
            LogUtil.log(TAG, "disMiss executed!")
            this.dismiss()
        }
    }

    override fun onDestroyView() {
        LogUtil.log(TAG, "onDestroyView")
        super.onDestroyView()
        binding.unbind()
        destroy()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        LogUtil.log(TAG, "onDismiss")
    }
}