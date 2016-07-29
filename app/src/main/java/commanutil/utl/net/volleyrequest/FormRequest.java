package commanutil.utl.net.volleyrequest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import commanutil.utl.LogManager;

/**
 * Created by zhanglin on 16/7/8.
 */
public class FormRequest<T> extends Request<T> {
    private String boundary;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    boolean isSetLast = false;
    boolean isSetFirst = false;

    private Response.Listener<T> mListener;

    public FormRequest(String url, Response.ErrorListener listener, String boundary) {
        super(url, listener);
        this.boundary = boundary;
    }

    public FormRequest(String url, Response.Listener listener, Response.ErrorListener errorlistener, String boundary) {
        super(url, errorlistener);
        this.boundary = boundary;
        this.mListener = listener;
    }

    public FormRequest(int method, String url, Response.ErrorListener listener, String boundary) {
        super(method, url, listener);
        this.boundary = boundary;
    }

    public FormRequest(int method, String url, Response.Listener listener, Response.ErrorListener errorlistener, String boundary) {
        super(method, url, errorlistener);
        this.boundary = boundary;
        this.mListener = listener;
    }


    public void writeFirstBoundaryIfNeeds() {
        if (!isSetFirst) {
            try {
                out.write(("--" + boundary + "\r\n").getBytes());
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        isSetFirst = true;
    }

    public void writeLastBoundaryIfNeeds() {
        if (isSetLast) {
            return;
        }

        try {
            out.write(("\r\n--" + boundary + "--\r\n").getBytes());
        } catch (final IOException e) {
            e.printStackTrace();
        }

        isSetLast = true;
    }


    public void addPart(final String key, final String value) {
        writeFirstBoundaryIfNeeds();
        try {
            out.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
            out.write(value.getBytes());
            out.write(("\r\n--" + boundary + "\r\n").getBytes());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }


    public void addPart(final String key, final String fileName, final InputStream fin, final boolean isLast) {
        addPart(key, fileName, fin, "application/octet-stream", isLast);
    }

    public void addPart(final String key, final String fileName, final InputStream fin, String type, final boolean isLast) {
        writeFirstBoundaryIfNeeds();
        try {
            type = "Content-Type: " + type + "\r\n";
            out.write(("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"\r\n").getBytes());
            out.write(type.getBytes());
            out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());

            final byte[] tmp = new byte[4096];
            int l = 0;
            while ((l = fin.read(tmp)) != -1) {
                out.write(tmp, 0, l);
            }
            if (!isLast)
                out.write(("\r\n--" + boundary + "\r\n").getBytes());
            else {
                writeLastBoundaryIfNeeds();
            }
            out.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPart(final String key, final File value, final boolean isLast) {
        try {
            addPart(key, value.getName(), new FileInputStream(value), isLast);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public long getContentLength() {
        writeLastBoundaryIfNeeds();
        return out.toByteArray().length;
    }


    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        byte[] result = out.toByteArray();
//        LogManager.e(new String(result));
        return result;
    }

    protected static final String PROTOCOL_CHARSET = "utf-8";

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            LogManager.e(jsonString.toString());
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> heads = new HashMap<>();
        heads.put("content-type", "multipart/form-data; boundary=" + boundary);
        return heads;
    }


    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }


}
