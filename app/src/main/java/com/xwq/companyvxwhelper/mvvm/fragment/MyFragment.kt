package com.xwq.companyvxwhelper.mvvm.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xwq.companyvxwhelper.BR
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseFragment
import com.xwq.companyvxwhelper.bean.dataBindingBean.FunctionItemBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.MyFragmentBean
import com.xwq.companyvxwhelper.const.Const
import com.xwq.companyvxwhelper.databinding.FragmentMineBinding
import com.xwq.companyvxwhelper.mvvm.activity.InformationSettingActivity
import com.xwq.companyvxwhelper.mvvm.activity.SettingActivity
import com.xwq.companyvxwhelper.mvvm.adapter.FunctionAdapter
import com.xwq.companyvxwhelper.mvvm.adapter.decoration.RevenueSummaryItemDecoration
import com.xwq.companyvxwhelper.mvvm.model.fragment.MyModel
import com.xwq.companyvxwhelper.mvvm.view.fragment.MyView
import com.xwq.companyvxwhelper.publicinterface.ObservableInterface
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.widget.MineLocationPullView
import com.xwq.companyvxwhelper.widget.RoundImageView

class MyFragment : BaseFragment<FragmentMineBinding, MyView, MyModel>(), ObservableInterface{

    lateinit private var myFragmentBean : MyFragmentBean
    lateinit private var mineMLPV : MineLocationPullView
    lateinit private var mainCard : CardView
    lateinit private var mainCSTL : ConstraintLayout
    lateinit private var roundIV : RoundImageView
    lateinit private var nickNameTV : AppCompatTextView
    lateinit private var telephoneTV : AppCompatTextView
    lateinit private var functionRCY : RecyclerView

    companion object {
        @BindingAdapter("webImageUrl")
        @JvmStatic
        fun loadWebImage(roundImageView: RoundImageView, imageUrl : String) {
            Glide.with(MyApplication.app).load(imageUrl).into(roundImageView)
        }
    }

    var functionAdapter : FunctionAdapter? = null

    override fun notifyDataChange(vararg data: Any) {
        super.notifyDataChange(*data)

        var distance = ((data.get(0) as Float) - mainCard.measuredHeight / 2).toInt()
        var mainLayoutParams = mainCSTL.layoutParams
        (mainLayoutParams as ConstraintLayout.LayoutParams).setMargins(0,
            distance, 0 ,0)
        mainCSTL.layoutParams = mainLayoutParams
    }

    override fun onClick(p0: View?) {

    }

    override fun retryRequest() {

    }

    override fun onResume() {
        super.onResume()
        LogUtil.log(TAG, "MyFragment onResume()")

        mFContainer.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                // 吧touch事件传递下去
                return true
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        mineMLPV = getBinding().fragmentMineMlpv
        mainCSTL = getBinding().fragmentMineMainContainer
        mainCard = getBinding().fragmentMineMainCard
        roundIV = getBinding().fragmentMineRivMain
        nickNameTV = getBinding().fragmentMineQctvNickname
        telephoneTV = getBinding().fragmentMineQctvTelephone
        functionRCY = getBinding().fragmentMineMainRcv

    }

    override fun init() {
        myFragmentBean = MyFragmentBean("", "", "", null, arrayOf<FunctionItemBean>())
        // 测试图片地址
        myFragmentBean.headImgUrl = "https://img2.baidu.com/it/u=4001462169,2041869795&fm=26&fmt=auto&gp=0.jpg"
        myFragmentBean.nickName = "喜洋洋2021"
        myFragmentBean.userPhone = "139****3217"
        myFragmentBean.rightDrawable = resources.getDrawable(R.mipmap.right_enter)
        getBinding().setVariable(BR.my, myFragmentBean)

        mineMLPV.addObservable(this@MyFragment)

        var dataList : ArrayList<FunctionItemBean> = arrayListOf()
        dataList.add(FunctionItemBean(resources.getDrawable(R.mipmap.setting), resources.getString(R.string.information_setting)))
        dataList.add(FunctionItemBean(resources.getDrawable(R.mipmap.checkin), resources.getString(R.string.check_in_setting)))
        dataList.add(FunctionItemBean(resources.getDrawable(R.mipmap.testfunction), resources.getString(R.string.simulate_test)))
        dataList.add(FunctionItemBean(resources.getDrawable(R.mipmap.feedback), resources.getString(R.string.submit_feedback)))
        dataList.add(FunctionItemBean(resources.getDrawable(R.mipmap.aboutus), resources.getString(R.string.about_us)))

        functionAdapter = FunctionAdapter(mContext)
        functionAdapter?.dataList = dataList

        functionRCY.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        functionRCY.adapter = functionAdapter

        functionAdapter?.setOnItemClickListener(object : FunctionAdapter.onChooseItemListener {
            override fun onChooseItem(data: FunctionItemBean, postion: Int) {
                if (SharePreferenceUtil.instance.getData(Const.PULLDOWN_CAN_CLICK, true)) {
                    when (postion) {
                        0 -> {startActivity(Intent().setClass(mContext, InformationSettingActivity::class.java))}
                        1 -> {startActivity(Intent().setClass(mContext, InformationSettingActivity::class.java))}
                        2 -> {}
                        3 -> {}
                        4 -> {}
                    }
                }
            }
        })
        getBinding().setVariable(BR.MyFragment, this)
    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mineMLPV.removeObservable(this@MyFragment)
    }

    fun toSetting() {
        startActivity(Intent().setClass(context, SettingActivity::class.java))
    }

    override fun getContentViewId(): Int {
        return R.layout.fragment_mine
    }
}