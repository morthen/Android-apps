package com.martinozgun.notes.widget;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.martinozgun.notes.R;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

/**
 * Created by Martin Özgun.
 */
public class AboutNoticeDialog extends RoboDialogFragment {

    private static final String TAG = AboutNoticeDialog.class.getSimpleName();

    @InjectView(R.id.version_text) private TextView versionText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_about_notice, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            String versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            versionText.setText(getString(R.string.version_format, versionName));
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e(TAG, "Couldn't find version name", ex);
        }
    }
}