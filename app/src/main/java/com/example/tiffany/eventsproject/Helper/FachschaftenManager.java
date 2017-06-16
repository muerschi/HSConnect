package com.example.tiffany.eventsproject.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.ArraySet;

import com.example.tiffany.eventsproject.Model.Fachschaft;
import com.example.tiffany.eventsproject.Model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nina on 09.06.2017.
 */

public class FachschaftenManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String KEY_FS_LIST = "FachschaftsList";

    private Set<String> fsStringSet = null;
    // Constructor
    public FachschaftenManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void refreshFachschaftsList(List<Fachschaft> fachschaftsList){
        if(!fachschaftsList.isEmpty()){
            if(fsStringSet== null){
                fsStringSet= new HashSet<String>();
            }else {
                fsStringSet.clear();
            }
            for (Fachschaft fachschaft : fachschaftsList) {
                String json = new Gson().toJson(fachschaft);
                fsStringSet.add(json);
            }
        }
        
        if(!fsStringSet.isEmpty()){
             editor.putStringSet(KEY_FS_LIST, fsStringSet);
             // commit changes
             editor.commit();
         }
    }

    public List<Fachschaft> getFsList() {
        fsStringSet = pref.getStringSet(KEY_FS_LIST, null);
        List<Fachschaft> fsList = null;

        //TODO: ExceptionHandling when sharedPreferences are empty
        if(!fsStringSet.isEmpty()) {
            fsList = new ArrayList<Fachschaft>();
            Gson gson = new Gson();
            for (String s : fsStringSet) {
                Fachschaft fachschaft = gson.fromJson(s, Fachschaft.class);
                fsList.add(fachschaft);
            }
        }
        
        return fsList;
    }

    public Fachschaft getFachschaft(String fsname){

        List<Fachschaft> fsList = getFsList();
        for(Fachschaft fachschaft :fsList ){
            String fsName = fachschaft.getName().toString();
            if (fsName.equalsIgnoreCase(fsname)){
                return fachschaft;
            }
        }

        // Add not found Exception
        return null;
    }

    public boolean doesExist(){


        // If default value is returned, shared preferences is empty
        if( pref.contains(KEY_FS_LIST))
        {
            return true;
        }

        return false;

    }
}
