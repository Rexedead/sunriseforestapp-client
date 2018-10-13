package pro.sunriseforest.sunriseforestapp_client.server;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.Ad;

public class ServerHelper {

    private static ServerHelper sInstance;

    public static ServerHelper getInstance(){
        if(sInstance == null){
            sInstance = new ServerHelper();
        }

        return sInstance;
    }

    private List<Ad> mAds;

    private ServerHelper(){
        mAds = Arrays.asList(
                new Ad(1, "выруби лес плес)))0)"),
                new Ad(2, "го рубить лес"),
                new Ad(3, "В России, столь могущественной своей матерьяльной силой" +
                        " и силой своего духа, нет войска; есть толпы угнетенных рабов, " +
                        "повинующихся ворам, угнетающим наемникам и грабителям, и в этой толпе нет " +
                        "ни преданности к царю, ни любви к отечеству — слова, которые так часто " +
                        "злоупотребляют,— ни рыцарской чести и отваги, есть с одной стороны дух терпения" +
                        " и подавленного ропота, с другой дух угнетения и лихоимства.")
        );

    }

    public List<Ad> getAds() {
        List<Ad> copyAds = new ArrayList<>();
        for(Ad ad : mAds){
            copyAds.add(ad.copy());
        }
        return copyAds;
    }

    private @Nullable
    Ad _getAdById(int id){

        for(Ad _ad : mAds){
            if(_ad.getId() == id) return _ad;
        }

        return null;
    }

    public @Nullable Ad getAdById(int id){
        Ad ad = _getAdById(id);
        return ad == null ? null : ad.copy();
    }

    public boolean sendAd(Ad ad){


        for(int i = 0; i < mAds.size(); i++){
            if(mAds.get(i).getId() == ad.getId()){
                mAds.set(i, ad);
                return true;
            }
        }
        return false;
    }


}
