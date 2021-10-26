package com.xwq.companyvxwhelper.thread

class ThreadPoolFactory {

    companion object {
        var instance : ThreadPoolFactory = ThreadPoolFactory()
    }

    fun getHttpRequestPool () : WebRequestThread {
        return WebRequestThread.webRequestThread
    }

    fun getCoreRequestPool () : CoreRequestThread {
        return CoreRequestThread.coreRequestThread
    }
}