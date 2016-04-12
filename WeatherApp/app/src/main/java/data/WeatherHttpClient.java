package data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import util.Utils;

/**
 * Created by Martin on 3/11/15.
 */
public class WeatherHttpClient {


    private Bitmap bitmap;

    public String getWeatherData(String place) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;


        try {
            connection = (HttpURLConnection) (new URL(Utils.BASE_URL + place)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            //Read the response
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while ((line = bufferedReader.readLine()) != null)
                stringBuffer.append(line + "\r\n");

            inputStream.close();
            connection.disconnect();
            return stringBuffer.toString();

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {

            }

            connection.disconnect();
        }

        return null;
    }

    public static Bitmap getImage(String code) {

        HttpParams httpParams = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpGet get = new HttpGet(Utils.ICON_URL + code + ".png");
        DefaultHttpClient client;

        client = new DefaultHttpClient(httpParams);
        HttpResponse response = null;
        try {
            response = client.execute(get);
            String network_response = response.getStatusLine().toString();
            HttpEntity responseEntity = response.getEntity();
            BufferedHttpEntity httpEntity = new BufferedHttpEntity(responseEntity);
            InputStream imageStream = httpEntity.getContent();

            return BitmapFactory.decodeStream(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("getBmpFromUrl error: ", e.getMessage().toString());
            return null;
        }



    }


}
