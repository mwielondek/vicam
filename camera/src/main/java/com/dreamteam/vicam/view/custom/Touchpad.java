package com.dreamteam.vicam.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * A grey square with square outlines on top of it..
 *
 * @author Fredrik Sommar
 * @since 2014-05-04.
 */
public class Touchpad extends View {

  private final Paint mBackgroundPaint;
  private final Paint mAccentPaint;
  private RectF mBackgroundBounds;
  private RectF mOuterSquare;
  private RectF mMiddleSquare;
  private RectF mInnerSquare;
  private RectF mInnerBorder;
  private RectF mHorizontalLine;
  private RectF mVerticalLine;

  public Touchpad(Context context, AttributeSet attrs) {
    super(context, attrs);
    mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mBackgroundPaint.setStyle(Paint.Style.FILL);
    mBackgroundPaint.setColor(getResources().getColor(android.R.color.darker_gray));

    mAccentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mAccentPaint.setStyle(Paint.Style.STROKE);
    mAccentPaint.setColor(getResources().getColor(android.R.color.background_light));

    mBackgroundBounds = new RectF(0.0f, 0.0f, 0, 0);
    mHorizontalLine = new RectF(0.0f, 0.0f, 0, 0);
    mVerticalLine = new RectF(0.0f, 0.0f, 0, 0);
    mInnerBorder = new RectF(0.0f, 0.0f, 0, 0);
    mOuterSquare = new RectF(0.0f, 0.0f, 0, 0);
    mMiddleSquare = new RectF(0.0f, 0.0f, 0, 0);
    mInnerSquare = new RectF(0.0f, 0.0f, 0, 0);
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    // Account for padding
    float xpad = (float) (getPaddingLeft() + getPaddingRight());
    float ypad = (float) (getPaddingTop() + getPaddingBottom());

    float ww = (float) w - xpad;
    float hh = (float) h - ypad;

    // Figure out how big we can make the pie.
    float size = Math.min(ww, hh);
    mBackgroundBounds = new RectF(0.0f, 0.0f, size, size);
    mBackgroundBounds.offsetTo(getPaddingLeft(), getPaddingTop());

    mHorizontalLine = new RectF(mBackgroundBounds.width() / 20, mBackgroundBounds.centerY(),
                                19 * mBackgroundBounds.width() / 20, mBackgroundBounds.centerY());

    mVerticalLine = new RectF(mBackgroundBounds.centerX(), mBackgroundBounds.height() / 20,
                              mBackgroundBounds.centerX(), 19 * mBackgroundBounds.height() / 20);

    mInnerBorder = new RectF(
        0.0f, 0.0f, mBackgroundBounds.width() - 10, mBackgroundBounds.height() - 10);
    mInnerBorder.offsetTo(getPaddingLeft() + 5, getPaddingTop() + 5);

    mOuterSquare = new RectF(
        0.0f, 0.0f, 3 * mBackgroundBounds.width() / 4, 3 * mBackgroundBounds.height() / 4);
    mOuterSquare.offsetTo(getPaddingLeft() + mBackgroundBounds.width() / 8,
                          getPaddingTop() + mBackgroundBounds.height() / 8);

    mMiddleSquare = new RectF(
        0.0f, 0.0f, mBackgroundBounds.width() / 2, mBackgroundBounds.height() / 2);
    mMiddleSquare.offsetTo(getPaddingLeft() + mBackgroundBounds.width() / 4,
                           getPaddingTop() + mBackgroundBounds.height() / 4);

    mInnerSquare = new RectF(
        0.0f, 0.0f, mBackgroundBounds.width() / 4, mBackgroundBounds.height() / 4);
    mInnerSquare.offsetTo(getPaddingLeft() + 3 * mBackgroundBounds.width() / 8,
                          getPaddingTop() + 3 * mBackgroundBounds.height() / 8);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // Try for a width based on our minimum
    int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();

    int w = Math.max(minw, MeasureSpec.getSize(widthMeasureSpec));

    // Whatever the width ends up being, ask for a height that would let the pie
    // get as big as it can
    int minh = w + getPaddingBottom() + getPaddingTop();
    int h = Math.min(MeasureSpec.getSize(heightMeasureSpec), minh);

    int size = Math.min(w, h);

    setMeasuredDimension(size, size);
  }


  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawRect(mBackgroundBounds, mBackgroundPaint);

    canvas.drawRect(mInnerBorder, mAccentPaint);
    canvas.drawLine(mHorizontalLine.left, mHorizontalLine.top, mHorizontalLine.right,
                    mHorizontalLine.bottom, mAccentPaint);
    canvas.drawLine(mVerticalLine.left, mVerticalLine.top, mVerticalLine.right,
                    mVerticalLine.bottom, mAccentPaint);

    canvas.drawRect(mInnerSquare, mAccentPaint);
    canvas.drawRect(mMiddleSquare, mAccentPaint);
    canvas.drawRect(mOuterSquare, mAccentPaint);
  }
}
