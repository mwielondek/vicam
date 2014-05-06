package com.dreamteam.vicam.view.custom;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dreamteam.vicam.model.pojo.Speed;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.dreamteam.vicam.view.MainActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by fsommar on 2014-04-26.
 */
public class TouchpadTouchListener implements View.OnTouchListener {

  private final MainActivity activity;

  private volatile boolean blocked;
  private Handler blockedHandler = new Handler();
  private Handler tapHandler = new Handler();

  public TouchpadTouchListener(MainActivity activity) {
    this.activity = activity;
  }

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {

    if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
      if (!blocked) {
        blocked = true;
        blockedHandler.postDelayed(new Runnable() {
          @Override
          public void run() {
            blocked = false;
          }
        }, Utils.DELAY_TIME_MILLIS);
        System.out.println("SENT REQUEST");
      } else {
        System.out.println("BLOCKED");
        return false;
      }
    }

    Runnable tapRunnable = new Runnable() {
      @Override
      public void run() {
        stopCameraMoving();
      }
    };

    switch (motionEvent.getAction()) {
      case MotionEvent.ACTION_DOWN:
        tapHandler.postDelayed(tapRunnable, Utils.DELAY_TIME_MILLIS);
      case MotionEvent.ACTION_MOVE:
        float eventX = motionEvent.getX();
        float eventY = motionEvent.getY();
        int normX = (int) (eventX / view.getWidth() * Speed.UPPER_BOUND + Speed.LOWER_BOUND);
        int normY = (int) (eventY / view.getHeight() * Speed.UPPER_BOUND + Speed.LOWER_BOUND);
        Log.i("MYTAG", String.format("norm: (%d, %d)", normX, normY));

        if (normX < Speed.LOWER_BOUND || normX > Speed.UPPER_BOUND
            || normY < Speed.LOWER_BOUND || normY > Speed.UPPER_BOUND) {
          return false;
        }

        activity.getFacade()
            .moveStart(new Speed(normX, normY))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                new Action1<String>() {
                  @Override
                  public void call(String s) {
                  }
                }, new Action1<Throwable>() {
                  @Override
                  public void call(Throwable throwable) {
                    System.out.println("ACTION_DOWN!!!");
                  }
                }
            );
        return true;

      case MotionEvent.ACTION_UP:
        // interrupt tap handler
        tapHandler.removeCallbacks(tapRunnable);
        stopCameraMoving();
        return true;
      default:
        return false;
    }

  }

  private void stopCameraMoving() {
    activity.getFacade()
        .moveStop()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.newThread()).subscribe(
        new Action1<String>() {
          @Override
          public void call(String s) {
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            System.out.println("ACTION_UP!!!");
          }
        }
    );
  }
}