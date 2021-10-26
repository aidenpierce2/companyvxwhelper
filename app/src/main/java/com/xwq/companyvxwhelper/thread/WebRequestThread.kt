package com.xwq.companyvxwhelper.thread

import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class WebRequestThread : ThreadAbstruct() {

    val CORE_POOL_NUM : Int = 0
    val MAX_THREAD_NUM : Int = 5
    val KEEP_ALIVE_TIME : Long = 30

    companion object {
        var webRequestThread : WebRequestThread = WebRequestThread()
        var threadPoolExecutor : ExecutorService? = null
    }

    fun getInstance() : WebRequestThread{
        return webRequestThread
    }

    private fun init() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = ThreadPoolExecutor(CORE_POOL_NUM, MAX_THREAD_NUM, KEEP_ALIVE_TIME, TimeUnit.SECONDS, object : SynchronousQueue<Runnable> () {

            });
        }
    }


    override fun execTask(runnable : Runnable)  {
        if (webRequestThread == null || runnable == null) {
            return Unit
        }

        init()

        threadPoolExecutor!!.execute(runnable)
    }

    override fun terminal() {
        if (webRequestThread == null) {
            return Unit
        }

        threadPoolExecutor!!.shutdown()
    }
}