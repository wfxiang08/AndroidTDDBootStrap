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

package com.github.piasy.model;

import android.content.Context;
import android.support.annotation.NonNull;
import com.github.piasy.base.model.provider.StorIOSQLiteProvider;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/19.
 *
 * Expose method used internally for DI in test code.
 */
public final class DaoModuleExposure {
    private DaoModuleExposure() {
        // no instance
    }

    public static StorIOSQLiteProvider.Config exposeStorIOSQLiteConfig(
            @NonNull final Context context) {
        final DaoModule daoModule = new DaoModule();
        return daoModule.provideStorIOSQLiteConfig(daoModule.provideSQSqLiteOpenHelper(context));
    }
}
