package katerina.rssreader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends ListActivity {

    public final static String EXTRA_NEWSITEM = "katerina.rssreader.NEWSITEMS";
    private final static String URL = "http://www.dailytechinfo.org/engine/rss.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getListView().setDividerHeight(5);

        ArrayList<NewsItem> items = new ArrayList<>();
        items = getItemsFake(items);

        loadRSS();

        RSSArrayAdapter adapter = new RSSArrayAdapter(this, items);
        setListAdapter(adapter);
    }

    private void loadRSS() {
        RSSLoaderTask task = new RSSLoaderTask();
        task.execute(URL);
    }

    private ArrayList<NewsItem> getItemsFake(ArrayList<NewsItem> items) {
        NewsItem item = NewsItem.getBuilder()
                .setTitle("Jack")
                .setGuid("www.www.com")
                .setLink("www.www.com")
                .setDescription("Ryyyyyyyyyyyyyyyyyyyyyyyyyyyzhiy")
                .setCategory("qqq")
                .build();
        items.add(item);
        item = NewsItem.getBuilder()
                .setTitle("John")
                .setGuid("www.qwerty.ru")
                .setLink("www.qwerty.ru")
                .setDescription("Booooooooooooooooooooob")
                .setCategory("ttt")
                .build();
        items.add(item);
        item = NewsItem.getBuilder()
                .setTitle("Bob")
                .setGuid("www.olololo.by")
                .setLink("www.olololo.by")
                .setDescription("Siiiiiiiiiimbak")
                .setCategory("ggg")
                .build();
        items.add(item);
        return items;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, NewsItemActivity.class);
        intent.putExtra(EXTRA_NEWSITEM, (NewsItem)l.getItemAtPosition(position));
        startActivity(intent);
    }
}
