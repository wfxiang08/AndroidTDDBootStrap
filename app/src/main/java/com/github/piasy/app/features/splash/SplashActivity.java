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

package com.github.piasy.app.features.splash;

import android.content.Intent;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.piasy.app.BootstrapApp;
import com.github.piasy.app.BuildConfig;
import com.github.piasy.app.R;
import com.github.piasy.app.features.search.SearchActivity;
import com.github.piasy.app.features.splash.di.SplashComponent;
import com.github.piasy.base.android.BaseActivity;
import com.github.piasy.base.di.HasComponent;
import com.github.piasy.base.utils.RxUtil;
import com.github.piasy.model.errors.CrashReportingTree;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import jonathanfinerty.once.Once;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/19.
 *
 * Splash activity. Init app and handle other Intent action. I imitate the way in
 * <a href="http://frogermcs.github.io/dagger-graph-creation-performance/">frogermcs'  blog:
 * Dagger
 * 2 - graph creation performance</a> to avoid activity state loss.
 */
@SuppressWarnings({
        "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity",
        "PMD.ModifiedCyclomaticComplexity"
})
public class SplashActivity extends BaseActivity implements HasComponent<SplashComponent> {

    private static final String TAG = "SplashActivity";

    private SplashComponent mSplashComponent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialize();
    }

    @Override
    protected void initializeInjector() {
        mSplashComponent = BootstrapApp.get().appComponent().plus();
        mSplashComponent.inject(this);
    }

    private void initialize() {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                final BootstrapApp app = BootstrapApp.get();
                if (BuildConfig.REPORT_CRASH) {
                    Fabric.with(app, new Crashlytics());
                    //Crashlytics.setString();
                }
                if ("debug".equals(BuildConfig.BUILD_TYPE)) {
                    Timber.plant(new Timber.DebugTree());
                } else {
                    Timber.plant(new CrashReportingTree());
                }

                Iconify.with(new MaterialModule());
                Once.initialise(app);
                Fresco.initialize(app);

                // Developer tools
                if (BuildConfig.INSTALL_LEAK_CANARY) {
                    LeakCanary.install(app);
                }
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(final Boolean success) {
                startActivity(new Intent(SplashActivity.this, SearchActivity.class));
                finish();
            }
        }, RxUtil.OnErrorLogger);
    }

    @Override
    public SplashComponent getComponent() {
        return mSplashComponent;
    }
}
