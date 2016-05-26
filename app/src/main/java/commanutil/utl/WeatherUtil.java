package commanutil.utl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by zhanglin on 15-9-6.
 */
public class WeatherUtil {
    static final String url = "http://api.map.baidu.com/telematics/v3/weather?location=#location#&output=json&ak=cY2BqHyujRXzHDFgLgrg89iR";

    public static void getWeather(final String cityName, final StringCallback stringCallback) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String realUrl = url.replace("#location#", URLEncoder.encode(cityName, "UTF-8"));
                    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(
                            new URL(realUrl)
                                    .openConnection().getInputStream()));
                    StringBuffer localStringBuffer = new StringBuffer();
                    while (true) {
                        String str1 = localBufferedReader.readLine();
                        if (str1 == null) {
                            String str2 = localStringBuffer.toString();
                            LogManager.e("123", str2);
                            JSONObject jsonObject = new JSONObject(str2);
                            JSONArray weatherArray = jsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("weather_data");
                            JSONObject weatherObj = weatherArray.getJSONObject(0);
                            if (stringCallback == null)
                                break;
                            stringCallback.back(weatherObj.toString());
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
