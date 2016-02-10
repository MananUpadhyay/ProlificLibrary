package com.example.manan.library.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Replacement for the Retrofit PUT and DELETE calls.
 * Due to some problems in them.
 */
public class RemoteTask extends AsyncTask<Void, Void, String> {
    private String mUrl;
    private String method;
    private RemoteCallback remoteCallback;

    public RemoteTask(String url, String method, final RemoteCallback rb) {
        this.mUrl = url;
        this.method = method;
        remoteCallback = rb;
    }

    @Override
    protected String doInBackground(Void... voids) {
        InputStream inputStream = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(mUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.connect();
            int response = con.getResponseCode();
            if (response != 200) {
                Log.e("RemoteTask", "Error Code : " + response);
                remoteCallback.onFailure(new Throwable("ErrorCode: " + response));
            } else {
                inputStream = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String str = "";
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                inputStream.close();
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            remoteCallback.onFailure(e);
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        remoteCallback.onSuccess(s);
    }

    public interface RemoteCallback {
        void onSuccess(String s);

        void onFailure(Throwable t);
    }
}
