import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DataWorker extends AsyncTask<String, Void, String> {
    Context context;
    public DataWorker(Context c)
    {
        context = c;
    }
    @Override
    protected String doInBackground(String... strings) {
        String imageSite = "http://192.168.1.212/postImage.php";
        String type = strings[0];
        if(type.equals("data"))
        {
            try {
                String id = strings[1];
                URL url = new URL(imageSite);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream sout = connection.getOutputStream();
                BufferedWriter bwrite = new BufferedWriter(new OutputStreamWriter(sout, "UTF-8"));
                String postData = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bwrite.write(postData);
                Log.v("GP2", "This is what it wrote " + postData);
                bwrite.flush();
                bwrite.close();
                sout.close();
                InputStream in = connection.getInputStream();
                BufferedReader bread = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String json;
                //reading until we don't find null
                while ((json = bread.readLine()) != null) {

                    //appending it to string builder
                    sb.append(json + "\n");
                }

                //finally returning the read string
                return sb.toString().trim();


            }
            catch (MalformedURLException e) {
                Log.v("GP2", "Malformed Error on getting img");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("GP2", "IO Error getting img");
                e.printStackTrace();
            }


        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
