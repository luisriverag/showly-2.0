package com.michaldrabik.showly2.ui.tutorial

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.michaldrabik.showly2.R

class TutorialTipView : FrameLayout {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  companion object {
    private const val ANIMATION_DURATION = 2500L
  }

  init {
    inflate(context, R.layout.view_tutorial_tip, this)
    layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    clipChildren = false
  }

  private val animatorX by lazy {
    ObjectAnimator.ofFloat(this, "scaleX", 1F, 1.2F, 1F, 0.8F, 1F).apply {
      repeatCount = INFINITE
    }
  }

  private val animatorY by lazy {
    ObjectAnimator.ofFloat(this, "scaleY", 1F, 1.2F, 1F, 0.8F, 1F).apply {
      repeatCount = INFINITE
    }
  }

  private val animatorSet by lazy {
    AnimatorSet().apply {
      playTogether(animatorX, animatorY)
      duration = ANIMATION_DURATION
    }
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    animatorSet.start()
  }

  override fun onDetachedFromWindow() {
    animatorSet.cancel()
    super.onDetachedFromWindow()
  }
}
