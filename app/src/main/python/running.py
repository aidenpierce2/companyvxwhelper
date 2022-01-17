from typing import Any, Text
import uiautomator2 as u2
import time
import os 
import re
import asyncio

global device
packageName = "com.tencent.wework"
launcherActivity = "com.tencent.wework.launch.LaunchSplashActivity"
adbGetCurActivityName = "adb shell dumpsys window | findstr mCurrentFocus"
global userTelePhone
userTelePhone = "13671667643"
qYWXTag = "登录企业微信"

def startApp() :

    openAppCmd = "adb shell am start -n " + packageName + "/" + launcherActivity
    time.sleep(5)

    time.sleep(3)

def getCurrentActivity() : 
    output = os.popen(adbGetCurActivityName).read()
    print(output)
    separator = "/"
    realActivityClass = output[output.rfind(separator):]
    separator1= "."
    realActivityMiddle = realActivityClass[realActivityClass.rfind(separator1)+1:]
    separator2 = "}"
    realActivityName = realActivityMiddle[:realActivityMiddle.rfind(separator2)]
    print(realActivityName)
    return realActivityName

async def dispatchAction( realActivityName ) :
    action = {
        "LoginWxAuthActivity" : reLogin,

    }
    actionImpl = action.get(realActivityName, "otherActivity")
    if actionImpl :
        await actionImpl()

async def reLogin() : 
    #点击手机号登录
    print("enter reLogin!")
    device(text="手机号登录", className="android.widget.TextView").click()
    time.sleep(3)
    if device(text="同意", className="android.widget.TextView").exists:
            device(text="同意", className="android.widget.TextView").click()
            time.sleep(3)
    await inputPhoneNuber()

async def inputPhoneNuber() :
    #输入手机号
    print("enter inputPhoneNuber!")
    device(text="手机号", className="android.widget.EditText").click()
    time.sleep(3)
    device(text="手机号", className="android.widget.EditText").set_text(userTelePhone)
    time.sleep(3)
    #点击下一步
    device(text="下一步", className="android.widget.Button").click()
    time.sleep(3)
    
async def inteceptorMsg(smsData) : 
    #拦截获取到的验证短信
    result = getVerifyCode(smsData)
    print("inteceptorMsg result: " + result)
    return result

def getVerifyCode(smsData):
    msgContent = smsData.msgContent
    if qYWXTag in msgContent :
        #获取六位数验证码
        result = re.findall(r"\D(\d{6})\D", msgContent)
        return result

#启动app
startApp()
#获取当前activity
realActivityName = getCurrentActivity()

print("start")
#区分具体界面 开启访问
#dispatchAction(realActivityName)

loop = asyncio.get_event_loop()
loop.run_until_complete(dispatchAction(realActivityName))
loop.close()
