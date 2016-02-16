package com.example.manan.library.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
    private String body;

    public RemoteTask(String url, String method, String body, final RemoteCallback rb) {
        this.mUrl = url;
        this.method = method;
        this.body = body;
        remoteCallback = rb;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(mUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            // add request Body;
            if (body != null) {
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(body);
                out.flush();
                out.close();
            }

            con.connect();
            Log.d("TO_SERVER: >>", con.toString());
            int response = con.getResponseCode();
            if (response != 200) {
                Log.e("RemoteTask", "Error Code : " + response);
                remoteCallback.onFailure(new Throwable("ErrorCode: " + response));
            } else {
                InputStream inputStream = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String str = "";
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                inputStream.close();
                br.close();
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            remoteCallback.onFailure(e);
        }
        Log.d("RemoteTask", sb.toString());
        System.out.println(sb.toString());
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
