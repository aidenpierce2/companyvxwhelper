package com.xwq.companyvxwhelper.mvvm.view.activity

import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.bean.ResponseBean.RegisterResBean

interface RegisterView : IBaseView{

    fun registSucc(data : RegisterResBean)
    fun registFail()

    fun getSmsSucc()
    fun getSmsFail()
}