package com.jack.app.rxtest.util;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.TextView;

import com.jack.app.rxtest.R;

/**
 * Created by Jack on 2017-01-03.
 */

public class TextViewLogger {
    private static final int TEXT_COLOR_DEBUG = 0xff00bb37;

    private TextView mTextView;

    public TextViewLogger(TextView textView) {
        this(textView, false);
    }

    public TextViewLogger(TextView textView, boolean applyDefaultTheme) {
        mTextView = textView;

        if (applyDefaultTheme) {
            int padding = 8;
            textView.setPadding(padding, 0, padding, 0);
            textView.setBackgroundColor(ContextCompat.getColor(textView.getContext(), R.color.gray));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10.f);
        }
    }

    public void d(@NonNull String tag, @NonNull String message) {
        print(tag, message, TEXT_COLOR_DEBUG);
    }

    private void print(@NonNull String tag, @NonNull String message, int color) {
        final SpannableString spannable = new SpannableString("[" + tag + "] " + message + "\n");
        spannable.setSpan(new ForegroundColorSpan(color), 0, spannable.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.append(spannable);
        final int scrollAmount = mTextView.getLayout().getLineTop(mTextView.getLineCount()) - mTextView.getHeight();
        if (scrollAmount > 0) {
            mTextView.scrollTo(0, scrollAmount);
        } else {
            mTextView.scrollTo(0, 0);
        }
    }
}
