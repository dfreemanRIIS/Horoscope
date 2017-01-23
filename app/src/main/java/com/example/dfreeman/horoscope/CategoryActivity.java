package com.example.dfreeman.horoscope;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CategoryActivity extends AppCompatActivity {

    public static final String EXTRA_HOROSCOPENO = "horoscopeNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        //Get current horoscope object
        int horoNo = (Integer) getIntent().getExtras().get(EXTRA_HOROSCOPENO);
        Horoscope horoscope = Horoscope.horoscopes[horoNo];

        //Set name
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(horoscope.getName());

        //OLD set description
        TextView description = (TextView) findViewById(R.id.desc);
        description.setText(horoscope.getDescription());

        //Set sign
        TextView sign = (TextView) findViewById(R.id.sign);
        sign.setText(horoscope.getSign());

        //Set month
        TextView month = (TextView) findViewById(R.id.month);
        month.setText(horoscope.getMonth());

        //Do the magic stuff
        checkNetworkConnection();
        new HTTPAsyncTask(horoscope.getName().toLowerCase()).execute();
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        TextView netCon = (TextView)findViewById(R.id.networkConnection);
        boolean isConnected = false;
        if(networkInfo != null && (isConnected = networkInfo.isConnected())) {
            netCon.setText("Network Connection Established");
            netCon.setBackgroundColor(0xFF7CCC26);
        } else {
            netCon.setText("No Network Connection Established");
            netCon.setBackgroundColor(0xFFFF0000);
        }

        return isConnected;
    }

    private class HTTPAsyncTask extends AsyncTask<Void, Void, String> {
        private String name;
        private String urlString;

        public HTTPAsyncTask(String name) {
            this.name = name;
        }

        @Override
        protected void onPreExecute() {
            urlString = "http://a.knrz.co/horoscope-api/current/" + name;
        }

        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                URL url = new URL(urlString);

                // Create the request and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView theeHoroscope = (TextView) findViewById(R.id.horoscope);
            theeHoroscope.setText(donsParser(s));
            Log.i("json", s);
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public String donsParser(String input) {
        String output = "";
        try {
            JSONObject obj = new JSONObject(input);
            output = obj.getString("prediction");
            return output;
        } catch(JSONException e) {
            return "bork";
        }
    }
}
