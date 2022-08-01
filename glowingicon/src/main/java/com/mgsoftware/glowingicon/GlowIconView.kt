package com.mgsoftware.glowingicon

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.example.glowingicon.R
import org.mini2Dx.gdx.math.Rectangle

class GlowIconView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val bounds = Rectangle()

    private lateinit var srcBitmap: Bitmap
    private val srcBitmapBounds = Rectangle()
    private val dstSrcBitmapBounds = Rectangle()
    private val srcBitmapMatrix = Matrix()

    private lateinit var extractedAlphaBitmap: Bitmap
    private val extractedAlphaBitmapBounds = Rectangle()
    private val dstExtractedAlphaBitmapBounds = Rectangle()
    private val extractedAlphaBitmapMatrix = Matrix()

    private val tmpRectF = RectF()
    private val tmpRectF2 = RectF()

    var glowEnabled = false
        set(value) {
            field = value
            invalidate()
        }
    var glowColor = Color.WHITE
        set(value) {
            field = value
            bitmapPaint.color = value
            invalidate()
        }
    var glowRadius = 1f
        set(value) {
            field = value
            bitmapPaint.maskFilter = BlurMaskFilter(value, glowStyle)
            invalidate()
        }
    var glowStyle = BlurMaskFilter.Blur.OUTER
        set(value) {
            field = value
            bitmapPaint.maskFilter = BlurMaskFilter(glowRadius, value)
            invalidate()
        }

    var srcInset = 0f
        set(value) {
            field = value
            onSrcInsetChanged(value)
            invalidate()
        }
    var glowInset = 0f
        set(value) {
            field = value
            onGlowInsetChanged(value)
            invalidate()
        }

    private val bitmapPaint = Paint().apply {
        color = glowColor
        maskFilter = BlurMaskFilter(glowRadius, glowStyle)
    }

    init {
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.GlowIconView, defStyleAttr, 0)
        if (a.hasValue(R.styleable.GlowIconView_GlowIconView_glowEnabled))
            glowEnabled = a.getBoolean(
                R.styleable.GlowIconView_GlowIconView_glowEnabled,
                glowEnabled
            )
        if (a.hasValue(R.styleable.GlowIconView_GlowIconView_glowColor))
            glowColor = a.getColor(
                R.styleable.GlowIconView_GlowIconView_glowColor,
                glowColor
            )
        if (a.hasValue(R.styleable.GlowIconView_GlowIconView_glowRadius))
            glowRadius =
                a.getDimension(
                    R.styleable.GlowIconView_GlowIconView_glowRadius,
                    glowRadius
                )
        if (a.hasValue(R.styleable.GlowIconView_GlowIconView_glowStyle))
            glowStyle =
                BlurMaskFilter.Blur.values()[
                        a.getInt(
                            R.styleable.GlowIconView_GlowIconView_glowStyle,
                            BlurMaskFilter.Blur.OUTER.ordinal
                        )
                ]
        if (a.hasValue(R.styleable.GlowIconView_GlowIconView_srcInset))
            srcInset =
                a.getDimension(
                    R.styleable.GlowIconView_GlowIconView_srcInset,
                    srcInset
                )
        if (a.hasValue(R.styleable.GlowIconView_GlowIconView_glowInset))
            glowInset =
                a.getDimension(
                    R.styleable.GlowIconView_GlowIconView_glowInset,
                    glowInset
                )
        a.recycle()

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (drawable is BitmapDrawable) {
            srcBitmap = (drawable as BitmapDrawable).bitmap
            srcBitmapBounds.set(srcBitmap)

            extractedAlphaBitmap = srcBitmap.extractAlpha()
            extractedAlphaBitmapBounds.set(extractedAlphaBitmap)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(0f, 0f, w.toFloat(), h.toFloat())

        if (drawable is VectorDrawable) {
            srcBitmap = Bitmap.createBitmap(
                bounds.width.toInt(),
                bounds.height.toInt(),
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(srcBitmap)
            drawable.bounds.set(0, 0, bounds.width.toInt(), bounds.height.toInt())
            drawable.draw(canvas)
            srcBitmapBounds.set(srcBitmap)

            extractedAlphaBitmap = srcBitmap.extractAlpha()
            extractedAlphaBitmapBounds.set(extractedAlphaBitmap)
        }

        dstSrcBitmapBounds.set(bounds)
        dstSrcBitmapBounds.inset(srcInset)
        srcBitmapMatrix.setRectToRect(
            srcBitmapBounds.toRectF(tmpRectF),
            dstSrcBitmapBounds.toRectF(tmpRectF2),
            Matrix.ScaleToFit.CENTER
        )

        dstExtractedAlphaBitmapBounds.set(bounds)
        dstExtractedAlphaBitmapBounds.inset(glowInset)
        extractedAlphaBitmapMatrix.setRectToRect(
            extractedAlphaBitmapBounds.toRectF(tmpRectF),
            dstExtractedAlphaBitmapBounds.toRectF(tmpRectF2),
            Matrix.ScaleToFit.CENTER
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (glowEnabled)
            canvas.drawBitmap(extractedAlphaBitmap, extractedAlphaBitmapMatrix, bitmapPaint)
        canvas.drawBitmap(srcBitmap, srcBitmapMatrix, null)
    }

    private fun onSrcInsetChanged(value: Float) {
        dstSrcBitmapBounds.set(bounds)
        dstSrcBitmapBounds.inset(value)
        srcBitmapMatrix.setRectToRect(
            extractedAlphaBitmapBounds.toRectF(tmpRectF),
            dstSrcBitmapBounds.toRectF(tmpRectF2),
            Matrix.ScaleToFit.CENTER
        )
    }

    private fun onGlowInsetChanged(value: Float) {
        dstExtractedAlphaBitmapBounds.set(bounds)
        dstExtractedAlphaBitmapBounds.inset(value)
        extractedAlphaBitmapMatrix.setRectToRect(
            extractedAlphaBitmapBounds.toRectF(tmpRectF),
            dstExtractedAlphaBitmapBounds.toRectF(tmpRectF2),
            Matrix.ScaleToFit.CENTER
        )
    }

    private fun Rectangle.toRectF(out: RectF): RectF {
        out.set(x, y, x + width, y + height)
        return out
    }

    private fun Rectangle.inset(value: Float): Rectangle {
        x += value
        y += value
        width -= 2 * value
        height -= 2 * value
        return this
    }

    private fun Rectangle.set(bitmap: Bitmap): Rectangle {
        return set(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
    }
}