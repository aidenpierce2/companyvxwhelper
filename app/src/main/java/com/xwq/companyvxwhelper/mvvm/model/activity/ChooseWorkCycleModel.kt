package com.xwq.companyvxwhelper.mvvm.model.activity

import com.xwq.companyvxwhelper.base.BaseModel
import com.xwq.companyvxwhelper.base.IBaseView
import com.xwq.companyvxwhelper.databinding.ActivityBaseSettingBinding
import com.xwq.companyvxwhelper.mvvm.view.activity.ChooseWorkCycleView

class ChooseWorkCycleModel(mBaseView : IBaseView) : BaseModel<ActivityBaseSettingBinding, ChooseWorkCycleView>(mBaseView) {

    // 存贮选中的信息到数据库 保存到服务器
    fun saveDataToDBAndServer() {

    }
}