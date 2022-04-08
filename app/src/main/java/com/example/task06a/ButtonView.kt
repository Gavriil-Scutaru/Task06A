package com.example.task06a

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import kotlin.math.sqrt

class ButtonView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //circle and text colors
    private val plusCol: Int = Color.RED
    private val minusCol: Int = Color.YELLOW
    private val backCol: Int=Color.argb(100,128,255,0 )
    private val textCol: Int = Color.BLACK

    //paint variables
    private val plusPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setStyle(Paint.Style.FILL)
        //set the paint color using the circle color specified
        setColor(plusCol)
    }

    private val backPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // Set up the paint style
        setStyle(Paint.Style.FILL)
        setColor(backCol)
    }

    private val minusPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setStyle(Paint.Style.FILL)
        setAntiAlias(true)
        setColor(minusCol)
    }

    private val wordsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setColor(textCol)
        //set text properties
        setTextAlign(Paint.Align.CENTER)
        setTextSize(50f * resources.displayMetrics.density)
        setTypeface(Typeface.SANS_SERIF)
    }

    /** The game that is displayed in this view. */
    private val game: GameInterface = ButtonGame()

    private val gameChangeListener: GameInterface.GameChangeListener = GameInterface.GameChangeListener {
        invalidate()
    }

    init {
        game.addGameChangeListener(gameChangeListener)
    }

    private val gestureDetector =
        GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val radius = minOf(width / 3f, height / 8f)

                val centerX = width / 2f
                val centerYPlus = height / 3f
                val centerYMinus = height / 1.5f

                val distPlus = sqrt((e.y - centerYPlus).square() + (e.x - centerX).square())
                val distMinus = sqrt((e.y - centerYMinus).square() + (e.x - centerX).square())

                when {
                    distPlus < radius -> game.buttonPressed('p')
                    distMinus < radius -> game.buttonPressed('m')
                    else -> return false
                }
                //invalidate()
                return true
            }

            private fun Float.square() = this * this

        })

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        //draw the View
        // Measure the size of the canvas, we could take into account padding here
        val canvasWidth = width.toFloat()
        val canvasHeight = height.toFloat()

        //get half of the width and height to locate the centre of the screen
        val viewWidthHalf = canvasWidth / 2f
        val viewHeightThird = canvasHeight / 3f

        val textOffset = run {
            val textBounds = Rect()
            wordsPaint.getTextBounds("X", 0, 1, textBounds)
            (textBounds.top + textBounds.bottom) / -2f
        }

        // Background
        canvas?.drawRect(0f, 0f, canvasWidth, canvasHeight, backPaint)

        // Retrieve the value of score from the game class instead of 0
        val score = game.counter.toString()

        canvas?.drawText(score, viewWidthHalf, canvasHeight / 8f + textOffset, wordsPaint)

        // Circles
        val radius: Float = minOf(canvasWidth/3f, canvasHeight/8f)

        canvas?.drawCircle(viewWidthHalf, viewHeightThird, radius, plusPaint)
        canvas?.drawText("+", viewWidthHalf, viewHeightThird + textOffset, wordsPaint)

        canvas?.drawCircle(viewWidthHalf, 2f * viewHeightThird, radius, minusPaint)
        canvas?.drawText("-", viewWidthHalf, 2f * viewHeightThird + textOffset, wordsPaint)


    }
}