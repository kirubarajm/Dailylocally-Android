package com.dailylocally.api.remote;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dailylocally.utilities.DailylocallyApp;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.NetworkUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsontoJsonRequest extends Request {

    private final Gson gson = new Gson();
    private final Class myClass;
    private final Map headers;
    private final JSONObject params;
    private final Response.Listener listener;

    public GsontoJsonRequest(int get, String url, Class myClass,
                             Response.Listener listener, Response.ErrorListener errorListener,String version) {
        super(Method.GET, url, errorListener);
        this.myClass = myClass;
        this.headers = addHeaders(version);
        this.params = null;
        this.listener = listener;
        setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public GsontoJsonRequest(int type, String url, Class myClass,
                             JSONObject params,
                             Response.Listener listener, Response.ErrorListener errorListener,String version) {
        super(type, url, errorListener);
        this.myClass = myClass;
        this.headers = addHeaders(version);
        this.params = params;
        this.listener = listener;
        setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public boolean onNetWorkCheck(){
        return  NetworkUtils.isNetworkConnected(DailylocallyApp.getInstance());
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, myClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        if(null != listener){
            listener.onResponse(response);
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public Map<String, String> addHeaders(String version){
        /*HashMap<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        //  headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("accept-version",version);
        headers.put("apptype", AppConstants.APP_TYPE_ANDROID);
        //  headers.put("Authorization","Bearer");

        AppPreferencesHelper preferencesHelper=new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);
        headers.put("Authorization","Bearer "+preferencesHelper.getApiToken());

        // headers.put("token",preferencesHelper.getApiToken());
        return  headers;*/



        return AppConstants.setHeaders(version);

    }


}
