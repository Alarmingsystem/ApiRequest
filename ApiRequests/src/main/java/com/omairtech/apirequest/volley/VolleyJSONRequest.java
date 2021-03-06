package com.omairtech.apirequest.volley;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;


public class VolleyJSONRequest extends JsonObjectRequest {

    private Map<String, String> headers = new Hashtable<>();
    private Map<String, String> params = new Hashtable<>();

    private final int initialTimeoutMs;
    private final String tag;

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     *                      parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public VolleyJSONRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener
            , int initialTimeoutMs, String tag, Map<String, String> headers) {
        super(method, url, new JSONObject(), listener, errorListener);

        this.initialTimeoutMs = initialTimeoutMs;
        this.tag = tag;
        this.headers = headers;

        Log.d("SERVER_URL", method + " " + url);
        setDefaults();
    }

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     *                      parameters will be posted along with request.
     * @param params        A {@link JSONObject} to post with the request. Null indicates no
     *                      parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public VolleyJSONRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener
            , int initialTimeoutMs, String tag, Map<String, String> headers, Map<String, String> params) {
        super(method, url, new JSONObject(params != null ? params : new Hashtable<>()), listener, errorListener);

        this.initialTimeoutMs = initialTimeoutMs;
        this.tag = tag;
        this.headers = headers;
        this.params = params;

        Log.d("SERVER_URL", method + " " + url);
        setDefaults();
    }

    private void setDefaults() {
        setRetryPolicy(new DefaultRetryPolicy(
                initialTimeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        setShouldCache(false);
        setTag(tag);
    }

    @Override
    public Map<String, String> getHeaders() /*throws AuthFailureError*/ {
        Log.d("HEADER_PARAMS", headers.toString());
        return headers;
    }

    @Override
    protected Map<String, String> getParams()/* throws AuthFailureError*/ {
        Log.d("BODY_PARAMS", params.toString());
        return params;
    }

//    @Override
//    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
//        retryPolicy = new DefaultRetryPolicy(
//                Helper.initialTimeoutMs1,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        return super.setRetryPolicy(retryPolicy);
//    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        Log.e("TAG", "volleyError " + volleyError.getMessage());
        return super.parseNetworkError(volleyError);
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        // Map<String, String> responseHeaders = response.headers;
//        try {
//            String expire = response.headers.get("expires");
//            String date = response.headers.get("Date");
//            Log.e("TAG", "parseNetworkResponse expire " + expire);
//            Log.e("TAG", "parseNetworkResponse date " + date);
//            if (expire != null && expire.length() < 3) {
//                response.headers.put("expires", date);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return super.parseNetworkResponse(response);
    }
}
