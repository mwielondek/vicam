package com.dreamteam.vicam.view.custom;

import android.widget.CompoundButton;

import com.dreamteam.vicam.presenter.network.camera.CameraFacade;
import com.dreamteam.vicam.view.MainActivity;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by fsommar on 2014-05-06.
 */
public class SwitchButtonCheckedListener implements CompoundButton.OnCheckedChangeListener {

  MainActivity mActivity;

  public SwitchButtonCheckedListener(MainActivity mActivity) {
    this.mActivity = mActivity;
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, final boolean isAutofocus) {
    mActivity.prepareObservable(mActivity.getFacade().setAF(isAutofocus))
        .flatMap(new Func1<String, Observable<Integer>>() {
          @Override
          public Observable<Integer> call(String s) {
            // after AF has changed we fetch the new state from camera
            // (focus has probably changed since last time we got its information)
            if (isAutofocus) {
              // The focus level doesn't need to be updated since autofocus was selected
              return Observable.empty();
            }
            return CameraFacade.accountForDelay(mActivity.getFacade().getFocusLevel());
          }
        }).subscribe(
        new Action1<Integer>() {
          @Override
          public void call(Integer focusLevel) {
            mActivity.updateFocusLevel(focusLevel);
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
          }
        }
    );
  }
}
