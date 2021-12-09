package com.xwq.companyvxwhelper.mvvm.view.fragment

import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.bean.ResponseBean.HistoryResBean

interface HistoryView : IBaseView{

   fun getHistoryDataSucc(data: HistoryResBean?)

   fun getHistoryDataFail(data : String)
}