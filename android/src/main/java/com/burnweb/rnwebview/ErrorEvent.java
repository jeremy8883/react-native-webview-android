package com.burnweb.rnwebview;

import android.os.Build;
import android.webkit.WebResourceError;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ErrorEvent extends Event<ErrorEvent> {

    public static final String EVENT_NAME = "error";

    private final WebResourceError error;

    public ErrorEvent(int viewId, WebResourceError error) {
        super(viewId);

        this.error = error;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        WritableMap eventData = Arguments.createMap();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            eventData.putString("message", error.getDescription().toString());
            eventData.putString("code", "" + error.getErrorCode());
        } else {
            eventData.putString("message", "Website failed to load");
            eventData.putString("code", "");
        }

        // TODO The event gets fired back, but for some reason it doesn't include the error data
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), eventData);
    }

}