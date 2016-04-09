package katerina.rssreader;

import java.io.Serializable;

/**
 * Created by Katerina on 9.4.16.
 */
public class NewsItem implements Serializable {

    private String mTitle;
    private String mGuid;
    private String mLink;
    private String mDescription;
    private String mCategory;

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

    public String getDescription() {
        return mDescription;
    }

    public String getCategory() {
        return mCategory;
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
            mDescription = description;
            return this;
        }

        public Builder setCategory(String category) {
            mCategory = category;
            return this;
        }

        public NewsItem build() {
            return NewsItem.this;
        }

    }
}
