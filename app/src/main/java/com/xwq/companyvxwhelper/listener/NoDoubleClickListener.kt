package com.xwq.companyvxwhelper.listener

import android.view.View

interface NoDoubleClickListener : View.OnClickListener {

    companion object {
        // 两次点击最短时间间隔
        val CLICK_INTERNAL : Int = 500
        var preClickTimeMillis : Long = 0L
    }

    override fun onClick(v: View?) {
        var currentTimeMillis : Long = System.currentTimeMillis()

        if (currentTimeMillis - preClickTimeMillis > CLICK_INTERNAL) {
            preClickTimeMillis = currentTimeMillis
            noDoubleClick(v)
        } else {
            return
        }
    }

    fun noDoubleClick(v: View?) {

    }
}