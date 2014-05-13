package com.dreamteam.vicam.view.custom.listeners;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.Speed;
import com.dreamteam.vicam.presenter.network.camera.CameraFacade;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.dreamteam.vicam.view.MainActivity;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by fsommar on 2014-04-26.
 */
public class TouchpadTouchListener implements View.OnTouchListener {

  private final MainActivity mActivity;

  private volatile boolean blocked;
  private Handler blockedHandler = new Handler();
  private Handler tapHandler = new Handler();
  private Vibrator vibrator;
  private Context context;


  public TouchpadTouchListener(MainActivity activity) {
    this.mActivity = activity;
    //  vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
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
        Utils.infoLog("Sent request!");
      } else {
        Utils.infoLog("BLOCKED");
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
        // vibrator.vibrate(1);
        tapHandler.postDelayed(tapRunnable, Utils.DELAY_TIME_MILLIS);

      case MotionEvent.ACTION_MOVE:
        float eventX = motionEvent.getX();
        float eventY = motionEvent.getY();
        final int normX = Math.min(
            (int) (eventX / view.getWidth() * Speed.UPPER_BOUND + Speed.LOWER_BOUND),
            Speed.UPPER_BOUND);
        final int normY = Math.min(
            (int) (eventY / view.getHeight() * Speed.UPPER_BOUND + Speed.LOWER_BOUND),
            Speed.UPPER_BOUND);

        if (normX < Speed.LOWER_BOUND || normX > Speed.UPPER_BOUND
            || normY < Speed.LOWER_BOUND || normY > Speed.UPPER_BOUND) {
          return false;
        }

        // If only java would've had lambdas...
        mActivity.prepareObservable(
            mActivity.getCurrentCamera().map(new Func1<Camera, Speed>() {
              @Override
              public Speed call(Camera camera) {
                int x = normX;
                int y = normY;

                if (camera.isInvertX()) {
                  x = 100 - x;
                }
                if (camera.isInvertY()) {
                  y = 100 - y;
                }
                return new Speed(x, y);
              }
            }).flatMap(new Func1<Speed, Observable<String>>() {
              @Override
              public Observable<String> call(final Speed speed) {
                return mActivity.getFacade().flatMap(new Func1<CameraFacade, Observable<String>>() {
                  @Override
                  public Observable<String> call(CameraFacade cameraFacade) {
                    return cameraFacade.moveStart(speed);
                  }
                });
              }
            })
        ).subscribe(Utils.noop(), Utils.<Throwable>noop());

        return true;

      case MotionEvent.ACTION_UP:
        // interrupt tap handler
        tapHandler.removeCallbacks(tapRunnable);
        new Thread(new Runnable() {
          @Override
          public void run() {
            while (blocked) {
              // block
            }
            stopCameraMoving();
          }
        }).start();
        return true;
      default:
        return false;
    }

  }

  private void stopCameraMoving() {
    mActivity.prepareObservable(
        mActivity.getFacade().flatMap(new Func1<CameraFacade, Observable<String>>() {
          @Override
          public Observable<String> call(CameraFacade cameraFacade) {
            return cameraFacade.moveStop();
          }
        })
    ).subscribe(Utils.noop(), Utils.<Throwable>noop());
  }
}