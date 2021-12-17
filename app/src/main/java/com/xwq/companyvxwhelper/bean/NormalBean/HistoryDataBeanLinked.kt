package com.xwq.companyvxwhelper.bean.NormalBean

import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.bean.Enum.EncryOrDecryEnum
import com.xwq.companyvxwhelper.bean.ResponseBean.HistoryResBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryBaseBean
import com.xwq.companyvxwhelper.bean.dataBindingBean.HistoryItemBean
import com.xwq.companyvxwhelper.utils.SignKeyUtils
import com.xwq.companyvxwhelper.utils.VerifyCodeUtils

// 此类仅在数据量不大的情况下 能够有效率的执行 数据量一旦过大 必定会非常消耗性能
class HistoryDataBeanLinked {

    var head : HistoryResBean.HistoryResDataBean? = null
    var size : Int = 0

    fun add(addedBean : HistoryResBean.HistoryResDataBean) {
        addInIndex(addedBean, 0)
    }

    //请注意 不适合很大链表 查找下标在插入数据的操作 数据量大会很影响性能
    fun addInIndex(addedBean : HistoryResBean.HistoryResDataBean,index : Int) {
        if (head == null || size <= 0) {
            head = addedBean
            return
        }

        var waitForAddedBean : HistoryResBean.HistoryResDataBean = HistoryResBean.HistoryResDataBean()
        for (i in 0..index) {
            waitForAddedBean = head!!.next!!
        }
        if (index + 1 < size){
            var tempBean = waitForAddedBean.next
            waitForAddedBean.next = addedBean
            addedBean.next = tempBean
        } else {
            waitForAddedBean.next = addedBean
        }
        size ++
    }

    //排序 并增加额外数据
    fun dateSerialForData() : HistoryDataBeanLinked {
        if (head == null) {
            throw KotlinNullPointerException()
        }

        var preHistoryResDataBean : HistoryResBean.HistoryResDataBean = head!!
        for (i in 1 until size) {
            var curHistoryResDataBean = preHistoryResDataBean.next
            var addTag = isNeededAdd(preHistoryResDataBean, curHistoryResDataBean!!)
            when (addTag) {
                -1 -> {
                    //err
                    throw KotlinNullPointerException()
                }
                0 -> {
                    //不需要
                }
                1 -> {
                    //添加一个
                    addDayData(preHistoryResDataBean, i)
                }
                2 -> {
                    //添加2两个
                    addMonthData(preHistoryResDataBean, i)
                    addDayData(preHistoryResDataBean, i + 1)
                }
            }
            preHistoryResDataBean = curHistoryResDataBean
        }
        return this
    }

    //判断是否需要添加
    fun isNeededAdd(preHistoryResDataBean : HistoryResBean.HistoryResDataBean, curHistoryResDataBean : HistoryResBean.HistoryResDataBean) : Int {
        var tag = 0
        if (preHistoryResDataBean == null || curHistoryResDataBean == null) {
            tag = -1
        }
        var preMonth = preHistoryResDataBean.monthStr
        var curMonth = curHistoryResDataBean.monthStr
        if (!preMonth.equals(curMonth)) {
            tag ++
        }
        var preDay = preHistoryResDataBean.dayStr
        var curDay = curHistoryResDataBean.dayStr
        if (!preDay.equals(curDay)) {
            tag ++
        }
        return tag
    }

    // 添加月份
    fun addMonthData(preHistoryResDataBean : HistoryResBean.HistoryResDataBean, index : Int) {
        var yearStr = preHistoryResDataBean.yearStr
        var monthStr = preHistoryResDataBean.monthStr
        var monthBean : HistoryResBean.HistoryResDataBean = HistoryResBean.HistoryResDataBean()
        monthBean.tag = yearStr + MyApplication.app.resources.getString(R.string.year) + " " + monthStr + MyApplication.app.resources.getString(R.string.month)
        monthBean.itemType = 2
        addInIndex(monthBean, index)
    }

    // 添加日期头
    fun addDayData(preHistoryResDataBean : HistoryResBean.HistoryResDataBean, index : Int) {
        var monthStr = preHistoryResDataBean.monthStr
        var dayStr = preHistoryResDataBean.dayStr
        var dayBean : HistoryResBean.HistoryResDataBean = HistoryResBean.HistoryResDataBean()
        dayBean.tag = monthStr + MyApplication.app.resources.getString(R.string.month) + " " + dayStr + MyApplication.app.resources.getString(R.string.day)
        dayBean.itemType = 1
        addInIndex(dayBean, index)
    }

    // 转换为数组
    fun translateToList() : List<HistoryItemBean>{
        if (this == null || this.head == null) {
            throw KotlinNullPointerException()
        }

        var enqueData : ArrayList<HistoryItemBean> = arrayListOf()
        var preAddHistoryItemBean : HistoryItemBean = HistoryItemBean()
        var curHistoryResDataBean = head
        for (i in 0 until size) {
            var itemType = curHistoryResDataBean!!.itemType
            when (itemType) {
                0,1-> {
                    //一级头 二级头
                    preAddHistoryItemBean.dateStr = curHistoryResDataBean.tag
                    preAddHistoryItemBean.itemType = curHistoryResDataBean.itemType

                    enqueData.add(preAddHistoryItemBean)
                }
                2 -> {
                    if (preAddHistoryItemBean.startWork) {
                        preAddHistoryItemBean.bgDrawable = MyApplication.app.resources.getDrawable(R.mipmap.startwork)
                    } else {
                        preAddHistoryItemBean.bgDrawable = MyApplication.app.resources.getDrawable(R.mipmap.knockoff)
                    }
                    if (preAddHistoryItemBean.success) {
                        preAddHistoryItemBean.statusDrwable = MyApplication.app.resources.getDrawable(R.mipmap.success)
                    } else {
                        preAddHistoryItemBean.statusDrwable = MyApplication.app.resources.getDrawable(R.mipmap.fail)
                    }
                    preAddHistoryItemBean.tickAddress = SignKeyUtils.encryOrDecryValue(curHistoryResDataBean.tickAddressEncry, EncryOrDecryEnum.DECRYPTION)
                    preAddHistoryItemBean.timeStamp = curHistoryResDataBean.tickTimeStamp
                    preAddHistoryItemBean.itemType = curHistoryResDataBean.itemType

                    enqueData.add(preAddHistoryItemBean)
                }
                else -> {

                }
            }
        }
        return enqueData
    }
}