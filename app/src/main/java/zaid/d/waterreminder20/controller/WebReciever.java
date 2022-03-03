package zaid.d.waterreminder20.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Objects;

public class WebReciever {
    public static void setReminding(Context context, Boolean isReminding) {
        String remind = isReminding.toString();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://10.0.0.193:7238/WaterReminder/"+remind;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                response -> {}, error -> Log.d("Error", Objects.requireNonNull(error.getMessage())));
        queue.add(stringRequest);
    }
}
