package pro.sunriseforest.sunriseforestapp_client.date;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.Ad;

public class DataBaseHelper {


    private static  DataBaseHelper sInstance;

    private static DataBaseHelper getInstance(){
        if(sInstance == null){
            sInstance = new DataBaseHelper();
        }
        return sInstance;
    }

    private List<Ad> mAds;
    private DataBaseHelper(){
        mAds = new ArrayList<>();
    }

    public List<Ad> getAds(){

        List<Ad> copyAds = new ArrayList<>();
        for(Ad ad : mAds){
            copyAds.add(ad.copy());
        }
        return copyAds;
    }

    private @Nullable Ad _getAdById(int id){

        for(Ad _ad : mAds){
            if(_ad.getId() == id) return _ad;
        }

        return null;
    }

    public @Nullable Ad getAdById(int id){
        Ad ad = _getAdById(id);
        return ad == null ? null : ad.copy();
    }

    public void cacheAd(Ad ad){
       for(int i = 0; i < mAds.size(); i++){
           if(mAds.get(i).getId() == ad.getId()){
               mAds.set(i, ad);
               return;
           }
       }
       mAds.add(ad);
    }

    public void cacheAds(List<Ad> ads){
        for(Ad ad: ads){
            cacheAd(ad);
        }
    }
    
}
