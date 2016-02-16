package com.example.manan.library;

import android.util.Log;

import com.example.manan.library.network.RemoteTask;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ExampleUnitTest {
    @Test
    public void testRemoteTask() {
        final String[] res = {""};
        String bookID = "1";
        String checkoutUrl = "http://prolific-interview.herokuapp.com/56aff03f3ecbf90009be7bec/books/1/";
        String body = "{\"lastCheckedOutBy\" : \"Iron Man\"}";
        RemoteTask rt = new RemoteTask(checkoutUrl, "PUT", body, new RemoteTask.RemoteCallback() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                Book bb = gson.fromJson(s, Book.class);
                System.out.println(bb.toString());
                res[0] = bb.toString();
                if (res[0].isEmpty())
                    System.out.println("NOOOO");
                System.out.println(res[0]);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("CheckoutActivity", t.getMessage());
            }
        });
        rt.execute();
        assertNotNull(res[0]);
    }


}