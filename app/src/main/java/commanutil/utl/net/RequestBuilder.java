package commanutil.utl.net;

import com.squareup.okhttp.Request;

/**
 * Created by zhanglin on 8/15/16.
 */
public class RequestBuilder<T> {
    private Request.Builder builder;


    public RequestBuilder(Request.Builder builder) {
        this.builder = builder;
    }


    public T parseResut(byte[] values) {
        return null;
    }


    public Request getReuest() {
        return builder.build();
    }


}
