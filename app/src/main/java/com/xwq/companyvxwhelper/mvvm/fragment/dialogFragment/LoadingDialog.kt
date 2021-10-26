package com.xwq.companyvxwhelper.mvvm.fragment.dialogFragment

import android.app.Dialog
import android.graphics.drawable.AnimationDrawable
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.trello.rxlifecycle2.components.support.RxFragment
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseDialog
import com.xwq.companyvxwhelper.databindbean.LoadingDataBindBean
import com.xwq.companyvxwhelper.databinding.DialogLoadingBinding
import com.xwq.companyvxwhelper.utils.LogUtil
import kotlinx.android.synthetic.main.dialog_loading.*
import java.util.*

class LoadingDialog : BaseDialog() {

    // 最大网络加载时间10秒钟
    private val MAX_LOADING_TIME : Long = 10000
    var loadingACIV : AppCompatImageView? = null
    var timer : Timer? = Timer()

    companion object {
        // 不允许同时出现两个加载框
        private var instance : LoadingDialog? = null

        @Synchronized fun getSingleton() : LoadingDialog{
            if (this.instance == null) {
                this.instance = LoadingDialog()
            }
            return instance!!
        }
    }

    override fun setAnimation(dialog: Dialog) {

    }

    override fun setContentId(): Int {
        return R.layout.dialog_loading
    }

    override fun setBind() {
//        var bean : LoadingDataBindBean = LoadingDataBindBean(R.drawable.dialog_loading_animation)
//        binding.setVariable(BR.LoadingDataBindBean, bean)
    }

    override fun initView() {
        loadingACIV = dialog_loading_aciv_01
    }

    override fun initData() {
        // 启动animation

        var drawable : AnimationDrawable = loadingACIV!!.drawable as AnimationDrawable
        if (drawable != null) {
            drawable.start()
        }

        timer!!.schedule(object : TimerTask() {
            override fun run() {
                //超时 结束
                LogUtil.log(TAG, "disMiss() excute!")
                disMiss()
            }
        }, MAX_LOADING_TIME)
//        animation = AnimationUtils.loadAnimation(mContext, R.anim.animation_loading)
//        animation!!.repeatMode = Animation.INFINITE
//        animation!!.duration = 1000
//        var animationInterpolator : AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()
//        animation!!.interpolator = animationInterpolator
//        loadingACIV!!.animation = animation
//        loadingACIV!!.startAnimation(animation)
    }

    override fun initListener() {

    }

    override fun destroy() {
//        var drawable : AnimationDrawable = loadingACIV!!.drawable as AnimationDrawable
//        if (loadingACIV != null && drawable.isRunning) {
//            drawable.stop()
//        }
        loadingACIV!!.setImageDrawable(null);
        loadingACIV = null;

        if (timer != null) {
            timer!!.cancel()
        }
        instance = null
    }

    override fun dialogCancelAble(): Boolean {
        return false
    }

    override fun needEventBus(): Boolean {
        return false
    }

    @Synchronized fun build(fragment: Fragment, allowStateLose: Boolean) {
        if (fragment == null) {
            return
        }

        if (instance != null && instance!!.dialog != null && instance!!.dialog!!.isShowing) {
            // 还有个dialog正在运行
            return
        }
        if (allowStateLose) {
            showAllowStateLoss(fragment.childFragmentManager)
        } else {
            showNotAllowStateLoss(fragment.childFragmentManager)
        }
    }
}