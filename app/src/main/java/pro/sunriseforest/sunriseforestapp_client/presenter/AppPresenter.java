package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.support.v7.app.AppCompatActivity;

public abstract class AppPresenter<A extends AppCompatActivity> {
    
    public abstract void init(A activity);

}
