package edu.csusb.wemo.presenter;

/**
 * Created by Josiah on 2/24/2017.
 */

public interface Presenter<V> {
    void setView(V view);

    void detachView();

    void onDestroy();
    void onPause();
}
