package com.xwq.companyvxwhelper.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.drawable.AnimationDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.*
import android.view.MotionEvent.*
import android.view.animation.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.xwq.companyvxwhelper.MyApplication
import com.xwq.companyvxwhelper.R
import com.xwq.companyvxwhelper.base.BaseActivity
import com.xwq.companyvxwhelper.const.Const.PULLDOWN_CAN_CLICK
import com.xwq.companyvxwhelper.databinding.WidgetMineBinding
import com.xwq.companyvxwhelper.publicinterface.ObservableInterface
import com.xwq.companyvxwhelper.publicinterface.ObserverInterface
import com.xwq.companyvxwhelper.utils.LogUtil
import com.xwq.companyvxwhelper.utils.SharePreferenceUtil
import com.xwq.companyvxwhelper.utils.WindowScreenUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MineLocationPullView: ConstraintLayout, ObserverInterface {

    private val TAG : String = MineLocationPullView::class.java.simpleName.toString()

    //默认加载最大持续时间
    private val maxLoadingDuration : Long = 10000L
    //回弹持续时间
    private val kickBackDuration : Float = 500F
    //触发下拉刷新的阈值
    private val loadingMaxRate : Float = 0.99F
    // decelrateinterceptor的factor
    private val mFactor = 1f
    //当前视图
    private lateinit var curView : View
    //顶部动图宽度
    private var topPicWidth : Float = 0f
    //顶部动图高度
    private var topPicHeight : Float = 0f
    //主背景图高度屏占比
    private var mainBgScreenRate : Float = 0.0f
    //主背景图遮蔽部分占比
    private var mainBgScreenCuteRate : Float = 0.0f
    //card的图片
    private var cardPicAddress : String? = ""
    //card的昵称
    private var cardNickName : String? = ""
    //card的号码
    private var cardPhoneNumber : String? = ""
    //主背景图高度
    private var mainBgHeight : Float = 0f
    //之前按下的横坐标
    private var prePointx : Float = 0f
    //之前按下的纵坐标
    private var prePointy : Float = 0f
    //当前按下的横坐标
    private var curPointx : Float = 0f
    //当前按下的纵坐标
    private var curPointy : Float = 0f
    //当前状态
    private var viewStatue : ViewStatue = ViewStatue.DEFAUL
    //当前整体视图向下滑动的距离
    private var scrollYDistance : Float = 0f
    //真实需要下滑间隔距离
    private var realAddMiddleDistance : Float = 0f
    //真实需要下滑距离
    private var realScrollYDistance : Float = 0f
    //最大可用下滑距离
    private var maxAvailableDistance : Float = 0f
    //初始主背景宽度
    private var defMainBgWidth : Float = 0f
    //初始主背景高度
    private var defMainBgHeight : Float = 0f
    //回弹的一线
    private var preScrollYDistance : Float = 0f
    //判断是否需要阻尼效果
    private var needDamp : Boolean = false

    //测试
    private var pullDistacne : Float = 0F
    private var goBackDistacne : Float = 0F

    // 初始化tag
    private var initTag : Boolean = false
    //上下文
    lateinit private var mContext : BaseActivity<*, *, *>
    //主容器
    lateinit private var mainContainer : ConstraintLayout
    //顶部加载视图
    lateinit private var topLoadingIV : AppCompatImageView
    //主背景视图
    lateinit private var mainBgIV : AppCompatImageView

    lateinit private var timer : Timer
    private var webLoadingStatus : Boolean = false
    private var observableList : ArrayList<ObservableInterface> = arrayListOf()


    enum class ViewStatue(statue : String) {
        // 开始状态 下拉状态 加载态 回弹状态 阻尼态
        DEFAUL("default"),PULLDOWN("pulldown"),LOADING("loading"),GOBACK("goback"),DAMPING("damping")
    }


    constructor(context : Context) : this(context, null) {

    }

    constructor(context : Context, attrs : AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int) : super(context, attrs, defStyleAttr){

        setWillNotDraw(false)
        mContext = context as BaseActivity<*, *, *>

        var typedArray : TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MineLocationPullView)
        topPicWidth = typedArray.getDimension(R.styleable.MineLocationPullView_topPicWidth, 0f)
        topPicHeight = typedArray.getDimension(R.styleable.MineLocationPullView_topPicHeight, 0f)
        mainBgScreenRate = typedArray.getFloat(R.styleable.MineLocationPullView_mainBgDefaultRate, 0.0f)
        mainBgScreenCuteRate = typedArray.getFloat(R.styleable.MineLocationPullView_mainBgDefaultCuteRate, 0.0f)
        cardPicAddress = typedArray.getString(R.styleable.MineLocationPullView_cardPicAddress)
        cardNickName = typedArray.getString(R.styleable.MineLocationPullView_cardNickName)
        cardPhoneNumber = typedArray.getString(R.styleable.MineLocationPullView_cardPhoneNumber)
        typedArray.recycle()

        var widgetMineBinding = DataBindingUtil.inflate<WidgetMineBinding>(LayoutInflater.from(context), R.layout.widget_mine, this@MineLocationPullView,true)
        curView = widgetMineBinding.root
        mainContainer = widgetMineBinding.wigetMineCstlContainer
        topLoadingIV = widgetMineBinding.wigetMineAcivTop
        mainBgIV = widgetMineBinding.wigetMineAcivMainBg
    }

    override fun addObservable(dataObj: ObservableInterface) {
        super.addObservable(dataObj)
        observableList.add(dataObj)
    }

    override fun removeObservable(dataObj: ObservableInterface) {
        super.removeObservable(dataObj)
        observableList.remove(dataObj)
    }

    override fun notifyAllObservable() {
        super.notifyAllObservable()
        for (element in observableList) {
            element.notifyDataChange( mainBgHeight - maxAvailableDistance * 2 + realScrollYDistance / 3 * 4)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        // 顶部图片 宽高相关
        // 先从宽度开始
        when (widthMode) {
            MeasureSpec.UNSPECIFIED -> {
                // 不做任何限制 一般用于系统 顶部图片宽度不需要变化
            }
            MeasureSpec.AT_MOST -> {
                // 最多
                if (topPicWidth > widthSize) {
                    topPicWidth = widthSize.toFloat()
                }
            }
            MeasureSpec.EXACTLY -> {
                // 已确定
                topPicWidth = widthSize.toFloat()
            }
        }

        // 防止内存溢出 使用application
        var screenHeight = WindowScreenUtil.getScreenHeight(MyApplication.app)
        // 高度
        when (heightMode) {
            MeasureSpec.UNSPECIFIED -> {
                // 啥都不用做
                mainBgHeight = screenHeight * mainBgScreenRate
            }
            MeasureSpec.AT_MOST -> {
                topPicHeight = heightSize.toFloat()
                if ((screenHeight * mainBgScreenRate).toInt() > heightSize) {
                    mainBgHeight = heightSize.toFloat()
                }
            }
            MeasureSpec.EXACTLY -> {
                topPicHeight = heightSize.toFloat()
                mainBgHeight = heightSize.toFloat()
            }
        }

        var firstChild = topLoadingIV
        var firstChildParams = firstChild.layoutParams
        firstChildParams.width = topPicWidth.toInt()
        firstChildParams.height = topPicHeight.toInt()
        firstChild.layoutParams = firstChildParams


        var layoutParams : ConstraintLayout.LayoutParams = topLoadingIV.layoutParams as ConstraintLayout.LayoutParams
        var height = layoutParams.height

        var mainBgLayoutFramelayout : ConstraintLayout.LayoutParams = mainBgIV.layoutParams as ConstraintLayout.LayoutParams
        mainBgLayoutFramelayout.height = mainBgHeight.toInt()
        mainBgIV.layoutParams = mainBgLayoutFramelayout

        // 计算差值
        var balanceHeight = Math.min(mainBgHeight * (1 - mainBgScreenRate), height.toFloat() + layoutParams.topMargin + mainBgLayoutFramelayout.topMargin)
        maxAvailableDistance = balanceHeight
    }

    override fun onDraw(canvas: Canvas?) {

//        LogUtil.log(TAG, "viewStatue: " + viewStatue)
        //开始切割
        //区分状态
        //状态1 4 就这么切
        if (viewStatue == ViewStatue.DEFAUL || viewStatue == ViewStatue.DAMPING) {
            var path = Path()
            path.moveTo(0f,0f)
            path.lineTo(0f, mainBgHeight - maxAvailableDistance)
            path.lineTo(WindowScreenUtil.getScreenWidth(MyApplication.app).toFloat(), mainBgHeight - maxAvailableDistance)
            path.lineTo(WindowScreenUtil.getScreenWidth(MyApplication.app).toFloat(), 0f)
            path.close()
            canvas!!.clipPath(path)
        }
        // 状态2 3 切贝塞尔弧
        if (viewStatue == ViewStatue.PULLDOWN || viewStatue == ViewStatue.GOBACK) {
            var path = Path()
            path.moveTo(0f, -maxAvailableDistance)
            path.lineTo(0f, mainBgHeight - maxAvailableDistance)
            path.cubicTo(0f, (mainBgHeight - maxAvailableDistance) + realScrollYDistance / 3 * 4,
                WindowScreenUtil.getScreenWidth(MyApplication.app).toFloat(), (mainBgHeight - maxAvailableDistance) + realScrollYDistance / 3 * 4,
                WindowScreenUtil.getScreenWidth(MyApplication.app).toFloat(), mainBgHeight - maxAvailableDistance)
            path.lineTo(WindowScreenUtil.getScreenWidth(MyApplication.app).toFloat(), -maxAvailableDistance)
            path.close()
            canvas!!.clipPath(path)
        }

        super.onDraw(canvas)

        if (viewStatue == ViewStatue.PULLDOWN || viewStatue == ViewStatue.GOBACK) {
            if(realScrollYDistance >0 && initTag && realScrollYDistance < maxAvailableDistance) {
                //发生变化 整体移动该距离
                (curView.parent as ConstraintLayout).post (object : Runnable {
                    override fun run() {
                        if (viewStatue == ViewStatue.PULLDOWN) {
                            pullDistacne += realAddMiddleDistance.toInt()
                            goBackDistacne = 0f
                            (curView.parent as ConstraintLayout).scrollBy(0,  -realAddMiddleDistance.toInt())
                        } else if (viewStatue == ViewStatue.GOBACK) {
                            if (pullDistacne != 0f) {
                                LogUtil.log(TAG, "pullDistacne: " + pullDistacne)
                            }
                            goBackDistacne += realAddMiddleDistance.toInt()
                            LogUtil.log(TAG, "GOBACK middle distance: " + (-realAddMiddleDistance.toInt()).toString())
                            (curView.parent as ConstraintLayout).scrollBy(0,  realAddMiddleDistance.toInt())
                        }
                    }

                } )
            }
            notifyAllObservable()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (!initTag) {
            (curView.parent as ConstraintLayout).post (object : Runnable {
                override fun run() {
                    LogUtil.log(TAG, "onLayout: inner execute")
                    (curView.parent as ConstraintLayout).scrollBy(0,  maxAvailableDistance.toInt())
                    notifyAllObservable()
                }
            })
            initTag = true
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (defMainBgWidth <= 0 || defMainBgHeight <= 0) {
            defMainBgWidth = mainBgIV.measuredWidth.toFloat()
            defMainBgHeight = mainBgIV.measuredHeight.toFloat()
        }
        requestDisallowInterceptTouchEvent(true)
        // 阶段4 5所有触摸操作均不作处理
        if (viewStatue == ViewStatue.GOBACK || viewStatue == ViewStatue.DAMPING) {
            return true
        }
        when (ev!!.action) {
            //按下
            ACTION_DOWN -> {
                // 按下视为开始下拉
//                LogUtil.log(TAG, "ACTION_DOWN, executed!")
                if (viewStatue == ViewStatue.DEFAUL) {
                    scrollYDistance = 0f
                }
                curPointx = ev.rawX
                curPointy = ev.rawY
                return true
            }
            //移动
            ACTION_MOVE -> {
//                LogUtil.log(TAG, "ACTION_MOVE, executed!")
                prePointx = curPointx
                prePointy = curPointy

                curPointx = ev.rawX
                curPointy = ev.rawY
                // 区分情况 当处于阶段二时才生效否则不处理
                if (viewStatue == ViewStatue.DEFAUL) {
                    SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, true)
                    getRealScrollYDistance(curPointy, prePointy)
                    //初始状态 进入下滑状态
                    if(curPointy - prePointy > 0) {
                        viewStatue = ViewStatue.PULLDOWN
                        invalidate()
                    }
                    return true
                } else if (viewStatue == ViewStatue.PULLDOWN) {
                    SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, false)
                    // 用户尝试查看页头 不允许
                    if (prePointy <= 0) {
                        needDamp = false
                        viewStatue = ViewStatue.DEFAUL
                        gobackToDefault()
                        return true
                    }
                    //判断长度有没有到达限度 到达禁止触摸事件 开始加载
                    if (realScrollYDistance >= maxAvailableDistance * loadingMaxRate) {
                        // 通知已经到达阶段三 不允许滑动
                        // 先不允许进入 45 直接跳过
                        needDamp = false
                        viewStatue = ViewStatue.LOADING

                        return true
                    }

                    // 放弃下拉 进入阶段三 不会重新加载数
                    if (curPointy - prePointy < 0) {
                        needDamp = false
                        viewStatue = ViewStatue.GOBACK
                        scrollYDistance = Math.abs(curPointy - prePointy)

                        mainBgScallLogic()
                        kickBackLogic()
                        return true
                    }
                    // 计算诸元 绘制的事情交给ondraw()
                    getRealScrollYDistance(curPointy, prePointy)

                    mainBgScallLogic()
                    invalidate()
                    return true
                } else {
                    return false
                }
            }
            //松开
            ACTION_UP -> {
//                LogUtil.log(TAG, "ACTION_UP, executed!")
                // 阶段3 4均触发
                if (viewStatue == ViewStatue.PULLDOWN) {
                    SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, false)
                    // 下拉取消状态直接回复
                    viewStatue = ViewStatue.GOBACK

                    kickBackLogic()
                    invalidate()
                    return true
                } else if (viewStatue == ViewStatue.LOADING) {
                    SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, false)
                    // 加载状态 持续最大加载时间 或者 加载成功后 再回复
                    startLoadingDrawable()
                    loadingCountDown()
                    return true
                }
            }
            ACTION_CANCEL -> {
                LogUtil.log(TAG, "ACTION_CANCEL, executed!")
            }
        }
        return true
    }

    //获取真实的ydistance
    fun getRealScrollYDistance(curPointy : Float, prePointy : Float) {
        if (curPointy -  prePointy <= 0) {
            return
        }

        scrollYDistance = Math.abs(curPointy - prePointy)
        realAddMiddleDistance = ((1.0f - Math.pow((realScrollYDistance / maxAvailableDistance).toDouble(), 2 * mFactor.toDouble())) * scrollYDistance).toFloat()
        realScrollYDistance += realAddMiddleDistance
    }

    //回到初始逻辑
    fun gobackToDefault() {
        this@MineLocationPullView.scrollY = 0
    }

    //图片放大/缩小逻辑
    fun mainBgScallLogic() {
        mContext.runOnUiThread(object : Runnable{
            override fun run() {
                var scallRate : Float = 0.0f
                //计算缩放比率
                scallRate = realScrollYDistance / maxAvailableDistance + 1

                var mainBgParams = mainBgIV.layoutParams
                mainBgParams.width = (scallRate * defMainBgWidth).toInt()
                mainBgParams.height = (scallRate * defMainBgHeight).toInt()
                (mainBgParams as ConstraintLayout.LayoutParams).setMargins((-((scallRate * defMainBgWidth) / 2)).toInt(),0,0,0)
                mainBgIV.layoutParams = mainBgParams
            }
        })
    }

    // 回弹逻辑
    fun kickBackLogic() {
        mContext.runOnUiThread(object : Runnable{
            override fun run() {
                var valueAnimator : ValueAnimator = ObjectAnimator.ofInt(realScrollYDistance.toInt(), 0)
                valueAnimator.duration = kickBackDuration.toLong()
                valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator?) {
                        if (preScrollYDistance == 0f) {
                            preScrollYDistance = realScrollYDistance
                        }
                        realScrollYDistance = (animation?.animatedValue as Integer).toFloat()
                        realAddMiddleDistance = preScrollYDistance - realScrollYDistance
                        preScrollYDistance = realScrollYDistance
                        mainBgScallLogic()
                        if ((animation?.animatedValue as Integer) <= 0) {
                            pullDistacne = 0f
                            if (needDamp) {
                                viewStatue = ViewStatue.DAMPING
                                SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, false)
                                dampingLogic()
                            } else {
                                viewStatue = ViewStatue.DEFAUL
                                SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, true)
                            }
                            viewStatue = ViewStatue.DEFAUL
                            SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, true)
                        }
                    }
                })
                valueAnimator.start()
            }
        })
    }

    // 阻尼逻辑
    fun dampingLogic() {
        var firstChildParamas : ConstraintLayout.LayoutParams = topLoadingIV.layoutParams as LayoutParams
        var bottomMargin = firstChildParamas.bottomMargin
        var maxDampingValue : Float = bottomMargin.toFloat()

        CoroutineScope(Dispatchers.Main).launch {
            realScrollYDistance = 0f
            viewStatue = ViewStatue.DAMPING
            SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, false)
            var channel : Channel<String> = Channel<String>()

            var curDampingValue : Float = maxDampingValue
            withContext(Dispatchers.Main) {
                var dampingPullAnimation : ValueAnimator = ObjectAnimator.ofFloat(0f, maxDampingValue)
                dampingPullAnimation.duration = 1000
                dampingPullAnimation.interpolator = AccelerateInterpolator()
                dampingPullAnimation.addUpdateListener (object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator?) {
                        var animatedValue : Float = animation!!.animatedValue as Float
                        var middleDampPullValue = curDampingValue - animatedValue
                        LogUtil.log(TAG, "dampingLogic down: " + middleDampPullValue)
                        (curView.parent as ConstraintLayout).scrollBy(0,
                            (-middleDampPullValue).toInt()
                        )
                        curDampingValue = animatedValue
                        if (animatedValue >= maxDampingValue) {
                            CoroutineScope(Dispatchers.Main).launch {
                                if (!channel.isClosedForSend) {
                                    channel.send("ok")
                                    channel.cancel()
                                }
                            }
                        }
                    }
                })
                dampingPullAnimation.start()
            }

            if(!channel.isClosedForReceive) {
                var resultStr : String = channel.receive()
                if (resultStr.equals("ok")) {
                    withContext(Dispatchers.Main) {
                        var curDampingValue : Float = maxDampingValue
                        var dampingPushAnimation : ValueAnimator = ObjectAnimator.ofFloat(maxDampingValue, 0f)
                        dampingPushAnimation.duration = 1000
                        dampingPushAnimation.interpolator = DecelerateInterpolator()
                        dampingPushAnimation.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener{
                            override fun onAnimationUpdate(animation: ValueAnimator?) {
                                var animatedValue : Float = animation!!.animatedValue as Float
                                var middleDampPullValue = curDampingValue - animatedValue
                                LogUtil.log(TAG, "dampingLogic up: " + middleDampPullValue)
                                (curView.parent as ConstraintLayout).scrollBy(0,
                                    (middleDampPullValue).toInt()
                                )
                                curDampingValue = animatedValue
                                if (animatedValue <= 0f) {
                                    viewStatue = ViewStatue.DEFAUL
                                    SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, true)
                                }
                            }
                        })
                        dampingPushAnimation.start()
                    }
                }
            }
        }
    }

    // 开启loadingdrawable
    fun startLoadingDrawable() {
        var animationDrawable : AnimationDrawable = resources.getDrawable(R.drawable.dialog_loading_animation) as AnimationDrawable
        animationDrawable.isOneShot = false
        topLoadingIV.setImageDrawable(animationDrawable)
        animationDrawable.start()
    }

    // 重新设置回禁止的drawable
    fun resetLoadingDrawable() {
        mContext.runOnUiThread(object : Runnable{
            override fun run() {
                var isAnimationDrawable = topLoadingIV.drawable is AnimationDrawable
                if (isAnimationDrawable) {
                    (topLoadingIV.drawable as AnimationDrawable).stop()
                    topLoadingIV.setImageDrawable(resources.getDrawable(R.drawable.loading_search_01))
                }
            }
        })
    }

    //加载结果通知
    fun loadingResNotify(success : Boolean) {
        // 赞不需要处理是否成功
        timer?.cancel()
    }

    // 加载倒计时计数器
    @Synchronized fun loadingCountDown() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                // 检测网络是否加载完毕 没有直接回退
                if (webLoadingStatus == false) {

                    viewStatue = ViewStatue.GOBACK
                    SharePreferenceUtil.instance.setData(PULLDOWN_CAN_CLICK, false)
                    resetLoadingDrawable()
                    kickBackLogic()
                }
            }

        },maxLoadingDuration)
    }
}