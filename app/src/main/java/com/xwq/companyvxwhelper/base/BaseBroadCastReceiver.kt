package com.xwq.companyvxwhelper.base

import android.content.BroadcastReceiver

abstract class BaseBroadCastReceiver<V : IBroadCastBaseView, M : BroadCastBaseModel<V>>: BroadcastReceiver() {

    /**
     * 获取baseview receiver本身
     */
    abstract fun getView() : V

    /**
     * 获取baseModel 网络请求实现类
     */
    abstract fun getModel() : M

}