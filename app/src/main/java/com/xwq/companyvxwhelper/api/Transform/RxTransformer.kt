package com.xwq.companyvxwhelper.api.Transform

import androidx.viewbinding.ViewBinding
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BroadCastBaseModel
import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.base.IBroadCastBaseView
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
    open fun <VB : ViewBinding, T, V : IBaseView,M : BaseModel<VB, V>> switchSchedulers(baseModel: M): ObservableTransformer<T?, T?>? {
        return ObservableTransformer { upstream ->
            upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { disposable -> baseModel.mDisposable.add(disposable) }
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    open fun <T, V : IBroadCastBaseView,M : BroadCastBaseModel<V>> switchSchedulers(broadCastBaseModel: M): ObservableTransformer<T?, T?>? {
        return ObservableTransformer { upstream ->
            upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { disposable -> broadCastBaseModel.mDisposable.add(disposable) }
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