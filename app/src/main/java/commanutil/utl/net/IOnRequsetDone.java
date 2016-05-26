package commanutil.utl.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.RedirectError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import commanutil.utl.LogManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class IOnRequsetDone<T> {

    public RequestFuture<T> listener;
    public RequestFuture<T> errorlisten;


    public IOnRequsetDone() {
        final RequestFuture<T> future = RequestFuture.newFuture();
        listener = future;
        errorlisten = future;
    }


    public Observable getObservable() {
        Observable observable = Observe(listener).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable;
    }


    public Observable Observe(final RequestFuture future) {

        Observable observable = Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable call() {
                try {
                    return Observable.just(future.get());
                } catch (InterruptedException e) {
                    return Observable.error(e);
                } catch (Exception e) {
                    return Observable.error(e.getCause());
                }
            }
        });
        return observable;
    }

    public static JSONObject parseError(Throwable e) {
        JSONObject errorjson = new JSONObject();
        if (e instanceof VolleyError) {
            VolleyError error = (VolleyError) e;
            LogManager.printStackTrace(error);
            try {
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    String errorbody = new String(error.networkResponse.data);
                    LogManager.e(errorbody);
                    errorjson = new JSONObject(errorbody);
                } else {
                    if (error instanceof TimeoutError) {
                        errorjson = new JSONObject("{error:TimeoutError}");
                    } else if (error instanceof ParseError) {
                        errorjson = new JSONObject("{error:ParseError}");
                    } else if (error instanceof RedirectError) {
                        errorjson = new JSONObject("{error:RedirectError}");
                    } else if (error instanceof AuthFailureError) {
                        errorjson = new JSONObject("{error:AuthFailureError}");
                    } else if (error instanceof NetworkError) {
                        errorjson = new JSONObject("{error:TimeoutError}");
                    } else if (error instanceof ServerError) {
                        errorjson = new JSONObject("{error:TimeoutError}");
                    }
                }
            } catch (Exception e1) {
                LogManager.printStackTrace(e1);
            }
        }
        return errorjson;
    }


}
