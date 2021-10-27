package com.xwq.companyvxwhelper.mvvm.activity

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.base.Enum.PermissionArray
import com.xwq.companyvxwhelper.base.Enum.PermissionMode
import com.xwq.companyvxwhelper.bean.RadioButtonBean
import com.xwq.companyvxwhelper.mvvm.fragment.HistoryFragment
import com.xwq.companyvxwhelper.mvvm.fragment.LocationFragment
import com.xwq.companyvxwhelper.mvvm.fragment.MyFragment
import com.xwq.companyvxwhelper.mvvm.model.activity.MainModel
import com.xwq.companyvxwhelper.mvvm.view.activity.MainView
import com.xwq.companyvxwhelper.widget.RedDotRadioButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainView, MainModel>(),MainView{

    var radioButtons : RadioButtonBean? = null
    val fragmentManager : FragmentManager by lazy {
        supportFragmentManager
    }
    var locationFragment : LocationFragment? = null
    var historyFragment : HistoryFragment? = null
    var myFragment : MyFragment? = null
    var curIndex : Int? = 0

    var mainFL : FrameLayout? = null
    var containerRG : RadioGroup? = null
    var locationRDRB : RedDotRadioButton? = null
    var historyRDRB : RedDotRadioButton? = null
    var mineRDRB : RedDotRadioButton? = null


    override fun setContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun fullScreenEnable(): Boolean {
        return true
    }

    override fun needSetStatusColor(): Boolean {
        return true
    }

    override fun isStatusBlack(): Boolean {
        return true
    }

    override fun initView() {
        initLayout()
        initFragment()
    }

    override fun initData() {
        permissionManager!!.request(this,PermissionArray.LOCATION,PermissionMode.REQUIRED)
        permissionManager!!.request(this,PermissionArray.SDCARD,PermissionMode.REQUIRED)

        var tv = TextView(this)
        var linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(100, 100)
        linearLayout.addView(tv)
        tv.layoutParams = linearLayout.layoutParams

        var measuredHeight = tv.measuredHeight
        var width = tv.width
        var measuredWidth = tv.measuredWidth
        var height = tv.height
    }

    override fun initListener() {
        containerRG!!.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                switchFragment(p1)
            }
        })
    }

    override fun startRequest() {

    }

    override fun onClick(p0: View?) {

    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is LocationFragment -> {
                locationFragment = fragment
            }
            is HistoryFragment -> {
                historyFragment = fragment
            }
            is MyFragment -> {
                myFragment = fragment
            }
        }
    }

    fun initLayout() {
        mainFL = activity_main_fl_container
        containerRG = activity_main_rg_container
        locationRDRB = activity_main_rdrb_location
        historyRDRB = activity_main_rdrb_history
        mineRDRB = activity_main_rdrb_mine
    }

    fun initFragment() {
        if (radioButtons == null) {
            radioButtons = RadioButtonBean(arrayListOf<RadioButtonBean.RadioButtonItem>(), 1)
        }
        fragmentManager.beginTransaction().apply {
            LocationFragment().let {
                locationFragment = it
                this.add(R.id.activity_main_fl_container, locationFragment!!,it.TAG)
                (radioButtons!!.items as ArrayList).add(RadioButtonBean.RadioButtonItem(resources.getString(R.string.homepage),
                    it.TAG,
                    resources.getDrawable(R.mipmap.location_un_choosed),
                    ""
                ))
            }
            HistoryFragment().let {
                historyFragment = it
                this.add(R.id.activity_main_fl_container, historyFragment!!,it.TAG)
                (radioButtons!!.items as ArrayList).add(RadioButtonBean.RadioButtonItem(resources.getString(R.string.history),
                    it.TAG,
                    resources.getDrawable(R.mipmap.history_un_choose),
                    ""
                ))
            }
            MyFragment().let {
                myFragment = it
                this.add(R.id.activity_main_fl_container, myFragment!!,it.TAG)
                (radioButtons!!.items as ArrayList).add(RadioButtonBean.RadioButtonItem(resources.getString(R.string.mine),
                    it.TAG,
                    resources.getDrawable(R.mipmap.mine_un_checked),
                    ""
                ))
            }
            hideFragments(this)
        }.commitAllowingStateLoss()
        switchFragment(R.id.activity_main_rdrb_location)
    }

    fun hideFragments(transaction : FragmentTransaction) {
        fragmentManager?.let {
            locationFragment?.let {
                transaction.hide(it)
            }
            historyFragment?.let {
                transaction.hide(it)
            }
            myFragment?.let {
                transaction.hide(it)
            }
        }
    }

    fun switchFragment(index : Int) {
        radioButtons?.let {
            it.items?.let {
                curIndex = index
                fragmentManager.beginTransaction().apply {
                    hideFragments(this)
                    when (curIndex) {
                        R.id.activity_main_rdrb_location -> {
                            locationFragment?.let {
                                this.show(it).commit()
                                locationRDRB?.let {
                                    if (!it.isChecked) {
                                        it.isChecked = true
                                    }
                                }
                            }
                        }
                        R.id.activity_main_rdrb_history -> {
                            historyFragment?.let {
                                    this.show(it).commit()
                                    historyRDRB?.let {
                                        if (!it.isChecked) {
                                            it.isChecked = true
                                        }
                                    }
                            }
                        }
                        R.id.activity_main_rdrb_mine -> {
                            myFragment?.let {
                                this.show(it).commit()
                                mineRDRB?.let {
                                    if (!it.isChecked) {
                                        it.isChecked = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun needLocation(): Boolean {
        return false
    }

    override fun needEventBus(): Boolean {
        return false
    }

    override fun getSelfModel(): MainModel {
        return MainModel(this@MainActivity)
    }

    override fun showToast(value: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

}