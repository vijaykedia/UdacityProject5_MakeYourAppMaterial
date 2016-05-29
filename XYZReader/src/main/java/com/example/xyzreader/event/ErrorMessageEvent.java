package com.example.xyzreader.event;

import android.support.annotation.NonNull;

/**
 * Created by vijaykedia on 26/05/16.
 * This will be used to pass {@link String} error message
 */
public class ErrorMessageEvent {

    private final String message;

    public ErrorMessageEvent(@NonNull final String message) {
        this.message = message;
    }

    @NonNull
    public String getMessage() {
        return message;
    }
}
