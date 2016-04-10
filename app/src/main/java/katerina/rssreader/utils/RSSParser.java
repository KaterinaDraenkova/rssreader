package katerina.rssreader.utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

import katerina.rssreader.model.NewsItem;

/**
 * Created by Katerina on 10.4.16.
 */
public class RSSParser {

    private String mCurrentTag;

    public ArrayList<NewsItem> parse(InputStream is) throws XmlPullParserException, ParseException, IOException {
        ArrayList<NewsItem> news = new ArrayList<>();

        XmlPullParserFactory factory = XmlPullParserFactory
                .newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(is, null);

        int eventType = xpp.getEventType();
        NewsItem newsItem = null;
        NewsItem.Builder newsBuilder = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                mCurrentTag = xpp.getName().toLowerCase();
                if (mCurrentTag.equals("item")) {
                    newsBuilder = NewsItem.getBuilder();
                }
            } else if (eventType == XmlPullParser.TEXT) {
                String content = xpp.getText();
                content = content.trim();
                if (newsBuilder != null) {
                    switch (mCurrentTag) {
                        case "title":
                            if (content.length() != 0) {
                                if (newsBuilder.getTitle() != null) {
                                    newsBuilder.setTitle(newsBuilder.getTitle() + content);
                                } else {
                                    newsBuilder.setTitle(content);
                                }
                            }
                            break;
                        case "guid":
                            if (content.length() != 0) {
                                if (newsBuilder.getGuid() != null) {
                                    newsBuilder.setGuid(newsBuilder.getGuid() + content);
                                } else {
                                    newsBuilder.setGuid(content);
                                }
                            }
                            break;
                        case "link":
                            if (content.length() != 0) {
                                if (newsBuilder.getLink() != null) {
                                    newsBuilder.setLink(newsBuilder.getLink() + content);
                                } else {
                                    newsBuilder.setLink(content);
                                }
                            }
                            break;
                        case "description":
                            if (content.length() != 0) {
                                if (newsBuilder.getDescription() != null) {
                                    newsBuilder.setDescription(newsBuilder.getDescription() + content);
                                } else {
                                    newsBuilder.setDescription(content);
                                }
                            }
                            break;
                        case "category":
                            if (content.length() != 0) {
                                if (newsBuilder.getCategory() != null) {
                                    newsBuilder.setCategory(newsBuilder.getCategory() + content);
                                } else {
                                    newsBuilder.setCategory(content);
                                }
                            }
                            break;
                        case "creator":
                            if (content.length() != 0) {
                                if (newsBuilder.getDcCreator() != null) {
                                    newsBuilder.setDcCreator(newsBuilder.getDcCreator() + content);
                                } else {
                                    newsBuilder.setDcCreator(content);
                                }
                            }
                            break;
                        case "pubdate":
                            if (content.length() != 0) {
                                if (newsBuilder.getPubDate() != null) {
                                    newsBuilder.setPubDate(newsBuilder.getPubDate() + content);
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
                mCurrentTag = xpp.getName().toLowerCase();
                if ((mCurrentTag.equals("item")) && (newsBuilder != null)) {
                    newsItem = newsBuilder.build();
                    news.add(newsItem);
                    newsBuilder = null;
                }
            }

            eventType = xpp.next();
        }

        return news;
    }
}
