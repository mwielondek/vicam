package com.dreamteam.vicam.view.custom.listeners;

import android.os.Handler;
import android.widget.SeekBar;

import com.dreamteam.vicam.model.pojo.Zoom;
import com.dreamteam.vicam.presenter.network.camera.CameraFacade;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.dreamteam.vicam.view.MainActivity;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by fsommar on 2014-04-26.
 */
public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

  private final MainActivity mActivity;

  public enum Type {FOCUS, ZOOM}

  private Type seekBarType;
  private Handler mHandler = new Handler();
  private long lastRequestMillis;

  public SeekBarChangeListener(MainActivity activity, Type seekBarType) {
    this.mActivity = activity;
    this.seekBarType = seekBarType;
  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
    final int level = progressToLevel(seekBar.getProgress(), seekBar.getMax());
    doAction(level);
  }

  private void doAction(int level) {
    if (seekBarType == Type.ZOOM) {
      zoom(level);
    } else if (seekBarType == Type.FOCUS) {
      focus(level);
    }
    lastRequestMillis = System.currentTimeMillis();
  }

  private void focus(final int level) {
    mActivity.prepareObservable(
        mActivity.getFacade().flatMap(new Func1<CameraFacade, Observable<String>>() {
          @Override
          public Observable<String> call(CameraFacade cameraFacade) {
            return cameraFacade.focusAbsolute(level);
          }
        })
    ).subscribe(
        new Action1<String>() {
          @Override
          public void call(String s) {
            Utils.infoLog("FOCUS");
          }
        }, Utils.<Throwable>noop()
    );
  }

  private void zoom(final int level) {
    mActivity.prepareObservable(
        mActivity.getFacade().flatMap(new Func1<CameraFacade, Observable<String>>() {
          @Override
          public Observable<String> call(CameraFacade cameraFacade) {
            return cameraFacade.zoomAbsolute(level);
          }
        })
    ).subscribe(
        new Action1<String>() {
          @Override
          public void call(String s) {
            Utils.infoLog("ZOOM");
          }
        }, Utils.<Throwable>noop()
    );
  }

  public static int progressToLevel(int progress, int max) {
    return progress * Zoom.RANGE / max + Zoom.LOWER_BOUND;
  }

  public static int levelToProgress(int level, int max) {
    return (level - Zoom.LOWER_BOUND) * max / Zoom.RANGE;
  }
}
