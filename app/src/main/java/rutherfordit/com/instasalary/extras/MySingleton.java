package rutherfordit.com.instasalary.extras;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private final Context context;
    private RequestQueue requestQueue;
    private static MySingleton minstance;

    public MySingleton(Context context)
    {
        this.context = context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getinstance(Context context)
    {
        if (minstance==null)
        {
            minstance= new MySingleton(context);
        }
        return minstance;
    }

    public<T> void addrequest(Request<T> request, String tag)
    {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

}