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

package com.github.piasy.app.features.search;

import android.os.Bundle;
import com.github.piasy.app.BootstrapApp;
import com.github.piasy.app.features.search.di.SearchComponent;
import com.github.piasy.app.features.search.di.SearchModule;
import com.github.piasy.base.android.BaseActivity;
import com.github.piasy.base.di.HasComponent;

public class SearchActivity extends BaseActivity implements HasComponent<SearchComponent> {

    private SearchComponent mSearchComponent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new SearchFragment())
                .commit();
    }

    @Override
    protected void initializeInjector() {
        mSearchComponent =
                BootstrapApp.get().appComponent().plus(getActivityModule(), new SearchModule());
    }

    @Override
    public SearchComponent getComponent() {
        return mSearchComponent;
    }
}
