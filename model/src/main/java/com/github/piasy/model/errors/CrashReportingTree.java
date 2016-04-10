/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.model.errors;

import android.text.TextUtils;
import com.crashlytics.android.Crashlytics;
import timber.log.Timber;

/**
 * Created by guyacong on 2015/4/11.
 */
public class CrashReportingTree extends Timber.Tree {

    @Override
    public void e(final String message, final Object... args) {
        if (!TextUtils.isEmpty(message)) {
            Crashlytics.log(message);
        }
    }

    @Override
    public void e(final Throwable t, final String message, final Object... args) {
        if (t != null) {
            Crashlytics.logException(t);
        }
    }

    @Override
    protected void log(final int priority, final String tag, final String message,
            final Throwable t) {
        // do nothing
    }
}
