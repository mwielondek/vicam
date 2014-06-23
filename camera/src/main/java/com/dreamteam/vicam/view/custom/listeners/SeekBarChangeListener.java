package com.dreamteam.vicam.view.custom.listeners;

import android.widget.SeekBar;

import com.dreamteam.vicam.model.pojo.Zoom;
import com.dreamteam.vicam.presenter.network.camera.CameraFacade;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.dreamteam.vicam.view.MainActivity;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Manages the focus and zoom seek bars by updating the web camera on seek bar changes.
 *
 * @author Donia Alipoor
 * @author Dajana Vlajic
 * @author Fredrik Sommar
 * @since 2014-04-26.
 */
public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

  private final MainActivity mActivity;

  public enum Type {FOCUS, ZOOM}

  private Type seekBarType;

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
    // Seek bar updates are only done when the finger is lifted from the seek bar.
    // It's done to minimize the amount of network requests to the web camera.
    doAction(level);
  }

  private void doAction(int level) {
    if (seekBarType == Type.ZOOM) {
      zoom(level);
    } else if (seekBarType == Type.FOCUS) {
      focus(level);
    }
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
            Utils.debugLog("FOCUS");
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
            Utils.debugLog("ZOOM");
          }
        }, Utils.<Throwable>noop()
    );
  }

  /**
   * Converts the seek bar progress value to the scale used on the web camera.
   *
   * @param max The maximum progress value. 0 is expected to be the minimum.
   */
  public static int progressToLevel(int progress, int max) {
    return progress * Zoom.RANGE / max + Zoom.LOWER_BOUND;
  }

  /**
   * Converts the value used on the web camera to the progress scale used in the application.
   *
   * @param max The maximum progress value. 0 is expected to be the minimum.
   */
  public static int levelToProgress(int level, int max) {
    return (level - Zoom.LOWER_BOUND) * max / Zoom.RANGE;
  }
}
