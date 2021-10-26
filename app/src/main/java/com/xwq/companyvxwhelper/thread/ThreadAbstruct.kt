package com.xwq.companyvxwhelper.thread

abstract class ThreadAbstruct {

    // 获取一个thread
    open fun execTask (runnable : Runnable) {
    }

    // 回收一个thread
    open fun terminal(){}
}