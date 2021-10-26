package com.xwq.companyvxwhelper.base

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.xwq.companyvxwhelper.BuildConfig
import com.xwq.companyvxwhelper.bean.DataBaseBean.DaoMaster
import com.xwq.companyvxwhelper.bean.DataBaseBean.DaoSession
import org.greenrobot.greendao.AbstractDao

abstract class BaseDataBaseHelper(context : Context) {

    var mContext = context
    var instance : BaseDataBaseHelper? = null
    var devOpenHelper : DaoMaster.DevOpenHelper? = null
    var readAbleDataBase : SQLiteDatabase? = null
    var writeAbleDataBase : SQLiteDatabase? = null
    var mDaomaster : DaoMaster? = null
    var mDaoSession : DaoSession? = null
    var mSelfDao : AbstractDao<BaseEntity, Long>? = null

    init {
        fun BaseDataBaseHelper(context : Context) {
            mContext = context
            if (instance == null) {
                instance = this
                init()
            }
        }
    }

    fun init() {
        devOpenHelper = DaoMaster.DevOpenHelper(mContext, BuildConfig.DB_NAME)
        mDaomaster = DaoMaster(getWriteableDatabase())
        mDaoSession = mDaomaster!!.newSession()
        mSelfDao = getSelfDao()
    }

    fun getReadableDatabase() : SQLiteDatabase{
        if (readAbleDataBase == null) {
            readAbleDataBase = devOpenHelper!!.readableDatabase
        }
        return readAbleDataBase!!
    }

    fun getWriteableDatabase() : SQLiteDatabase{
        if (writeAbleDataBase == null) {
            writeAbleDataBase = devOpenHelper!!.writableDatabase
        }
        return writeAbleDataBase!!
    }

    // 获取子类自己的dao
    abstract fun getSelfDao() : AbstractDao<BaseEntity, Long>

    // 增 或者 改
    abstract fun addOrReplace(T : BaseEntity)
    
    // 删
    abstract fun delete(T : BaseEntity)

    // 查
    abstract fun selectAll() : List<BaseEntity>
}