package com.fatdog.WeatherMusic.reuse.network;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class JsonResponseHandler extends JsonHttpResponseHandler {
    private HttpRequester.NetworkResponseListener networkResponseListener;
    
    private static final String PARM_RESULT = "result";
    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_FAIL = "fail";
    private static final String PARM_ERROR_CODE = "error_code";
    public static final String PARM_DATA = "data";
    
    public static final int ERROR_CODE_NETWORK_UNAVAILABLE = -1;
    public static final int ERROR_CODE_PASSWORDS_ARE_NOT_IDENTICAL = 1;
    public static final int ERROR_CODE_MISSING_USERNAME = 2;
    public static final int ERROR_CODE_ALREADY_EXIST_USERNAME = 3;
    public static final int ERROR_CODE_ID_NOT_EXIST = 4;
    public static final int ERROR_CODE_PASSWORD_INCORRECT = 5;
    public static final int ERROR_CODE_HAVE_TO_LOGIN = 7;

    public JsonResponseHandler(HttpRequester.NetworkResponseListener aNetworkResponseListener) {
        this.networkResponseListener = aNetworkResponseListener;
    }

    // 여기가 콜백 메소드 부분이다.
    // Fired when a request returns successfully
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.i("JsonResponseHandler",""+response.toString());
        Log.e("attach", response.toString());
        try {
            if(response.getString(PARM_RESULT).equals(RESULT_SUCCESS)){
                this.networkResponseListener.onSuccess(response);
            }else if(response.getString(PARM_RESULT).equals(RESULT_FAIL))
                this.networkResponseListener.onFail(response, response.getInt(PARM_ERROR_CODE));
        } catch (JSONException e) { }
    }

    // Returns when request failed
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
    	if(statusCode == 0) {
            this.networkResponseListener.onFail(new JSONObject(), ERROR_CODE_NETWORK_UNAVAILABLE);
        }
        else { }
    }
}