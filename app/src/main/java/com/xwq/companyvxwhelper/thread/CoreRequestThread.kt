package com.xwq.companyvxwhelper.thread

import java.util.concurrent.*

class CoreRequestThread  : ThreadAbstruct(){
    val CORE_POOL_NUM : Int = 5
    val MAX_THREAD_NUM : Int = 5
    val KEEP_ALIVE_TIME : Long = 0

    companion object {
        var coreRequestThread : CoreRequestThread = CoreRequestThread()
        var coreRequestService : ExecutorService? = null
    }

    fun getInstance() : CoreRequestThread{
        return coreRequestThread
    }

    private fun init() {
        if (coreRequestService == null) {
            coreRequestService = ThreadPoolExecutor(CORE_POOL_NUM, MAX_THREAD_NUM, KEEP_ALIVE_TIME, TimeUnit.SECONDS, object : LinkedBlockingDeque<Runnable> () {

            });
        }
    }


    override fun execTask(runnable : Runnable)  {
        if (coreRequestThread == null || runnable == null) {
            return Unit
        }

        init()

        coreRequestService!!.execute(runnable)
    }

    override fun terminal() {
        if (coreRequestThread == null) {
            return Unit
        }

        coreRequestService!!.shutdown()
    }
}