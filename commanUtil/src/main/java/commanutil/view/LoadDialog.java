package commanutil.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * Created by zhanglin on 3/31/16.
 */
public class LoadDialog<T> {
    private Context context;

    public LoadDialog(Context context) {
        this.context = context;
    }

    public Observable<T> showLoadDialog(final Observable<T> observable) {
        return showLoadDialog(observable, "loading");
    }



    public Observable<T> showLoadDialog(final Observable<T> observable, String msg) {
        final Dialog dialog = DialogInfo.showLoadingDialog(context, msg, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                observable.unsubscribeOn(Schedulers.io());
            }
        });
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                observable.subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(T o) {
                        subscriber.onNext(o);
                    }
                });
            }
        });


    }


}
