package id.technomotion.util;

import android.support.v4.util.Pair;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.local.QiscusCacheManager;
import com.qiscus.sdk.data.model.QiscusComment;

import id.technomotion.model.Room;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created on : March 24, 2017
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class RealTimeChatroomHandler {
    private Listener listener;


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        listener = null;
    }

    public void updateChatrooms(QiscusComment qiscusComment) {
        triggerListener(qiscusComment);
    }


    private void triggerListener(QiscusComment comment) {
        if (listener != null) {
            listener.onReceiveComment(comment);
        }
    }

    public interface Listener {
        void onReceiveComment(QiscusComment comment);


    }
}
