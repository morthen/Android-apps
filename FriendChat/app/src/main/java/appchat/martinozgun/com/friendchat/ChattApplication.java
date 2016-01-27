package appchat.martinozgun.com.friendchat;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import model.Message;

/**
 * Created by Martin Özgun.
 */
public class ChattApplication extends Application {

    public static final String APP_KEY_ID = "cbYiCSXRcmWsXrmv4i6DvNlhvi6DARVI0nD53erb";
    public static final String APP_CLIENT_ID = "XiQy2qF2lfAq9zype2gQVS9ZWj2auBaf6eQGyjgr";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(Message.class);

        Parse.initialize(this, APP_KEY_ID, APP_CLIENT_ID);

    }
}
