package com.dailylocally.api.remote;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.dailylocally.utilities.AppConstants;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest extends Request {

    private final Gson gson = new Gson();
    private final Class myClass;
    private final Map headers;
    private final Map params;
    private final Response.Listener listener;

    public GsonRequest(int get, String url, Class myClass,
                       Response.Listener listener, Response.ErrorListener errorListener,String version) {
        super(Method.GET, url, errorListener);
        this.myClass = myClass;
        this.headers = addHeaders(version);
        this.params = null;
        this.listener = listener;

        setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public GsonRequest(int type, String url, Class myClass,
                       Object params,
                       Response.Listener listener, Response.ErrorListener errorListener,String version) {
        super(type, url, errorListener);
        this.myClass = myClass;
        this.headers = addHeaders(version);
        this.params = getStringMap(params);
        this.listener = listener;

        setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
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
       /* HashMap<String, String> headers = new HashMap<>();
      //  headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        //headers.put("Content-Type", "application/json");
        headers.put("accept-version",version);
        headers.put("apptype",AppConstants.APP_TYPE_ANDROID);
      //  headers.put("Authorization","Bearer");

        AppPreferencesHelper preferencesHelper=new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);
        headers.put("Authorization","Bearer "+preferencesHelper.getApiToken());

       // headers.put("token",preferencesHelper.getApiToken());

        return  headers;*/
        return AppConstants.setHeaders(version);
    }



    public HashMap<String, String> getStringMap(Object object) {
        try {
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            return gson.fromJson(gson.toJson(object), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
