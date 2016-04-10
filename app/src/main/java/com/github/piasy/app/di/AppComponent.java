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

package com.github.piasy.app.di;

import com.github.piasy.app.features.search.di.SearchComponent;
import com.github.piasy.app.features.search.di.SearchModule;
import com.github.piasy.app.features.splash.di.SplashComponent;
import com.github.piasy.base.di.ActivityModule;
import com.github.piasy.base.model.jsr310.JSR310Module;
import com.github.piasy.base.model.provider.ProviderModule;
import com.github.piasy.base.utils.UtilsModule;
import com.github.piasy.model.DaoModule;
import com.github.piasy.model.errors.ErrorProcessorModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * DI appComponent abstraction for Application scope.
 * Application scope is {@link Singleton} scope, sub appComponent like {@link
 * com.github.piasy.base.di.ActivityScope} could have wilder scope.
 *
 * We put it inside app module, because {@link ProviderConfigModule} must locate in app module,
 * and there is no other side effect with this manner, besides, we can use
 * {@link dagger.Subcomponent} to define sub component, which need less code.
 */
@Singleton
@Component(
        modules = {
                AppModule.class,

                ProviderModule.class, ProviderConfigModule.class, DaoModule.class,
                ErrorProcessorModule.class,

                JSR310Module.class, UtilsModule.class
        })
public interface AppComponent {
    SplashComponent plus();

    SearchComponent plus(ActivityModule activityModule, SearchModule searchModule);
}
