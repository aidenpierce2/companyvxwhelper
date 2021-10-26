package com.xwq.companyvxwhelper.bean.DataBaseBean;

import com.xwq.companyvxwhelper.base.BaseEntity;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;


@Entity
public class UserCheckInDBBean extends BaseEntity {

    @Id(autoincrement = true)
    Long id = 0L;

    //用户id
    @Index(unique = true)
    String userUUid;

    //周一早上打卡时间
    String monAmCheckIn;
    //周一下午打卡时间
    String monPmCheckOut;
    //周二上午打卡
    String tueAmCheckIn;
    //周二下午打卡
    String tuePmCheckOut;
    //周三上午打卡
    String wedAmCheckIn;
    //周三下午打卡
    String wedPmCheckOut;
    //周四上午打卡
    String thuAmCheckIn;
    //周四下午打卡
    String thuPmCheckOut;
    //周五上午打卡
    String friAmCheckIn;
    //周五下午打卡
    String friPmCheckOut;
    //周六上午打卡
    String satAmCheckIn;
    //周六下午打卡
    String satPmCheckOut;
    //周日上午打卡
    String sunAmCheckIn;
    //周日下午打卡
    String sunPMCheckOut;

    //周一到周日需要打卡
    boolean monNeedCheck;
    boolean tueNeedCheck;
    boolean wedNeedCheck;
    boolean thuNeedCheck;
    boolean friNeedCheck;
    boolean satNeedCheck;
    boolean sunNeedCheck;

