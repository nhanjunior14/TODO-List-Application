package project.group.todo.Fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import project.group.todo.R;

public class Settings extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}