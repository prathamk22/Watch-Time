package projects.pratham.movieinformation;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class AsyncTasks extends AsyncTask<Void,Void,Void> {
    private String urls,MovieLine="",MovieData="";
    public AsyncTasks(String url) {
        this.urls = url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            URL url = new URL (urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection ();
            InputStream inputStream = httpURLConnection.getInputStream ();
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(inputStream));
            while(MovieLine != null){
                Log.e("inside","this");
                MovieLine = bufferedReader.readLine();
                MovieData = MovieData + MovieLine;
            }
        }catch (IOException ignored){
            Log.e("excepiton", ignored.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            JSONObject jsonObject = new JSONObject (MovieData);
            String response = jsonObject.getString("Response");
            if(response.matches("True")){
                MovieInformation.info.setTitle(jsonObject.getString("Title"));
                MovieInformation.info.setYear(jsonObject.getString("Year"));
                MovieInformation.info.setRated(jsonObject.getString("Rated"));
                MovieInformation.info.setReleased(jsonObject.getString("Released"));
                MovieInformation.info.setDirector(jsonObject.getString("Director"));
                MovieInformation.info.setRuntime(jsonObject.getString("Runtime"));
                MovieInformation.info.setGenre(jsonObject.getString("Genre"));
                MovieInformation.info.setWriter(jsonObject.getString("Writer"));
                MovieInformation.info.setActors(jsonObject.getString("Actors"));
                MovieInformation.info.setPlot(jsonObject.getString("Plot"));
                MovieInformation.info.setLang(jsonObject.getString("Language"));
                MovieInformation.info.setUrl(jsonObject.getString("Poster"));
                MovieInformation.info.setRating(jsonObject.getString("imdbRating"));
                MovieInformation.info.setProduction(jsonObject.getString("Production"));
            }else{
                MovieInformation.info.setTitle("No Movie Found with that Name");
            }

        } catch (JSONException e) {
            Log.e("Exception",e.getMessage());
        }
    }

}
