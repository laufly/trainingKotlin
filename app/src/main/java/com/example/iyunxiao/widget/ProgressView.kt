package com.example.iyunxiao.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.iyunxiao.training.R

/**
 * Created by dongxiaofei on 2018/7/25.
 *  @description 圆环ProgressView 好分数拷贝修改
 */


class ProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mProgressWidth: Float = 0.toFloat()
    private var mRadius: Float = 0.toFloat()
    private var mMaxProgress: Float = 0.toFloat()
    private var mCurrentProgress: Float = 0.toFloat()

    /**
     * 绘制底部圆环的paint
     */
    private var mCirclePaint: Paint? = null
    /**
     * 绘制圆环的paint
     */
    private var mArcPaint: Paint? = null

    private var mRect: RectF? = null

    /**
     * 渐变色集合
     */
    private var startColor:Int =0
    private var endColor:Int =0
    /**
     * 底部圆环的颜色
     */
    private var mCircleColor: Int = 0

    private var mSweepGradient: SweepGradient? = null

    init {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView)
        if (array != null) {
            mProgressWidth = array.getDimension(R.styleable.ProgressView_progress_with, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, resources.displayMetrics))
            mRadius = array.getDimension(R.styleable.ProgressView_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, resources.displayMetrics))
            mMaxProgress = array.getFloat(R.styleable.ProgressView_max_progress, 100f)
            mCurrentProgress = array.getFloat(R.styleable.ProgressView_progress, 0f)
            mCircleColor = array.getColor(R.styleable.ProgressView_circle_color, resources.getColor(R.color.abc_background_cache_hint_selector_material_light))
            //设置默认颜色
            startColor = array.getColor(R.styleable.ProgressView_start_arc_color, resources.getColor(R.color.abc_background_cache_hint_selector_material_dark))
            endColor = array.getColor(R.styleable.ProgressView_start_arc_color, resources.getColor(R.color.abc_background_cache_hint_selector_material_dark))
            array.recycle()
        }
        initPaint()
        mRect = RectF()
    }

    private fun initPaint() {
        mCirclePaint = Paint()
        mCirclePaint!!.isAntiAlias = true
        mCirclePaint!!.isDither = true
        mCirclePaint!!.color = mCircleColor
        mCirclePaint!!.style = Paint.Style.STROKE
        mCirclePaint!!.strokeWidth = mProgressWidth
        mCirclePaint!!.strokeCap = Paint.Cap.ROUND
        mArcPaint = Paint()
        mArcPaint!!.isAntiAlias = true
        mArcPaint!!.isDither = true
        mArcPaint!!.strokeWidth = mProgressWidth
        mArcPaint!!.style = Paint.Style.STROKE
        mArcPaint!!.strokeCap = Paint.Cap.ROUND
        mArcPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    fun setProgress(progress: Float) {
        val animator = ValueAnimator.ofFloat(0f, progress)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            mCurrentProgress = animation.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    /**
     * 设置进度的颜色，多个时带有渐变色
     * 如果是单色的，需要设置2个相同的颜色
     *
     * @param colors
     */
    fun setProgressColors(colors: IntArray) {
        if (colors == null || colors!!.size < 2) {
            return
        }
        startColor = colors[0]
        endColor = colors[1]
    }

    /**
     * 设置底部圆环的
     *
     * @param color
     */
    fun setCircleColor(color: Int) {
        mCircleColor = color
        mCirclePaint!!.color = mCircleColor
    }

    /**
     * 设置最大进度
     *
     * @param maxProgress
     */
    fun setMaxProgress(maxProgress: Float) {
        mMaxProgress = maxProgress
    }

    /**
     * 设置进度宽度
     *
     * @param progressWidth
     */
    fun setProgressWidth(progressWidth: Float) {
        mProgressWidth = progressWidth
    }

    /**
     * 设置半径
     */
    fun setRadius(radius: Float) {
        mRadius = radius
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = 0
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        when (widthMode) {
            View.MeasureSpec.EXACTLY -> width = widthSize
            View.MeasureSpec.AT_MOST -> width = mRadius.toInt() * 2
            View.MeasureSpec.UNSPECIFIED -> width = widthSize
        }
        //保持高度与宽度相同
        setMeasuredDimension(width, width)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //如果设置的半径大于宽度的一半或者没设置半径，则使用宽度的一半作为半径
        if (mRadius > width / 2 - mProgressWidth / 2 || mRadius == 0f) {
            mRadius = width / 2 - mProgressWidth / 2
        }


        mRect!!.set(width / 2 - mRadius, height / 2 - mRadius, width / 2 + mRadius, height / 2 + mRadius)


        if (mMaxProgress > 0) {
            if (mSweepGradient == null) {
                mSweepGradient = SweepGradient(mRect!!.width() / 2, mRect!!.height() / 2, intArrayOf(startColor,endColor), null)
                mArcPaint!!.shader = mSweepGradient
            }
            canvas.save()
            //原来绘制从x轴正方向开始绘制，旋转画布135，使其从左下45开始绘制
            canvas.rotate(135f, (width / 2).toFloat(), (height / 2).toFloat())
            //绘制底部的圆环
            canvas.drawArc(mRect!!, 0f, 270f, false, mCirclePaint!!)
            canvas.drawArc(mRect!!, 0f, 270 * (mCurrentProgress / mMaxProgress), false, mArcPaint!!)
            canvas.restore()
        }
    }
}
