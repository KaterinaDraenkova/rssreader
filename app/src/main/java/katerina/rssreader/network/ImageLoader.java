package katerina.rssreader.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import java.io.InputStream;

/**
 * Created by Katerina on 11.4.16.
 */
public class ImageLoader extends AsyncTaskLoader<Bitmap> {

    String mUrl;

    public ImageLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public Bitmap loadInBackground() {
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(mUrl).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon;
    }
}
