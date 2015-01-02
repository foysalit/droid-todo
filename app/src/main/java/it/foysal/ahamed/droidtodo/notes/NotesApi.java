package it.foysal.ahamed.droidtodo.notes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by foysal on 01/01/15.
 */
public class NotesApi extends AsyncTask<String, Void, Void> {
    private HttpClient httpClient = new DefaultHttpClient();
    public static String API_URL = "http://note-foysal.rhcloud.com/notes";
    private Context activityContext;
    private static String LOG_TAG = "NOTE";

    public NotesApi(Context context) {
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
        String response = callApi(httpget);

        if (response != null) {
            Log.v(LOG_TAG, response);
            ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);
            Log.v(LOG_TAG, new Gson().toJson(apiResponse));
            Log.v(LOG_TAG, ""+ apiResponse.data.size());
            /*Log.v(LOG_TAG, apiResponse.data);
            Type noteType = new TypeToken<List<Note>>(){}.getType();
            Log.v(LOG_TAG, noteType.toString());
            List<Note> notes = new Gson().fromJson(apiResponse.data, noteType);
            Log.v(LOG_TAG, " "+ notes.size());*/

            Log.v(LOG_TAG, apiResponse.data.get(0).title);
        }

        return null;
    }
}
