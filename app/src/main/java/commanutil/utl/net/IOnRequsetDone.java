package commanutil.utl.net;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import commanutil.base.BaseApplication;
import commanutil.utl.LogManager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IOnRequsetDone<T> {
    private RequestBuilder<T> builder;


    public IOnRequsetDone(RequestBuilder<T> b) {
        this.builder = b;
    }

    public Observable<T> getObservable() {
        Observable<T> observable = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    Request request = builder.getReuest();
                    OkHttpClient client;
                    /**
                     * use custom http client for ssl verify
                     */
                    if (request.isHttps()) {
                        client = BaseApplication.okHttpClient(request.url().getHost());
                    } else {
                        client = BaseApplication.httpClient;
                    }

                    Response response = client.newCall(builder.getReuest()).execute();
                    if (response.code() != 200) {
                        T result = builder.parseResut(response.body().bytes());
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("response code: " + response.code()));
                        String body = new String(response.body().bytes());
                        LogManager.e(body);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
