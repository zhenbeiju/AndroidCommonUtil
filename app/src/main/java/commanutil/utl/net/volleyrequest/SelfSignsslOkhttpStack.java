package commanutil.utl.net.volleyrequest;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by zhanglin on 16/8/4.
 * http://www.chinaz.com/web/2015/0731/428907.shtml
 * KeyStore ks = KeyStore.getInstance("JKS");
 * <p>
 * // get user password and file input stream
 * char[] password = ("mykspassword")).toCharArray();
 * ClassLoader cl = this.getClass().getClassLoader();
 * InputStream stream = cl.getResourceAsStream("myjks.jks");
 * ks.load(stream, password);
 * stream.close();
 * <p>
 * SSLContext sc = SSLContext.getInstance("TLS");
 * KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
 * TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
 * <p>
 * kmf.init(ks, password);
 * tmf.init(ks);
 * <p>
 * sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(),null);
 * <p>
 * return sc.getSocketFactory();
 */
public class SelfSignsslOkhttpStack extends HurlStack {
    private OkHttpClient okHttpClient;
    private Map<String, SSLSocketFactory> socketFactoryMap;


    /**
     * @param socketFactoryMap
     */
    public SelfSignsslOkhttpStack(Map<String, SSLSocketFactory> socketFactoryMap) {
        this(new OkHttpClient(), socketFactoryMap);

    }


    public SelfSignsslOkhttpStack(OkHttpClient client, Map<String, SSLSocketFactory> socketFactoryMap) {
        this.okHttpClient = client;
        this.socketFactoryMap = socketFactoryMap;

    }


    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        if ("https".equals(url.getProtocol()) && socketFactoryMap.containsKey(url.getHost())) {
            HttpsURLConnection connection = (HttpsURLConnection) new OkUrlFactory(okHttpClient).open(url);
            connection.setSSLSocketFactory(socketFactoryMap.get(url.getHost()));
            return connection;
        } else {
            return new OkUrlFactory(okHttpClient).open(url);
        }
    }
}
