package com.dreamteam.vicam.view.custom;


import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.events.DeletePresetsEvent;
import com.dreamteam.vicam.model.events.EditPresetDialogEvent;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.presenter.utility.Dagger;

import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by fsommar on 2014-05-04.
 */
public class DrawerMultiChoiceListener implements AbsListView.MultiChoiceModeListener {

  @Inject
  EventBus mEventBus;

  private final Context mContext;
  private final ListView mList;
  private ActionMode mActionMode;
  private List<Preset> mSelected = new ArrayList<>();

  public DrawerMultiChoiceListener(Context context, ListView list) {
    Dagger.inject(this);
    this.mContext = context;
    this.mList = list;
  }

  @Override
  public void onItemCheckedStateChanged(ActionMode mode, int position,
                                        long id, boolean checked) {
    Preset preset = (Preset) mList.getItemAtPosition(position);
    if (checked) {
      mSelected.add(preset);
    } else {
      mSelected.remove(preset);
    }

    mode.invalidate();
  }

  @Override
  public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    switch (item.getItemId()) {
      case R.id.context_delete_preset:
        mode.finish(); // Action picked, so close the CAB
        mEventBus.post(new DeletePresetsEvent(mSelected));
        return true;
      case R.id.context_edit_preset:
        if (mSelected.size() != 1) {
          return false;
        }
        mEventBus.post(new EditPresetDialogEvent(mSelected.get(0)));
        mode.finish();
        return true;
      default:
        return false;
    }
  }

  @Override
  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    mActionMode = mode;
    MenuInflater inflater = mode.getMenuInflater();
    inflater.inflate(R.menu.context, menu);
    return true;
  }

  @Override
  public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    MenuItem edit = menu.findItem(R.id.context_edit_preset);
    MenuItem delete = menu.findItem(R.id.context_delete_preset);

    boolean oneSelection = mList.getCheckedItemCount() <= 1;
    edit.setVisible(oneSelection);
    edit.setEnabled(oneSelection);

    int deleteTitle = oneSelection ? R.string.delete_preset : R.string.delete_presets;
    delete.setTitle(mContext.getString(deleteTitle));

    return true;
  }

  @Override
  public void onDestroyActionMode(ActionMode mode) {
    mActionMode = null;
    mSelected.clear();
  }

  public void close() {
    if (mActionMode != null) {
      mActionMode.finish();
    }
  }
}


