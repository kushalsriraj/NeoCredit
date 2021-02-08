package rutherfordit.com.instasalary.extras;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;

public class VolleyRequest {

    public ResponseHandler handler;
    Context context;

    public void JsonObjRequest(Activity activity, final JSONObject jsonObject, String url, final int i) {
        this.handler = (ResponseHandler) activity;

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("***", "Register_Request " + response.toString());
                VolleyRequest.this.handler.responseHandler(response, i );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("*******", " Register_Request  " + error.toString());
                VolleyRequest.this.handler.responseHandler(null, i );
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        MySingleton.getinstance(activity).addrequest(jsonObjectRequest, url);
    }


    public void JsonObjRequestAuthorization(Context activity, JSONObject jsonObject, String url, final int i, final String accesstoken) {
        this.handler = (ResponseHandler) activity;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("***", "Register_Request " + response.toString());
                VolleyRequest.this.handler.responseHandler(response, i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("*******", " Register_Request  " + error.toString());
                VolleyRequest.this.handler.responseHandler(null, i);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-type" , "application/json");
                params.put("Accept" , "application/json");
                params.put("Authorization" , accesstoken);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError
            {
            }
        });
        MySingleton.getinstance(activity).addrequest(jsonObjectRequest, url);
    }


}
