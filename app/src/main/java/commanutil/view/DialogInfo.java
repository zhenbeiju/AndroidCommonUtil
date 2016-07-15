package commanutil.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhenbeiju.commanutil.R;

import java.util.List;

import commanutil.base.BaseApplication;
import commanutil.utl.LogManager;
import commanutil.utl.ScreenUtil;


public class DialogInfo {

    private static final ProgressDialog progressDialog = null;
    private static AlertDialog dialog = null;
    private static Dialog dialogCreate = null;
    private static Dialog loadingdialog = null;
    private static Toast toast;
    private static int posSelect = 0;
    public static final int HANDLER_PROGRESS_UPDATE = 11;
    public static final int HANDLER_TIPTEXT_UPDATE = 21;

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     */

    public static Dialog showLoadingDialog(Context context, String msg) {
        return showLoadingDialog(context, msg, null);
    }

    public static Dialog showLoadingDialog(final Context context, String msg, final DialogInterface.OnCancelListener onCancelListener) {
        dismissLoadingDialog();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        final LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        tipTextView.setText(msg);// 设置加载信息
        // 创建自定义样式dialog


        loadingdialog = new Dialog(context, R.style.custom_dialog);
        loadingdialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        loadingdialog.show();
        LogManager.e("show");
        loadingdialog.setCancelable(true);
        if (onCancelListener != null) {
            loadingdialog.setOnCancelListener(onCancelListener);
        }
        return loadingdialog;
    }


    public static Handler showProgressDialog(final Context context, final String msg, final DialogInterface.OnCancelListener onCancelListener) {
        dismissDialog();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_progress_loading, null);// 得到加载view
        final LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局

