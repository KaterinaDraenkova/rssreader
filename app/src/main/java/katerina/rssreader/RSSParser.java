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
        NewsItem.Builder newsBuilder = null;

//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "EEE, DD MMM yyyy HH:mm:ss Z");
//        // format the data here, otherwise format data in
//        // Adapter
//        Date postDate = dateFormat.parse(newsItem.getPubDate());
//        newsItem.setPubDate(dateFormat.format(postDate));

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {

            } else if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("item")) {
                    newsBuilder = NewsItem.getBuilder();
                    mCurrentTag = RSSXMLTag.IGNORETAG;
                } else if (newsBuilder != null) {
                    if (xpp.getName().equals("title")) {
                        mCurrentTag = RSSXMLTag.TITLE;
                    } else if (xpp.getName().equals("guid")) {
                        mCurrentTag = RSSXMLTag.GUID;
                    } else if (xpp.getName().equals("link")) {
                        mCurrentTag = RSSXMLTag.LINK;
                    } else if (xpp.getName().equals("description")) {
                        mCurrentTag = RSSXMLTag.DESCRIPTION;
                    } else if (xpp.getName().equals("category")) {
                        mCurrentTag = RSSXMLTag.CATEGORY;
                    } else if (xpp.getName().equals("dc:creater")) {
                        mCurrentTag = RSSXMLTag.DCCREATOR;
                    } else if (xpp.getName().equals("pubDate")) {
                        mCurrentTag = RSSXMLTag.PUBDATE;
                    }
                } else { mCurrentTag = RSSXMLTag.IGNORETAG; }
            } else if (eventType == XmlPullParser.TEXT) {
                String content = xpp.getText();
                content = content.trim();
                Log.d("debug", content);
                if (newsBuilder != null) {
                    switch (mCurrentTag) {
                        case TITLE:
                            if (content.length() != 0) {
                                if (newsBuilder.getTitle() != null) {
                                    newsBuilder.setTitle(newsItem.getTitle() + content);
                                } else {
                                    newsBuilder.setTitle(content);
                                }
                            }
                            break;
                        case GUID:
                            if (content.length() != 0) {
                                if (newsBuilder.getGuid() != null) {
                                    newsBuilder.setGuid(newsItem.getGuid() + content);
                                } else {
                                    newsBuilder.setGuid(content);
                                }
                            }
                            break;
                        case LINK:
                            if (content.length() != 0) {
                                if (newsBuilder.getLink() != null) {
                                    newsBuilder.setLink(newsItem.getLink() + content);
                                } else {
                                    newsBuilder.setLink(content);
                                }
                            }
                            break;
                        case DESCRIPTION:
                            if (content.length() != 0) {
                                if (newsBuilder.getDescription() != null) {
                                    newsBuilder.setDescription(newsItem.getDescription() + content);
                                } else {
                                    newsBuilder.setDescription(content);
                                }
                            }
                            break;
                        case CATEGORY:
                            if (content.length() != 0) {
                                if (newsBuilder.getCategory() != null) {
                                    newsBuilder.setCategory(newsItem.getCategory() + content);
                                } else {
                                    newsBuilder.setCategory(content);
                                }
                            }
                            break;
                        case DCCREATOR:
                            if (content.length() != 0) {
                                if (newsBuilder.getDcCreator() != null) {
                                    newsBuilder.setDcCreator(newsItem.getDcCreator() + content);
                                } else {
                                    newsBuilder.setDcCreator(content);
                                }
                            }
                            break;
                        case PUBDATE:
                            if (content.length() != 0) {
                                if (newsBuilder.getPubDate() != null) {
                                    newsBuilder.setPubDate(newsItem.getPubDate() + content);
                                } else {
                                    newsBuilder.setPubDate(content);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if ((xpp.getName().equals("item")) && (newsBuilder != null)) {
                    newsItem = newsBuilder.build();
                    news.add(newsItem);
                    newsBuilder = null;
                } else {
                    mCurrentTag = RSSXMLTag.IGNORETAG;
                }
            }

            eventType = xpp.next();
        }
        Log.v("tst", String.valueOf((news.size())));

        return news;
    }
}
