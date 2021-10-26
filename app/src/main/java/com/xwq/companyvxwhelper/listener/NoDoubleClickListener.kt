package com.xwq.companyvxwhelper.listener

import android.view.View

interface NoDoubleClickListener : View.OnClickListener {

    companion object {
        // 两次点击最短时间间隔
        val CLICK_INTERNAL : Int = 500
        // 维护一个map
        var clickViewMap : HashMap<View, Long> = hashMapOf()
    }

    override fun onClick(v: View?) {
        var currentTimeMillis : Long = System.currentTimeMillis()

        if (clickViewMap.get(v) == null) {
            //直接触发
            clickViewMap.set(v!!, currentTimeMillis)
            noDoubleClick(v)
            return
        } else {
            var preTimeMillis : Long? = clickViewMap.get(v)
            if (currentTimeMillis - preTimeMillis!! > CLICK_INTERNAL) {
                // 处理点击事件 重新设置点击时间
                clickViewMap.set(v!!, currentTimeMillis)
                noDoubleClick(v)
                return
            }else {
                // 不处理点击事件
                return
            }
        }

    }

    fun noDoubleClick(v: View?) {

    }

}