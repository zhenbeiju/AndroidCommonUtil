package commanutil.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import commanutil.utl.LogManager;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by Zhanglin on 2016/11/1.
 */

public class DialogTransform<T> implements Observable.Transformer<T, T> {
    private Context context;
    private String msg;
    private Subscription subscription;

    public DialogTransform(Context context) {
        this.context = context;
    }

    public DialogTransform(Context context, String msg) {
        this.context = context;
        this.msg = msg;
    }


    @Override
    public Observable<T> call(final Observable<T> observable) {
        final Dialog dialog = DialogInfo.showLoadingDialog(context, msg, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                observable.unsubscribeOn(Schedulers.io());
                if (subscription != null) {
                    subscription.unsubscribe();
                }
                LogManager.e("unsubscribe");
            }
        });
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                subscription = observable.subscribe(new Subscriber<T>() {
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
