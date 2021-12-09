package com.xwq.companyvxwhelper.mvvm.model.fragment

import com.xwq.companyvxwhelper.api.Api
import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.BaseObserver
import com.xwq.companyvxwhelper.bean.RequestBean.HistoryReqBean
import com.xwq.companyvxwhelper.bean.ResponseBean.HistoryResBean
import com.xwq.companyvxwhelper.databinding.FragmentHistoryBinding
import com.xwq.companyvxwhelper.mvvm.view.fragment.HistoryView

class HistoryModel(historyView: HistoryView) : BaseModel<FragmentHistoryBinding, HistoryView>(historyView) {

    var url = ""

    fun getHistoryData(historyReqBean: HistoryReqBean) {
        url = Urls.GET_HISTORY_INFO
        apiService().getHistoryDate(url, historyReqBean.toMap())
            .compose(RxTransformer.switchSchedulers(this))
            .subscribe(object : BaseObserver<HistoryResBean>(curContext) {
                override fun onFailure(e: Throwable?) {
                    curView?.hideLoading()
                    curView?.getHistoryDataFail(e.toString())
                }

                override fun onRequestStart() {
                    curView?.showLoading()

                }

                override fun onSuccess(msg: String?, data: HistoryResBean?) {
                    curView?.hideLoading()
                    curView?.getHistoryDataSucc(data)
                }

                override fun onFinally() {
                    curView?.hideLoading()
                }
            })
    }
}