package katerina.rssreader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Katerina on 10.4.16.
 */
public class RSSParser {

    private enum RSSXMLTag {
        IGNORETAG, TITLE, GUID, LINK, DESCRIPTION, CATEGORY, DCCREATOR, PUBDATE;
    }

    private RSSXMLTag mCurrentTag;

    public ArrayList<NewsItem> parse(InputStream is) throws XmlPullParserException, ParseException, IOException {
        ArrayList<NewsItem> news = new ArrayList<>();

        // parse xml after getting the data
        XmlPullParserFactory factory = XmlPullParserFactory
                .newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(is, null);

        int eventType = xpp.getEventType();
        NewsItem newsItem = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, DD MMM yyyy HH:mm:ss Z");
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {

            } else if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("item")) {
                    newsItem = new NewsItem();
                    mCurrentTag = RSSXMLTag.IGNORETAG;
                } else if (xpp.getName().equals("title")) {
                    mCurrentTag = RSSXMLTag.TITLE;
                } else if (xpp.getName().equals("link")) {
                    mCurrentTag = RSSXMLTag.LINK;
                } else if (xpp.getName().equals("pubDate")) {
                    mCurrentTag = RSSXMLTag.PUBDATE;
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if (xpp.getName().equals("item")) {
                    // format the data here, otherwise format data in
                    // Adapter
                    Date postDate = dateFormat.parse(newsItem.getPubDate());
                    newsItem.setPubDate(dateFormat.format(postDate));
                    news.add(newsItem);
                } else {
                    mCurrentTag = RSSXMLTag.IGNORETAG;
                }
            } else if (eventType == XmlPullParser.TEXT) {
                String content = xpp.getText();
                content = content.trim();
                Log.d("debug", content);
                if (newsItem != null) {
                    switch (mCurrentTag) {
                        case TITLE:
                            if (content.length() != 0) {
                                if (newsItem.getTitle() != null) {
                                    newsItem.setTitle(newsItem.getTitle() + content);
                                } else {
                                    newsItem.setTitle(content);
                                }
                            }
                            break;
                        case LINK:
                            if (content.length() != 0) {
//                                if (newsItem.postLink != null) {
//                                    newsItem.postLink += content;
//                                } else {
//                                    newsItem.postLink = content;
//                                }
                            }
                            break;
                        case PUBDATE:
                            if (content.length() != 0) {
//                                if (newsItem.postDate != null) {
//                                    newsItem.postDate += content;
//                                } else {
//                                    newsItem.postDate = content;
//                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            eventType = xpp.next();
        }
        Log.v("tst", String.valueOf((news.size())));

        return news;
    }
}
