package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LogFragment extends Fragment {


    protected abstract String createTag();

    private String mTAG = "%%%/fragments/" + createTag();


    protected String getTAG(){
        return mTAG;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("lc: onCreate()");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("lc: onAttach()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("lc: onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("lc: onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("lc: onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("lc: onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("lc: onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("lc: onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("lc: onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("lc: onDetach()");
    }

    protected void log(String msg){
        Log.i( mTAG, msg);
    }

    protected void logError(String msg){
        Log.e(mTAG, msg);
    }

}
