package katerina.rssreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import katerina.rssreader.model.NewsItem;
import katerina.rssreader.R;
import katerina.rssreader.utils.InternalStorage;
import katerina.rssreader.utils.RSSArrayAdapter;
import katerina.rssreader.network.RSSLoader;

/**
 * Created by Katerina on 10.4.16.
 */
public class RSSFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>>,
        SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    private Loader<ArrayList<NewsItem>> mLoader;
    private ArrayList<NewsItem> mNews;
    private RSSArrayAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        try {
            // Retrieve the list from internal storage
            mNews = (ArrayList<NewsItem>) InternalStorage.readObject(getContext(), InternalStorage.KEY);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (mNews == null) {
            mNews = new ArrayList<>();
        }

        mAdapter = new RSSArrayAdapter(getContext(), mNews);
        setListAdapter(mAdapter);

        mLoader = getLoaderManager().initLoader(MainActivity.LOADER_ID, null, this);
        mLoader.forceLoad();
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
        if (mNews != null) {
            mAdapter = new RSSArrayAdapter(getContext(), mNews);
            setListAdapter(mAdapter);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {
        getLoaderManager().restartLoader(MainActivity.LOADER_ID, null, this);
    }

    @Override
    public void onRefresh() {
        mLoader.forceLoad();
    }
}
