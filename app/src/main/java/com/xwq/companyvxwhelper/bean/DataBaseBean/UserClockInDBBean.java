package com.xwq.companyvxwhelper.bean.DataBaseBean;


import com.xwq.companyvxwhelper.base.BaseEntity;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserClockInDBBean extends BaseEntity {

    // greendao 不支持 kotlin 采用java

    @Id(autoincrement = true)
    Long id = 0L;

    @Index(unique = true)
    String userUUid = null;

    String telephoneNum = "";

    String userNickName = "";

    String emailAddressStr = "";

    String passwordStr = "";

    String mondayClock = "";

    String tuesdayClock = "";

    boolean wednesdayClock = false;

    boolean thursdayClock = false;

    boolean fridayClock = false;

    boolean saturdayClock = false;

    boolean sundayClock = false;

    boolean weekendClock = false;

    boolean holidayClock = false;

    String morningClockTime = "";

    String eveningClockTime = "";

    boolean inUse = true;

    @Generated(hash = 1196762148)
    public UserClockInDBBean(Long id, String userUUid, String telephoneNum, String userNickName,
            String emailAddressStr, String passwordStr, String mondayClock, String tuesdayClock,
            boolean wednesdayClock, boolean thursdayClock, boolean fridayClock, boolean saturdayClock,
            boolean sundayClock, boolean weekendClock, boolean holidayClock, String morningClockTime,
            String eveningClockTime, boolean inUse) {
        this.id = id;
        this.userUUid = userUUid;
        this.telephoneNum = telephoneNum;
        this.userNickName = userNickName;
        this.emailAddressStr = emailAddressStr;
        this.passwordStr = passwordStr;
        this.mondayClock = mondayClock;
        this.tuesdayClock = tuesdayClock;
        this.wednesdayClock = wednesdayClock;
        this.thursdayClock = thursdayClock;
        this.fridayClock = fridayClock;
        this.saturdayClock = saturdayClock;
        this.sundayClock = sundayClock;
        this.weekendClock = weekendClock;
        this.holidayClock = holidayClock;
        this.morningClockTime = morningClockTime;
        this.eveningClockTime = eveningClockTime;
        this.inUse = inUse;
    }

    @Generated(hash = 1119347038)
    public UserClockInDBBean() {
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

    public String getTelephoneNum() {
        return this.telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getUserNickName() {
        return this.userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getEmailAddressStr() {
        return this.emailAddressStr;
    }

    public void setEmailAddressStr(String emailAddressStr) {
        this.emailAddressStr = emailAddressStr;
    }

    public String getPasswordStr() {
        return this.passwordStr;
    }

    public void setPasswordStr(String passwordStr) {
        this.passwordStr = passwordStr;
    }

    public String getMondayClock() {
        return this.mondayClock;
    }

    public void setMondayClock(String mondayClock) {
        this.mondayClock = mondayClock;
    }

    public String getTuesdayClock() {
        return this.tuesdayClock;
    }

    public void setTuesdayClock(String tuesdayClock) {
        this.tuesdayClock = tuesdayClock;
    }

    public boolean getWednesdayClock() {
        return this.wednesdayClock;
    }

    public void setWednesdayClock(boolean wednesdayClock) {
        this.wednesdayClock = wednesdayClock;
    }

    public boolean getThursdayClock() {
        return this.thursdayClock;
    }

    public void setThursdayClock(boolean thursdayClock) {
        this.thursdayClock = thursdayClock;
    }

    public boolean getFridayClock() {
        return this.fridayClock;
    }

    public void setFridayClock(boolean fridayClock) {
        this.fridayClock = fridayClock;
    }

    public boolean getSaturdayClock() {
        return this.saturdayClock;
    }

    public void setSaturdayClock(boolean saturdayClock) {
        this.saturdayClock = saturdayClock;
    }

    public boolean getSundayClock() {
        return this.sundayClock;
    }

    public void setSundayClock(boolean sundayClock) {
        this.sundayClock = sundayClock;
    }

    public boolean getWeekendClock() {
        return this.weekendClock;
    }

    public void setWeekendClock(boolean weekendClock) {
        this.weekendClock = weekendClock;
    }

    public boolean getHolidayClock() {
        return this.holidayClock;
    }

    public void setHolidayClock(boolean holidayClock) {
        this.holidayClock = holidayClock;
    }

    public String getMorningClockTime() {
        return this.morningClockTime;
    }

    public void setMorningClockTime(String morningClockTime) {
        this.morningClockTime = morningClockTime;
    }

    public String getEveningClockTime() {
        return this.eveningClockTime;
    }

    public void setEveningClockTime(String eveningClockTime) {
        this.eveningClockTime = eveningClockTime;
    }

    public boolean getInUse() {
        return this.inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

}