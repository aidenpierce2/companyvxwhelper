package com.xwq.companyvxwhelper.databaseCenter.DataBaseHelper

import android.content.Context
import android.text.TextUtils
import com.xwq.companyvxwhelper.base.BaseDataBaseHelper
import com.xwq.companyvxwhelper.base.BaseEntity
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserClockInDBBean
import com.xwq.companyvxwhelper.bean.DataBaseBean.UserClockInDBBeanDao
import org.greenrobot.greendao.AbstractDao

class UserInfoDao(context: Context) : BaseDataBaseHelper(context) {

    override fun getSelfDao(): AbstractDao<BaseEntity, Long> {
       return mDaoSession!!.userClockInDBBeanDao!! as AbstractDao<BaseEntity, Long>
    }

    override fun addOrReplace(T: BaseEntity) {
        var isUserClockIn : Boolean = T is UserClockInDBBean
        if (!isUserClockIn) {
            return
        }
        mSelfDao!!.insertOrReplace(T)
    }

    override fun delete(T: BaseEntity) {
        var isUserClockIn : Boolean = T is UserClockInDBBean
        if (!isUserClockIn) {
            return
        }
        mSelfDao!!.delete(T)
    }

    override fun selectAll() : List<BaseEntity>{
        var list : List<BaseEntity> = mSelfDao!!.queryBuilder().list()
        return list
    }

    // 根据号码获取对象
    fun getUserClockInByPhoneNum(telephoneNum : String) : UserClockInDBBean?{
        if (TextUtils.isEmpty(telephoneNum)) {
            return null
        }

        var userClockInDBBeanList : List<UserClockInDBBean> = mSelfDao!!.queryBuilder().where(UserClockInDBBeanDao.Properties.TelephoneNum.eq(telephoneNum)).build().unique() as List<UserClockInDBBean>
        if (userClockInDBBeanList != null && userClockInDBBeanList.size > 0) {
            return userClockInDBBeanList.get(0)
        }
        return null
    }
}