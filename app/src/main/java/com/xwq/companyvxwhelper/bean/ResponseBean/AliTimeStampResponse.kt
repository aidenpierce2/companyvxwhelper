package com.xwq.companyvxwhelper.bean.ResponseBean

import com.xwq.companyvxwhelper.base.BaseEntity

data class AliTimeStampResponse(var api : String?, var v : String?, var ret : Array<String>?, var data : Data?) : BaseEntity() {


    data class Data(var t : String?) {

    }
}