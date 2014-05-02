package com.dreamteam.vicam.view.custom;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.dreamteam.vicam.model.pojo.Speed;
import com.dreamteam.vicam.view.MainActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static java.lang.Thread.sleep;

/**
 * Created by fsommar on 2014-04-26.
 */
public class TouchpadTouchListener implements View.OnTouchListener {
  private final MainActivity activity;
  public TouchpadTouchListener(MainActivity activity) {
    this.activity = activity;
  }
  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {

    int runTime = 130;
    long startTime = System.currentTimeMillis();

    float eventX = motionEvent.getX();
    float eventY = motionEvent.getY();
    int normX = (int) (eventX / view.getWidth() * Speed.UPPER_BOUND + Speed.LOWER_BOUND);
    int normY = (int) (eventY / view.getHeight() * Speed.UPPER_BOUND + Speed.LOWER_BOUND);

    if (normX < Speed.LOWER_BOUND || normX > Speed.UPPER_BOUND
        || normY < Speed.LOWER_BOUND || normY > Speed.UPPER_BOUND) {
      return false;
    }
    switch (motionEvent.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
        activity.getFacade()
            .moveStart(new Speed(normX, normY))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                new Action1<String>() {
                  @Override
                  public void call(String s) {
                    activity.showToast("debug", Toast.LENGTH_SHORT);
                  }
                }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                    activity.showToast("ERRRR", Toast.LENGTH_SHORT);
                  }
                }
            );
        return true;


      case MotionEvent.ACTION_UP:

        activity.getFacade()
            .moveStop()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread()).subscribe(
            new Action1<String>() {
              @Override
              public void call(String s) {
                activity.showToast("debugstop", Toast.LENGTH_SHORT);
              }
            }, new Action1<Throwable>() {
              @Override
              public void call(Throwable throwable) {
                activity.showToast("ERRRRopp", Toast.LENGTH_SHORT);
              }
            }
          );

        long currentTime = runTime - startTime;

        try {
          Thread.sleep(currentTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        return true;
      default:
        return false;
    }
  }
}