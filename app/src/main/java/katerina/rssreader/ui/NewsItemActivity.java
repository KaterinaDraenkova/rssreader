package katerina.rssreader.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import katerina.rssreader.model.NewsItem;
import katerina.rssreader.R;

/**
 * Created by Katerina on 9.4.16.
 */
public class NewsItemActivity extends Activity {

    @Bind(R.id.image) ImageView mImage;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.description) TextView mDescription;
    private NewsItem mNewsItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsitem);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mNewsItem = (NewsItem) intent.getSerializableExtra(MainActivity.EXTRA_NEWSITEM);

        mTitle.setText(mNewsItem.getTitle());
        mDescription.setText(mNewsItem.getDescription());
    }

    @OnClick(R.id.title)
    public void showInBrowser(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNewsItem.getLink()));
        startActivity(browserIntent);
    }

}
