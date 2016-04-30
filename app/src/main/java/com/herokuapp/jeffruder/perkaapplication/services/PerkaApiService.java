package com.herokuapp.jeffruder.perkaapplication.services;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jeffrey on 4/28/2016.
 */
public class PerkaApiService {
    private Context mContext;
    private String mEncodedPdf;
    private OkHttpClient mClient;
    private static final String API_ENDPOINT = "https://api.perka.com/1/communication/job/apply";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public PerkaApiService(Context context, String encodedPdf) {
        mContext = context;
        mEncodedPdf = encodedPdf;
        mClient = new OkHttpClient();
    }

    public String buildJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name", "Jeffrey");
            jsonObject.put("last_name", "Ruder");
            jsonObject.put("email_address", "ruderjt@gmail.com");
            jsonObject.put("position_id", "ANDROID");
            jsonObject.put("explanation", "I created a very simple Android application that encodes a pdf to base64" +
                    " and submits the post request using OkHttp.");
            jsonObject.put("projects", new String[]{"http://jeffruder.herokuapp.com",
                    "https://github.com/JeffreyRuder",
                    "https://play.google.com/store/apps/details?id=com.epicodus.shake_it_up",
                    "https://play.google.com/store/apps/details?id=com.jtrmb.movietime",
                    "http://michael-scott-paper.herokuapp.com/",
                    "http://salem-sunshine.herokuapp.com/"
            });
            jsonObject.put("resume", mEncodedPdf);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String post(String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(API_ENDPOINT)
                .post(body)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
