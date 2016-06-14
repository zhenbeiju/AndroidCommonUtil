#Android Common util#
   gradle use:
>       compile 'com.zhenbeiju:app:1.1.4'
## MyApplication ##
  you must extend MyApplication,and set name in Manifest.

  MyApplication  init something in onCreate,

### use ###
  `MyApplication.mBaseContext.getPref*(key)`

  `MyApplication.mBaseContext.setPref*(key,value)`

  getter && setter Preferences Value Anywhere.

  `MyApplication.mBaseContext.getGlobal*(key)`

  `MyApplication.mBaseContext.setGlobal*(key,value)`

  getter and setter temp Value in app life,


  `MyApplication.mQueue.add(volleyrequest)`
  `MyApplication.mHandler.post(Runable)`


## LogManager ##

   Easy manager Log print, And print in a pretty style
### init ###
>      LogManager.initConfig(String tag, int logLevel, boolean logToFile)

    tag: log tag
    logLevel: like Log.Error ,just print log bigger then you set
    logToFile: save log to a file,file path is '/sdcard/log.txt'

   you should init LogManager in your Application's onCreate

### use ###
    `LogManager.e("log you want print");`
    `LogManager.w("Log you want print");`

### result ###
     [className|methodName]'logcontent'


## DialogInfo ##
   Easy way to show dialog with custom view, with one button, two button
     listview in a dialog ...
### use
   `DialogInfo.showLoadingDialog(Context context, String msg)`

   show a loading dialog with a custom notice

   `DialogInfo.showProgressDialog(final Context context, final String msg, final DialogInterface.OnCancelListener onCancelListener) `

   show a loading dialog with a custom notice & a on OnCancelListener

   `DialogInfo.showCustomView(Context Context, View v, String title)`

   show a dialog with custom view

## IOnRequsetDone
   convert a Volley request call back to Rxjava's observer
### useage ###
   `IOnRequsetDone<T> onrequestDone = new IOnRequsetDone();`

   `VolleyQuene.add(new JsonobjectRequest(url,onrequestDone.listener,onrequestDone.errorlisten))`

   `Observer<T> ob = onrequestDone.getObservable();`





