package com.xwq.companyvxwhelper.mvvm.activity

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserCheckInDBBean
import com.xwq.companyvxwhelper.bean.Enum.UserCheckInEnum
import com.xwq.companyvxwhelper.bean.WorkCycleItemBean
import com.xwq.companyvxwhelper.const.Const
import com.xwq.companyvxwhelper.mvvm.adapter.ChooseWorkCycleAdapter
import com.xwq.companyvxwhelper.mvvm.model.activity.ChooseWorkCycleModel
import com.xwq.companyvxwhelper.mvvm.view.activity.ChooseWorkCycleView
import com.xwq.companyvxwhelper.utils.CalculateTimeStampUtils
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.utils.TimeStampUtils
import com.xwq.companyvxwhelper.widget.SwitchButton
import kotlinx.android.synthetic.main.activity_base_setting.*

class ChooseWorkCycleActivity : BaseActivity<ChooseWorkCycleView, ChooseWorkCycleModel>() {

    lateinit var mainRCY : RecyclerView
    var dataList : ArrayList<WorkCycleItemBean> = arrayListOf()
    lateinit var chooseWorkCycleAdapter : ChooseWorkCycleAdapter

    override fun setContentViewId(): Int {
        return R.layout.activity_base_setting
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
        mainRCY = activity_base_setting_rcy_main
    }

    override fun initData() {
        dataList.add(WorkCycleItemBean(resources.getDrawable(R.mipmap.checkin), false, false, resources.getString(R.string.work_cycle_1)))
        dataList.add(WorkCycleItemBean(resources.getDrawable(R.mipmap.checkin), true, false, resources.getString(R.string.work_cycle_2)))
        dataList.add(WorkCycleItemBean(resources.getDrawable(R.mipmap.checkin), true, false, resources.getString(R.string.work_cycle_3)))
        dataList.add(WorkCycleItemBean(resources.getDrawable(R.mipmap.checkin), true, false, resources.getString(R.string.work_cycle_4)))

        chooseWorkCycleAdapter = ChooseWorkCycleAdapter(this@ChooseWorkCycleActivity, 2)
        chooseWorkCycleAdapter.dataList = dataList
        mainRCY.layoutManager  = GridLayoutManager(this@ChooseWorkCycleActivity, 2)
        mainRCY.adapter = chooseWorkCycleAdapter
    }

    override fun initListener() {
        chooseWorkCycleAdapter?.setOnClickAndCheck(object : ChooseWorkCycleAdapter.onClickAndCheckListener{
            override fun onItemClick(itemView: View, position: Int) {
                when (position) {
                    0 -> {
                        autoCreateCheckInBean(UserCheckInEnum.NINESIXFIVE)
                    }
                    1 -> {
                        autoCreateCheckInBean(UserCheckInEnum.NINESIXSIX)
                    }
                    2 -> {
                        autoCreateCheckInBean(UserCheckInEnum.NINENINESIX)
                    }
                    3 -> {
                        autoCreateCheckInBean(UserCheckInEnum.CUSTOMIZE)
                    }
                }
            }

            override fun onSwitchButtonCheck(switchButton: SwitchButton, isChecked: Boolean) {

            }
        })
    }

    override fun startRequest() {

    }

    override fun needLocation(): Boolean {
        return false
    }

