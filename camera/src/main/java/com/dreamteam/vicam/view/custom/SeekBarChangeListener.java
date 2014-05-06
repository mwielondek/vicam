package com.dreamteam.vicam.view.custom;

import android.widget.SeekBar;
import android.widget.Toast;

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
    final int normProgress = progressToLevel(seekBar);

    if (seekBarType == Type.ZOOM) {
      mActivity.prepareObservable(
          mActivity.getFacade().flatMap(new Func1<CameraFacade, Observable<String>>() {
            @Override
            public Observable<String> call(CameraFacade cameraFacade) {
              return cameraFacade.zoomAbsolute(normProgress);
            }
          })
      ).subscribe(
          new Action1<String>() {
            @Override
            public void call(String s) {
              mActivity.showToast("ZOOM", Toast.LENGTH_SHORT);
            }
          }, Utils.<Throwable>noop()
      );
    } else if (seekBarType == Type.FOCUS) {
      mActivity.prepareObservable(
          mActivity.getFacade().flatMap(new Func1<CameraFacade, Observable<String>>() {
            @Override
            public Observable<String> call(CameraFacade cameraFacade) {
              return cameraFacade.focusAbsolute(normProgress);
            }
          })
      ).subscribe(
          new Action1<String>() {
            @Override
            public void call(String s) {
              mActivity.showToast("FOCUS", Toast.LENGTH_SHORT);
            }
          }, Utils.<Throwable>noop()
      );
    }
  }

  public static int progressToLevel(SeekBar seekBar) {
    return seekBar.getProgress()
           * Zoom.RANGE / seekBar.getMax()
           + Zoom.LOWER_BOUND;
  }

  public static void levelToProgress(SeekBar seekBar, int level) {
    int progress = (level - Zoom.LOWER_BOUND) * seekBar.getMax() / Zoom.RANGE;
    seekBar.setProgress(progress);
  }
}
