package com.dreamteam.vicam.view.custom.listeners;

import android.view.MotionEvent;
import android.view.View;

import com.dreamteam.vicam.model.pojo.Speed;
import com.dreamteam.vicam.model.pojo.Zoom;
import com.dreamteam.vicam.presenter.network.camera.CameraFacade;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.dreamteam.vicam.view.MainActivity;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Manages zoom in and zoom out buttons by sending the corresponding request to the web camera on
 * click. The speed of zooming when clicking the buttons is determined by {@link #ZOOM_SPEED}.
 *
 * @author Fredrik Sommar
 * @since 2014-05-13.
 */
public class ZoomButtonTouchListener implements View.OnTouchListener {

  public static final int ZOOM_SPEED = 9;

  public enum Type {ZOOM_IN, ZOOM_OUT}

  private MainActivity mActivity;
  private Type type;

  public ZoomButtonTouchListener(MainActivity mActivity, Type type) {
    this.mActivity = mActivity;
    this.type = type;
  }

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
      mActivity.prepareObservable(
          mActivity.getFacade().flatMap(new Func1<CameraFacade, Observable<?>>() {
            @Override
            public Observable<?> call(CameraFacade cameraFacade) {
              int speed = Speed.STOP; // default to no speed
              if (type == Type.ZOOM_IN) {
                speed = Speed.LOWER_BOUND + ZOOM_SPEED;
              } else if (type == Type.ZOOM_OUT) {
                speed = Speed.UPPER_BOUND - ZOOM_SPEED;
              }
              return cameraFacade.zoomStart(speed);
            }
          })
      ).subscribe(Utils.noop(), Utils.<Throwable>noop());

    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
               || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
      mActivity.prepareObservable(
          mActivity.getFacade().flatMap(new Func1<CameraFacade, Observable<Zoom>>() {
            @Override
            public Observable<Zoom> call(final CameraFacade cameraFacade) {
              return cameraFacade.zoomStop().flatMap(new Func1<String, Observable<Zoom>>() {
                @Override
                public Observable<Zoom> call(String s) {
                  return cameraFacade.getZoom();
                }
              });
            }
          })
      ).subscribe(
          new Action1<Zoom>() {
            @Override
            public void call(Zoom zoom) {
              mActivity.updateZoomLevel(zoom.getLevel());
            }
          },
          Utils.<Throwable>noop()
      );
    }
    return false;
  }
}