    fun autoCreateCheckInBean(userCheckInEnum : UserCheckInEnum) {
        var chooseUserCheckInDBBean : UserCheckInDBBean? = null
        when (userCheckInEnum) {
            UserCheckInEnum.NINESIXFIVE -> {
                chooseUserCheckInDBBean = UserCheckInDBBean()
                var amCheckIn = CalculateTimeStampUtils.calculateRealCheckInTime(UserCheckInEnum.NINESIXFIVE, true)
                var pmCheckOut = CalculateTimeStampUtils.calculateRealCheckInTime(UserCheckInEnum.NINESIXFIVE, false)
                chooseUserCheckInDBBean.userUUid = SharePreferenceUtil.instance.getData(Const.USER_UUID)
                chooseUserCheckInDBBean.monAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.monPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.tueAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.tuePmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.wedAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.wedPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.thuAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.thuPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.friAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.friPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.satAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.satPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.sunAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.sunPMCheckOut = pmCheckOut

                chooseUserCheckInDBBean.monNeedCheck = true
                chooseUserCheckInDBBean.tueNeedCheck = true
                chooseUserCheckInDBBean.wedNeedCheck = true
                chooseUserCheckInDBBean.thuNeedCheck = true
                chooseUserCheckInDBBean.friNeedCheck = true
                chooseUserCheckInDBBean.satNeedCheck = false
                chooseUserCheckInDBBean.sunNeedCheck = false

                chooseUserCheckInDBBean.holidayNeedCheck = false
                chooseUserCheckInDBBean.big_smallWeek = false
                chooseUserCheckInDBBean.curWeekIsBig = false
                chooseUserCheckInDBBean.createTimeStamp = TimeStampUtils.getCurrentTime()
                chooseUserCheckInDBBean.updateTimeStamp = TimeStampUtils.getCurrentTime()
                chooseUserCheckInDBBean.isValid = true

            }
            UserCheckInEnum.NINESIXSIX -> {
                chooseUserCheckInDBBean = UserCheckInDBBean()
                var amCheckIn = CalculateTimeStampUtils.calculateRealCheckInTime(UserCheckInEnum.NINESIXSIX, true)
                var pmCheckOut = CalculateTimeStampUtils.calculateRealCheckInTime(UserCheckInEnum.NINESIXSIX, false)
                chooseUserCheckInDBBean.userUUid = SharePreferenceUtil.instance.getData(Const.USER_UUID)
                chooseUserCheckInDBBean.monAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.monPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.tueAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.tuePmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.wedAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.wedPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.thuAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.thuPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.friAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.friPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.satAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.satPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.sunAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.sunPMCheckOut = pmCheckOut

                chooseUserCheckInDBBean.monNeedCheck = true
                chooseUserCheckInDBBean.tueNeedCheck = true
                chooseUserCheckInDBBean.wedNeedCheck = true
                chooseUserCheckInDBBean.thuNeedCheck = true
                chooseUserCheckInDBBean.friNeedCheck = true
                chooseUserCheckInDBBean.satNeedCheck = true
                chooseUserCheckInDBBean.sunNeedCheck = false

                chooseUserCheckInDBBean.holidayNeedCheck = false
                chooseUserCheckInDBBean.big_smallWeek = false
                chooseUserCheckInDBBean.curWeekIsBig = false
                chooseUserCheckInDBBean.createTimeStamp = TimeStampUtils.getCurrentTime()
                chooseUserCheckInDBBean.updateTimeStamp = TimeStampUtils.getCurrentTime()
                chooseUserCheckInDBBean.isValid = true
            }
            UserCheckInEnum.NINENINESIX -> {
                chooseUserCheckInDBBean = UserCheckInDBBean()
                var amCheckIn = CalculateTimeStampUtils.calculateRealCheckInTime(UserCheckInEnum.NINENINESIX, true)
                var pmCheckOut = CalculateTimeStampUtils.calculateRealCheckInTime(UserCheckInEnum.NINENINESIX, false)
                chooseUserCheckInDBBean.userUUid = SharePreferenceUtil.instance.getData(Const.USER_UUID)
                chooseUserCheckInDBBean.monAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.monPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.tueAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.tuePmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.wedAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.wedPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.thuAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.thuPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.friAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.friPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.satAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.satPmCheckOut = pmCheckOut
                chooseUserCheckInDBBean.sunAmCheckIn = amCheckIn
                chooseUserCheckInDBBean.sunPMCheckOut = pmCheckOut

                chooseUserCheckInDBBean.monNeedCheck = true
                chooseUserCheckInDBBean.tueNeedCheck = true
                chooseUserCheckInDBBean.wedNeedCheck = true
                chooseUserCheckInDBBean.thuNeedCheck = true
                chooseUserCheckInDBBean.friNeedCheck = true
                chooseUserCheckInDBBean.satNeedCheck = true
                chooseUserCheckInDBBean.sunNeedCheck = false

                chooseUserCheckInDBBean.holidayNeedCheck = false
                chooseUserCheckInDBBean.big_smallWeek = false
                chooseUserCheckInDBBean.curWeekIsBig = false
                chooseUserCheckInDBBean.createTimeStamp = TimeStampUtils.getCurrentTime()
                chooseUserCheckInDBBean.updateTimeStamp = TimeStampUtils.getCurrentTime()
                chooseUserCheckInDBBean.isValid = true
            }
            UserCheckInEnum.CUSTOMIZE -> {
                //下一章 自己选取
            }
        }
    }

    override fun needEventBus(): Boolean {
        return false
    }
}