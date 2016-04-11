package katerina.rssreader.model;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Katerina on 9.4.16.
 */
public class NewsItem implements Serializable {

    private String mTitle;
    private String mGuid;
    private String mLink;
    private String mDescription;
    private String mCategory;
    private String mDcCreator;
    private String mPubDate;  // "EEE, DD MMM yyyy HH:mm:ss Z"

    private String mImageLink;

    private NewsItem() {
    }

    public String getTitle() {
        return mTitle;
    }

    public String getGuid() {
        return mGuid;
    }

    public String getLink() {
        return mLink;
    }

    public Spanned getDescription() {
        return Html.fromHtml(mDescription);
    }

    public String getCategory() {
        return mCategory;
    }

    public String getDcCreator() {
        return mDcCreator;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public String getImageLink() {
        return mImageLink;
    }

    public static Builder getBuilder() {
        return new NewsItem().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setGuid(String guid) {
            mGuid = guid;
            return this;
        }

        public Builder setLink(String link) {
            mLink = link;
            return this;
        }

        public Builder setDescription(String description) {
            String img = "";
            Pattern p = Pattern.compile("<img src=.*/>");
            Matcher m = p.matcher(description);
            while (m.find()) { // Find each match in turn; String can't do this.
                img = m.group(0); // Access a submatch group; String can't do this.
            }

            description = description.replaceAll(img, "");
            mDescription = description;

            String imageLink = "";
            p = Pattern.compile("src=\"[^\"]*\"");
            m = p.matcher(img);
            while (m.find()) { // Find each match in turn; String can't do this.
                imageLink = m.group(0); // Access a submatch group; String can't do this.
            }
            imageLink = imageLink.replaceAll("\"", "");
            imageLink = imageLink.replaceAll("src=", "");
            return setImageLink(imageLink);
        }

        public Builder setCategory(String category) {
            mCategory = category;
            return this;
        }

        public Builder setDcCreator(String dcCreator) {
            mDcCreator = dcCreator;
            return this;
        }

        public Builder setPubDate(String pubDate) {
            mPubDate = pubDate;
            return this;
        }

        public Builder setImageLink(String imageLink) {
            mImageLink = imageLink;
            return this;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getGuid() {
            return mGuid;
        }

        public String getLink() {
            return mLink;
        }

        public String getDescription() {
            return mDescription;
        }

        public String getCategory() {
            return mCategory;
        }

        public String getDcCreator() {
            return mDcCreator;
        }

        public String getPubDate() {
            return mPubDate;
        }

        public String getImageLink() {
            return mImageLink;
        }

        public NewsItem build() {
            return NewsItem.this;
        }

    }
}
