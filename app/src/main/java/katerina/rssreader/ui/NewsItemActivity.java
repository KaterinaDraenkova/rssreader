package katerina.rssreader.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import katerina.rssreader.model.NewsItem;
import katerina.rssreader.R;
import katerina.rssreader.network.ImageLoader;

/**
 * Created by Katerina on 9.4.16.
 */
public class NewsItemActivity extends FragmentActivity
        implements LoaderManager.LoaderCallbacks<Bitmap> {

    private static final int LOADER_ID = 2;

    @Bind(R.id.image) ImageView mImage;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.date) TextView mDate;
    @Bind(R.id.category) TextView mCategory;
    @Bind(R.id.description) TextView mDescription;
    private NewsItem mNewsItem;
    private Loader<Bitmap> mLoader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsitem);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mNewsItem = (NewsItem) intent.getSerializableExtra(MainActivity.EXTRA_NEWSITEM);

        mLoader = getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        mLoader.forceLoad();

        mTitle.setText(mNewsItem.getTitle());
        mDate.setText(mNewsItem.getPubDate());
        mCategory.setText(mNewsItem.getCategory());
        mDescription.setText(mNewsItem.getDescription());
    }

    @OnClick(R.id.title)
    public void showInBrowser(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNewsItem.getLink()));
        startActivity(browserIntent);
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        if ((id == LOADER_ID) && (mNewsItem.getImageLink() != null)) {
            mLoader = new ImageLoader(this, mNewsItem.getImageLink());
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        mImage.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
