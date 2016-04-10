package katerina.rssreader.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import katerina.rssreader.R;

public class MainActivity extends FragmentActivity {

    public final static String EXTRA_NEWSITEM = "katerina.rssreader.NEWSITEMS";
    public static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
