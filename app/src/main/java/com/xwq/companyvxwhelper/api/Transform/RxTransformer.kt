package com.xwq.companyvxwhelper.api.Transform

import com.xwq.companyvxwhelper.mvvm.model.broadcast.SendSmsBroadCastModel
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxTransformer {
    /**
     * 无参数
     *
     * @param <T> 泛型
     * @return 返回Observable
    </T> */
    fun <T> switchSchedulers(baseModel: SendSmsBroadCastModel): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { disposable -> baseModel!!.mDisposable.add(disposable!!) }
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> io_main(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}