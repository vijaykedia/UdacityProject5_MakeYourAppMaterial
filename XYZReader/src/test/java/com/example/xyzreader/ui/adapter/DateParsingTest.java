package com.example.xyzreader.ui.adapter;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vijaykedia on 20/05/16.
 *
 */
public class DateParsingTest {

    @Test
    public void parseDateTest() {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

        System.out.println(dateFormat.toString());

        Date date;
        try {
            date = dateFormat.parse("2013-06-20T00:00:00.000Z");
        } catch (final ParseException e) {
            System.err.println(e.toString());
            date = new Date();
        }

//        String text = DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString() + " by " + "Carl Sagan";

        System.out.print(date.toString());
    }
}