        final ProgressBarIndeterminateDeterminate progressbar = (ProgressBarIndeterminateDeterminate) v.findViewById(R.id.ProgressBarIndeterminateDeterminate);
        final TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        tipTextView.setText(msg);// 设置加载信息
        Handler handler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                switch (msg.what) {
                    case HANDLER_PROGRESS_UPDATE:
                        int value = (int) msg.obj;
                        if (value > 0) {
                            progressbar.setProgress(value);
                            tipTextView.setText(value + "%");
                        }
                        break;
                    case HANDLER_TIPTEXT_UPDATE:
                        String content = (String) msg.obj;
                        tipTextView.setText(content);
                        break;
                    default:
                        break;
                }
            }
        };


        // 创建自定义样式dialog
        BaseApplication.mHandler.post(new Runnable() {
            @Override
            public void run() {
                dialogCreate = new Dialog(context, R.style.custom_dialog);
                dialogCreate.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
                dialogCreate.show();
                dialogCreate.setCancelable(true);
                if (onCancelListener != null) {
                    dialogCreate.setOnCancelListener(onCancelListener);
                }
            }
        });


        return handler;
    }

    public static void dismissToast() {
        dismissDialog();

    }

    public static void dismissDialog() {
        try {
            if (toast != null)
                toast.cancel();
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
        try {
            if (dialog != null)
                dialog.dismiss();
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
        try {
            if (progressDialog != null)
                progressDialog.dismiss();
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }


        try {
            LogManager.e("dismiss");
            if (loadingdialog != null) {
                loadingdialog.dismiss();
                LogManager.e("dismiss");
            }
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }


        try {
            LogManager.e("dismiss");
            if (dialogCreate != null) {
                dialogCreate.dismiss();
                LogManager.e("dismiss");
            }
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
    }

    public static void dismissLoadingDialog() {
        dismissDialog();
    }

    public static void showOneBtnDialog(Context context, String title, String strmsg) {
        showOneBtnDialog(context, strmsg, title, null);
    }

    public static void showOneBtnDialog(Context context, String title, String strmsg, DialogInterface.OnClickListener onClickListener) {
        showOneBtnDialog(context, strmsg, title, onClickListener, null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Dialog showOneBtnDialog(Context context, String title, String strmsg, DialogInterface.OnClickListener onClickListener, DialogInterface.OnDismissListener onDismissListener) {
        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(strmsg)
                .setPositiveButton(R.string.confirm, onClickListener).setOnDismissListener(onDismissListener);
        dialog = builder.create();
        dialog.show();
        return dialog;
    }


    public static Dialog showSelectDialog(Context context, final String[] strs, String title) {
        dismissDialog();
        posSelect = 0;
        dialog = new AlertDialog.Builder(context).setTitle(title)
                .setSingleChoiceItems(strs, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        posSelect = which;
                    }
                }).setPositiveButton(R.string.confirm, null).setNeutralButton(R.string.cancel, null).create();
        dialog.show();
        return dialog;
    }

    public static Dialog showCustomView(Context Context, View v, String title) {
        return showCustomView(Context, v, title, null);
    }

    public static Dialog showCustomView(final Context context, View v, String title, final DialogInterface.OnCancelListener onCancelListener) {
        final View viewcontaine = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
        final TextView titletv = (TextView) viewcontaine.findViewById(R.id.title);// 提示文字
        titletv.setText(title);
        LinearLayout layout = (LinearLayout) viewcontaine.findViewById(R.id.viewcontainer);
        layout.addView(v);

        BaseApplication.mHandler.post(new Runnable() {
            @Override
            public void run() {
                dialogCreate = new Dialog(context, R.style.custom_dialog);
                dialogCreate.setContentView(viewcontaine, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
                dialogCreate.show();
                dialogCreate.setCancelable(true);
                if (onCancelListener != null) {
                    dialogCreate.setOnCancelListener(onCancelListener);
                }
            }
        });
        return dialogCreate;
    }


    public static Dialog showCustomViewSelf(final Context context, final View v, final DialogInterface.OnCancelListener onCancelListener) {

        BaseApplication.mHandler.post(new Runnable() {
            @Override
            public void run() {
                dialogCreate = new Dialog(context, R.style.custom_dialog);
                dialogCreate.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
                dialogCreate.show();
                dialogCreate.setCancelable(true);
                if (onCancelListener != null) {
                    dialogCreate.setOnCancelListener(onCancelListener);
                }
            }
        });
        return dialog;
    }

    public static Dialog showTwoBtnDialog(Context context, String strmsg, DialogInterface.OnClickListener confirm, DialogInterface.OnClickListener cancle) {
        dismissDialog();
        dialog = new AlertDialog.Builder(context).setTitle("Notice").setMessage(strmsg).setPositiveButton(BaseApplication.context.getString(R.string.confirm), confirm)
                .setNegativeButton(BaseApplication.context.getString(R.string.cancel), cancle).create();

        dialog.show();
        return dialog;
    }


    public static Dialog showTwoBtnDialog(Context context, String title, String strmsg, DialogInterface.OnClickListener confirm, DialogInterface.OnClickListener cancle) {
        dismissDialog();
        dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(strmsg).setPositiveButton(BaseApplication.context.getString(R.string.confirm), confirm)
                .setNegativeButton(BaseApplication.context.getString(R.string.cancel), cancle).create();
        dialog.show();
        return dialog;
    }


    public static AlertDialog showTwoBtnDialog(Context context, String title, String strmsg, DialogInterface.OnClickListener confirm, final DialogInterface.OnClickListener cancle, String cancleString, String confirmString) {
        dismissDialog();
        dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(strmsg).setPositiveButton(confirmString, confirm)
                .setNegativeButton(cancleString, cancle).create();
        dialog.show();
        return dialog;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Dialog showTwoBtnDialog(Context context, String title, String strmsg, DialogInterface.OnClickListener confirm, DialogInterface.OnDismissListener cancle, String cancleString, String confirmString) {
        dismissDialog();
        dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(strmsg).setPositiveButton(confirmString, confirm)
                .setNegativeButton(cancleString, null).setOnDismissListener(cancle).create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showTwoBtnDialog(Context context, String title, View view, DialogInterface.OnClickListener confirm, DialogInterface.OnClickListener cancle) {
        dismissDialog();
        dialog = new AlertDialog.Builder(context).setTitle(title).setPositiveButton(BaseApplication.context.getString(R.string.confirm), confirm)
                .setNegativeButton(BaseApplication.context.getString(R.string.cancel), cancle).setView(view).create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showOneBtnDialog(Context context, String title, View view, DialogInterface.OnClickListener confirm) {
        dismissDialog();
        dialog = new AlertDialog.Builder(context).setTitle(title).setPositiveButton(BaseApplication.context.getString(R.string.confirm), confirm)
                .setView(view).create();
        dialog.show();
        return dialog;
    }


    public static AlertDialog showThreeBtnDialog(Context context, DialogInterface.OnClickListener confirm, DialogInterface.OnClickListener middle, DialogInterface.OnClickListener cancle, View view, String title, String middleInfo) {
        dismissDialog();
        dialog = new AlertDialog.Builder(context).setTitle(title).setPositiveButton(BaseApplication.context.getString(R.string.confirm), confirm)
                .setNegativeButton(BaseApplication.context.getString(R.string.cancel), cancle).setNeutralButton(middleInfo, middle).setView(view).create();
        dialog.show();
        return dialog;
    }


    public static void showToast(final String text) {
        LogManager.e(text);
        BaseApplication.mHandler.post(new Runnable() {

            @Override
            public void run() {
                final View view = LayoutInflater.from(BaseApplication.context).inflate(R.layout.common_toast_alert, null);
                TextView tv = (TextView) view.findViewById(R.id.toast_text);
                tv.setText(text);

                toast = new Toast(BaseApplication.context);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, ScreenUtil.dip2px(120));
                toast.setView(view);
                toast.show();

                Animation alpAnimation = new AlphaAnimation(0.1f, 1.0f);
                alpAnimation.setDuration(Toast.LENGTH_SHORT);
                alpAnimation.setFillAfter(true);
                view.startAnimation(alpAnimation);
            }
        });
    }

    public static Dialog showListDialog(Context context, String title, final List<String> commands, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        String[] valuse = commands.toArray(new String[commands.size()]);
        builder.setItems(valuse, onClickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog showListDialog(Context context, String title, BaseAdapter adapter, final IOnPositionGot listen) {

        ListView dialogview = (ListView) LayoutInflater.from(context).inflate(R.layout.list, null);// 得到加载view

        dialogview.setAdapter(adapter);
        final Dialog dialog = showCustomView(context, dialogview, title);
        dialogview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listen != null) {
                    listen.onPositonGet(position);
                }
                dismissDialog();
            }
        });
        return dialog;
    }


    public static Dialog showPickNumberDialog(Context context, final int minNumber, final int maxNumber, final IOnPositionGot onPositionGot, String title, String notice) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_picknumber, null);
        TextView noticeTV = (TextView) v.findViewById(R.id.notice);
        noticeTV.setText(notice);
        final EditText et = (EditText) v.findViewById(R.id.content);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Integer i = Integer.valueOf(s.toString());
                    if (i < minNumber) {
                        i = minNumber;
                        et.setText(i + "");
                    } else if (i > maxNumber) {
                        i = maxNumber;
                        et.setText(i + "");
                    }
                } catch (Exception exception) {
                    LogManager.printStackTrace(exception);
                    // atRoom.setText(String.valueOf("1"));
                }
            }
        });

        return showOneBtnDialog(context, title, v, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int number = Integer.valueOf(et.getText().toString());
                onPositionGot.onPositonGet(number);
            }
        });

    }

    public static interface IOnPositionGot {
        void onPositonGet(int position);
    }


}