    //节日是否需要打卡
    boolean holidayNeedCheck;
    //是否开启大小周
    boolean big_smallWeek;
    //本周大周还是小周
    boolean curWeekIsBig;
    //创建时间  (不选择使用timestamp对象的原因是greendao不支持)
    String createTimeStamp;
    //更新时间  (不选择使用timestamp对象的原因是greendao不支持)
    String updateTimeStamp;
    //是否可用
    boolean isValid;
    @Generated(hash = 869491051)
    public UserCheckInDBBean(Long id, String userUUid, String monAmCheckIn,
            String monPmCheckOut, String tueAmCheckIn, String tuePmCheckOut,
            String wedAmCheckIn, String wedPmCheckOut, String thuAmCheckIn,
            String thuPmCheckOut, String friAmCheckIn, String friPmCheckOut,
            String satAmCheckIn, String satPmCheckOut, String sunAmCheckIn,
            String sunPMCheckOut, boolean monNeedCheck, boolean tueNeedCheck,
            boolean wedNeedCheck, boolean thuNeedCheck, boolean friNeedCheck,
            boolean satNeedCheck, boolean sunNeedCheck, boolean holidayNeedCheck,
            boolean big_smallWeek, boolean curWeekIsBig, String createTimeStamp,
            String updateTimeStamp, boolean isValid) {
        this.id = id;
        this.userUUid = userUUid;
        this.monAmCheckIn = monAmCheckIn;
        this.monPmCheckOut = monPmCheckOut;
        this.tueAmCheckIn = tueAmCheckIn;
        this.tuePmCheckOut = tuePmCheckOut;
        this.wedAmCheckIn = wedAmCheckIn;
        this.wedPmCheckOut = wedPmCheckOut;
        this.thuAmCheckIn = thuAmCheckIn;
        this.thuPmCheckOut = thuPmCheckOut;
        this.friAmCheckIn = friAmCheckIn;
        this.friPmCheckOut = friPmCheckOut;
        this.satAmCheckIn = satAmCheckIn;
        this.satPmCheckOut = satPmCheckOut;
        this.sunAmCheckIn = sunAmCheckIn;
        this.sunPMCheckOut = sunPMCheckOut;
        this.monNeedCheck = monNeedCheck;
        this.tueNeedCheck = tueNeedCheck;
        this.wedNeedCheck = wedNeedCheck;
        this.thuNeedCheck = thuNeedCheck;
        this.friNeedCheck = friNeedCheck;
        this.satNeedCheck = satNeedCheck;
        this.sunNeedCheck = sunNeedCheck;
        this.holidayNeedCheck = holidayNeedCheck;
        this.big_smallWeek = big_smallWeek;
        this.curWeekIsBig = curWeekIsBig;
        this.createTimeStamp = createTimeStamp;
        this.updateTimeStamp = updateTimeStamp;
        this.isValid = isValid;
    }
    @Generated(hash = 93752751)
    public UserCheckInDBBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserUUid() {
        return this.userUUid;
    }
    public void setUserUUid(String userUUid) {
        this.userUUid = userUUid;
    }
    public String getMonAmCheckIn() {
        return this.monAmCheckIn;
    }
    public void setMonAmCheckIn(String monAmCheckIn) {
        this.monAmCheckIn = monAmCheckIn;
    }
    public String getMonPmCheckOut() {
        return this.monPmCheckOut;
    }
    public void setMonPmCheckOut(String monPmCheckOut) {
        this.monPmCheckOut = monPmCheckOut;
    }
    public String getTueAmCheckIn() {
        return this.tueAmCheckIn;
    }
    public void setTueAmCheckIn(String tueAmCheckIn) {
        this.tueAmCheckIn = tueAmCheckIn;
    }
    public String getTuePmCheckOut() {
        return this.tuePmCheckOut;
    }
    public void setTuePmCheckOut(String tuePmCheckOut) {
        this.tuePmCheckOut = tuePmCheckOut;
    }
    public String getWedAmCheckIn() {
        return this.wedAmCheckIn;
    }
    public void setWedAmCheckIn(String wedAmCheckIn) {
        this.wedAmCheckIn = wedAmCheckIn;
    }
    public String getWedPmCheckOut() {
        return this.wedPmCheckOut;
    }
    public void setWedPmCheckOut(String wedPmCheckOut) {
        this.wedPmCheckOut = wedPmCheckOut;
    }
    public String getThuAmCheckIn() {
        return this.thuAmCheckIn;
    }
    public void setThuAmCheckIn(String thuAmCheckIn) {
        this.thuAmCheckIn = thuAmCheckIn;
    }
    public String getThuPmCheckOut() {
        return this.thuPmCheckOut;
    }
    public void setThuPmCheckOut(String thuPmCheckOut) {
        this.thuPmCheckOut = thuPmCheckOut;
    }
    public String getFriAmCheckIn() {
        return this.friAmCheckIn;
    }
    public void setFriAmCheckIn(String friAmCheckIn) {
        this.friAmCheckIn = friAmCheckIn;
    }
    public String getFriPmCheckOut() {
        return this.friPmCheckOut;
    }
    public void setFriPmCheckOut(String friPmCheckOut) {
        this.friPmCheckOut = friPmCheckOut;
    }
    public String getSatAmCheckIn() {
        return this.satAmCheckIn;
    }
    public void setSatAmCheckIn(String satAmCheckIn) {
        this.satAmCheckIn = satAmCheckIn;
    }
    public String getSatPmCheckOut() {
        return this.satPmCheckOut;
    }
    public void setSatPmCheckOut(String satPmCheckOut) {
        this.satPmCheckOut = satPmCheckOut;
    }
    public String getSunAmCheckIn() {
        return this.sunAmCheckIn;
    }
    public void setSunAmCheckIn(String sunAmCheckIn) {
        this.sunAmCheckIn = sunAmCheckIn;
    }
    public String getSunPMCheckOut() {
        return this.sunPMCheckOut;
    }
    public void setSunPMCheckOut(String sunPMCheckOut) {
        this.sunPMCheckOut = sunPMCheckOut;
    }
    public boolean getMonNeedCheck() {
        return this.monNeedCheck;
    }
    public void setMonNeedCheck(boolean monNeedCheck) {
        this.monNeedCheck = monNeedCheck;
    }
    public boolean getTueNeedCheck() {
        return this.tueNeedCheck;
    }
    public void setTueNeedCheck(boolean tueNeedCheck) {
        this.tueNeedCheck = tueNeedCheck;
    }
    public boolean getWedNeedCheck() {
        return this.wedNeedCheck;
    }
    public void setWedNeedCheck(boolean wedNeedCheck) {
        this.wedNeedCheck = wedNeedCheck;
    }
    public boolean getThuNeedCheck() {
        return this.thuNeedCheck;
    }
    public void setThuNeedCheck(boolean thuNeedCheck) {
        this.thuNeedCheck = thuNeedCheck;
    }
    public boolean getFriNeedCheck() {
        return this.friNeedCheck;
    }
    public void setFriNeedCheck(boolean friNeedCheck) {
        this.friNeedCheck = friNeedCheck;
    }
    public boolean getSatNeedCheck() {
        return this.satNeedCheck;
    }
    public void setSatNeedCheck(boolean satNeedCheck) {
        this.satNeedCheck = satNeedCheck;
    }
    public boolean getSunNeedCheck() {
        return this.sunNeedCheck;
    }
    public void setSunNeedCheck(boolean sunNeedCheck) {
        this.sunNeedCheck = sunNeedCheck;
    }
    public boolean getHolidayNeedCheck() {
        return this.holidayNeedCheck;
    }
    public void setHolidayNeedCheck(boolean holidayNeedCheck) {
        this.holidayNeedCheck = holidayNeedCheck;
    }
    public boolean getBig_smallWeek() {
        return this.big_smallWeek;
    }
    public void setBig_smallWeek(boolean big_smallWeek) {
        this.big_smallWeek = big_smallWeek;
    }
    public boolean getCurWeekIsBig() {
        return this.curWeekIsBig;
    }
    public void setCurWeekIsBig(boolean curWeekIsBig) {
        this.curWeekIsBig = curWeekIsBig;
    }
    public String getCreateTimeStamp() {
        return this.createTimeStamp;
    }
    public void setCreateTimeStamp(String createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }
    public String getUpdateTimeStamp() {
        return this.updateTimeStamp;
    }
    public void setUpdateTimeStamp(String updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }
    public boolean getIsValid() {
        return this.isValid;
    }
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

}
