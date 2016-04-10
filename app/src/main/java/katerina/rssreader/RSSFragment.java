package katerina.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Katerina on 10.4.16.
 */
public class RSSFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>> {

    private Loader<ArrayList<NewsItem>> mLoader;
    private ArrayList<NewsItem> mNews;
    private RSSArrayAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setDividerHeight(5);

        mNews = new ArrayList<>();
        mNews = getItemsFake(mNews);

        setListAdapter(mAdapter);

        mLoader = getLoaderManager().initLoader(MainActivity.LOADER_ID, null, this);
        mLoader.forceLoad();

//        mAdapter = new RSSArrayAdapter(getContext(), mNews);
//        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), NewsItemActivity.class);
        intent.putExtra(MainActivity.EXTRA_NEWSITEM, (NewsItem)l.getItemAtPosition(position));
        startActivity(intent);
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, Bundle args) {
        if (id == MainActivity.LOADER_ID) {
            mLoader = new RSSLoader(getActivity());
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, ArrayList<NewsItem> data) {
        mNews = data;
        mAdapter = new RSSArrayAdapter(getContext(), mNews);
        setListAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {
        getLoaderManager().restartLoader(MainActivity.LOADER_ID, null, this);
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

}
