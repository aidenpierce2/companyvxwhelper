package com.xwq.companyvxwhelper.publicinterface

interface ObserverInterface {

    //订阅
    fun addObservable(dataObj: ObservableInterface) {}

    //取消订阅
    fun removeObservable(dataObj: ObservableInterface) {}

    //通知文件发生变化
    fun notifyAllObservable() {}
}