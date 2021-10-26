package com.xwq.companyvxwhelper.mvvm.model.activity

import androidx.lifecycle.MutableLiveData
import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.mvvm.view.activity.SplashView

class SplashModel(splashView: SplashView) : BaseModel<SplashView>(splashView) {
    var test : MutableLiveData

}