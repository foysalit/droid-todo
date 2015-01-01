package it.foysal.ahamed.droidtodo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by foysal on 01/01/15.
 */
public class Notes extends AsyncTask<String, Void, Void> {
    private HttpClient httpClient = new DefaultHttpClient();
    public static String API_URL = "http://note-foysal.rhcloud.com/notes";
    private Context activityContext;

    Notes(Context context) {
        this.activityContext = context;
    }

    public Void getAll(){
        return null;
    }

    public String callApi(HttpGet getDataCall){
        try {
            HttpResponse response = httpClient.execute(getDataCall);
            if(response != null) {
                String line = "";
                InputStream inputstream = response.getEntity().getContent();
                line = convertStreamToString(inputstream);
                return line;
            } else {
                return null;
            }
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception exception: " + e.getMessage());
        }

        return null;
    }

    private String convertStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (Exception e) {
            System.out.println("General exception: " + e.getMessage());
        }
        return total.toString();
    }

    @Override
    protected Void doInBackground(String... params) {
        HttpGet httpget = new HttpGet(API_URL);
        callApi(httpget);
        return null;
    }
}
