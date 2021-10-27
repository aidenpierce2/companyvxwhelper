package com.xwq.companyvxwhelper.mvvm.model.activity

import com.xwq.companyvxwhelper.api.Api
import com.xwq.companyvxwhelper.api.Transform.RxTransformer
import com.xwq.companyvxwhelper.api.Urls
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.mvvm.view.activity.SplashView

class SplashModel(splashView: SplashView) : BaseModel<SplashView>(splashView) {
    var url : String = ""

    //检测token的有效性
    fun checkTokenValid(userToken : String) {
        url = Urls.CHECK_TOKEN_VALID
        apiService().checkTokenValid(url, Api.createRequestBody(userToken))
            .compose(RxTransformer.switchSchedulers(this))
    }
}