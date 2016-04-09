package katerina.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Katerina on 9.4.16.
 */
public class NewsItemActivity extends Activity {

    @Bind(R.id.image) ImageView mImage;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.description) TextView mDescription;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsitem);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        NewsItem newsItem = (NewsItem) intent.getSerializableExtra(MainActivity.EXTRA_NEWSITEM);

        mTitle.setText(newsItem.getTitle());
        mDescription.setText(newsItem.getDescription());
    }

}
