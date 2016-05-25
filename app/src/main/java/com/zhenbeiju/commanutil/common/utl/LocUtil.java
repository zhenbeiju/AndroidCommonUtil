package com.zhenbeiju.commanutil.common.utl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LocUtil {
	public void getLocationInfo(final StringCallback paramStringCallback) {
		new Thread(new Runnable() {
			public void run() {
				try {
					BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(
							new URL(
									"http://api.map.baidu.com/location/ip?ak=cY2BqHyujRXzHDFgLgrg89iR")
									.openConnection().getInputStream()));
					StringBuffer localStringBuffer = new StringBuffer();
					while (true) {
						String str1 = localBufferedReader.readLine();
						if (str1 == null) {
							String str2 = new JSONObject(new String(localStringBuffer.toString().getBytes(), "utf-8"))
									.getJSONObject("content").getJSONObject("address_detail").getString("city");
							String citycode = new JSONObject(new String(localStringBuffer.toString().getBytes(),
									"utf-8")).getJSONObject("content").getJSONObject("address_detail")
									.getString("city_code");
							Log.e("123", "city " + str2);
							if (paramStringCallback == null)
								break;
							paramStringCallback.back(str2);
							break;
						}
						localStringBuffer.append(str1);
					}
				} catch (MalformedURLException localMalformedURLException) {
					localMalformedURLException.printStackTrace();
				} catch (IOException localIOException) {
					localIOException.printStackTrace();
				} catch (JSONException localJSONException) {
					localJSONException.printStackTrace();
				}
			}
		}).start();
	}
}
