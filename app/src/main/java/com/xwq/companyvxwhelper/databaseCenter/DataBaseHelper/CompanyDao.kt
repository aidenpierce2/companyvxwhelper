package com.xwq.companyvxwhelper.databaseCenter.DataBaseHelper

import android.content.Context
import android.text.TextUtils
import com.xwq.companyvxwhelper.base.BaseDataBaseHelper
import com.xwq.companyvxwhelper.base.BaseEntity
import com.xwq.companyvxwhelper.bean.DataBaseBean.CompanyDBBean
import com.xwq.companyvxwhelper.bean.DataBaseBean.CompanyDBBeanDao
import org.greenrobot.greendao.AbstractDao

class CompanyDao(context: Context) : BaseDataBaseHelper(context) {

    override fun getSelfDao(): AbstractDao<BaseEntity, Long> {
        return mDaoSession!!.companyDBBeanDao as AbstractDao<BaseEntity, Long>
    }

    override fun addOrReplace(T: BaseEntity) {
        var isCompanyBean : Boolean = T is CompanyDBBean
        if (isCompanyBean) {
            mSelfDao!!.insertOrReplace(T)
        }
    }

    override fun delete(T: BaseEntity) {
        var isConpamyBean : Boolean = T is CompanyDBBean
        if (isConpamyBean) {
            mSelfDao!!.delete(T)
        }
    }

    override fun selectAll(): List<BaseEntity> {
        var list : List<BaseEntity> = mSelfDao!!.queryBuilder().list()
        return list
    }

    // 查找指定uuid的数据
    fun getBeanByUuid(uuid : String) : CompanyDBBean?{
        if (uuid.isNullOrEmpty()) {
            return null
        }

        var companyDBBeanList : List<CompanyDBBean> = mSelfDao!!.queryBuilder().where(CompanyDBBeanDao.Properties.UserUuidStr.eq(uuid)).build().unique() as List<CompanyDBBean>
        if (companyDBBeanList != null && companyDBBeanList.size != 0) {
            return companyDBBeanList.get(0)
        } else {
            return null
        }
    }

}
