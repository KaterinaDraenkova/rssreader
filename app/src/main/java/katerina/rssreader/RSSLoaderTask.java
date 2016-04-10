package katerina.rssreader;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Katerina on 10.4.16.
 */
class RSSLoaderTask extends AsyncTask<String, Integer, ArrayList<NewsItem>>{

    @Override
    protected ArrayList<NewsItem> doInBackground(String... params) {
        String urlStr = params[0];
        InputStream is = null;
        ArrayList<NewsItem> news = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d("debug", "The response is: " + response);
            is = connection.getInputStream();

            RSSParser rssParser = new RSSParser();
            news = rssParser.parse(is);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return news;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsItem> result) {
//        for (int i = 0; i < result.size(); i++) {
//            mListData.add(result.get(i));
//        }
//
//        mAdapter.notifyDataSetChanged();
    }
}
