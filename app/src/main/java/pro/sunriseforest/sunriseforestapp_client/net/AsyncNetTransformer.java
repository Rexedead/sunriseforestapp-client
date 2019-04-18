package pro.sunriseforest.sunriseforestapp_client.net;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AsyncNetTransformer <T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.first()
                .subscribeOn(Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread());
    }
}
