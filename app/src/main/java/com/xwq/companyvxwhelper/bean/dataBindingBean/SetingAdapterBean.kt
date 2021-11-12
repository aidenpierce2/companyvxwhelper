package com.xwq.companyvxwhelper.bean.dataBindingBean

import com.xwq.companyvxwhelper.base.BaseEntity
import com.xwq.companyvxwhelper.bean.Enum.InputInformationTypeEnum
import com.xwq.companyvxwhelper.bean.Enum.MultiAdapterEnum

data class SettingAdapterBean(var multiAdapterEnum : MultiAdapterEnum = MultiAdapterEnum.SEPARATOR, var titleStr : String, var contentStr : String,
                              var showExamineIcon : Boolean = false, var inputInformationSettingItemBean : InputInformationTypeEnum = InputInformationTypeEnum.OTHER,
                              var tag : String) : BaseEntity() {


    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String) : this(multiAdapterEnum, titleStr, "") {

    }

    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String, contentStr : String) : this(multiAdapterEnum, titleStr, contentStr, false) {

    }

    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String, contentStr : String, showExamineIcon : Boolean) : this(multiAdapterEnum, titleStr, contentStr, showExamineIcon, InputInformationTypeEnum.OTHER) {

    }

    constructor(multiAdapterEnum : MultiAdapterEnum, titleStr : String, contentStr : String, showExamineIcon : Boolean, inputInformationSettingItemBean : InputInformationTypeEnum ) : this(multiAdapterEnum, titleStr, contentStr, showExamineIcon, inputInformationSettingItemBean, "") {

    }

}