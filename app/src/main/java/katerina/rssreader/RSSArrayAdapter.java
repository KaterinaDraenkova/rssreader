package katerina.rssreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Katerina on 9.4.16.
 */
public class RSSArrayAdapter extends ArrayAdapter<NewsItem> {

    private final Context mContext;
    private final ArrayList<NewsItem> mValues;

    public RSSArrayAdapter(Context context, ArrayList<NewsItem> values) {
        super(context, R.layout.list_item, values);
        mContext = context;
        mValues = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        NewsItem newsItem = mValues.get(position);
        holder.mTitle.setText(newsItem.getTitle());
        holder.mDescription.setText(newsItem.getDescription());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.title) TextView mTitle;
        @Bind(R.id.description) TextView mDescription;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
