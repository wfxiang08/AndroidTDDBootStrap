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

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import com.github.piasy.app.MockBootstrapApp;
import com.github.piasy.app.R;
import com.github.piasy.base.test.BaseEspressoTest;
import com.github.piasy.base.test.MockProvider;
import com.github.piasy.model.users.GithubUserSearchResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.widget.IconTextView;
import java.io.IOException;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.github.piasy.app.espresso.contrib.ToolbarMatchers.onSupportToolbar;
import static com.github.piasy.app.espresso.contrib.ToolbarMatchers.withSupportToolbarTitle;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Piasy{github.com/Piasy} on 3/8/16.
 */
@RunWith(AndroidJUnit4.class)
@SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
public class SearchActivityTest extends BaseEspressoTest {
    @Rule
    public ActivityTestRule<SearchActivity> mActivityTestRule =
            new ActivityTestRule<>(SearchActivity.class, true, false);

    private GithubUserSearchResult mSingleResult;

    @Override
    public void setUp() throws IOException {
        super.setUp();
        final Gson gson = MockBootstrapApp.get().mGson;
        mSingleResult = gson.fromJson(MockProvider.provideSimplifiedGithubUserSearchResultStr(),
                new TypeToken<GithubUserSearchResult>() {}.getType());
    }

    @Override
    protected Dispatcher createDispatcher() {
        return new Dispatcher() {
            @Override
            public MockResponse dispatch(final RecordedRequest request)
                    throws InterruptedException {
                final String path = request.getPath();
                if (path.startsWith("/search/users?")) {
                    return new MockResponse().setBody(
                            MockProvider.provideSimplifiedGithubUserSearchResultStr());
                } else {
                    return new MockResponse();
                }
            }
        };
    }

    @Test
    public void testSearch() throws InterruptedException {
        final SearchActivity activity = mActivityTestRule.launchActivity(new Intent());
        onSupportToolbar().check(
                matches(allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        withSupportToolbarTitle(is(activity.getString(R.string.search))))));

        final Toolbar toolbar = ButterKnife.findById(activity, R.id.mToolBar);
        final MenuItem searchItem = toolbar.getMenu().findItem(R.id.mActionSearch);
        assertTrue(searchItem.getActionView() instanceof SearchView);
        assertFalse(searchItem.isActionViewExpanded());
        assertEquals(activity.getString(R.string.search), searchItem.getTitle());

        onView(withId(R.id.mActionSearch)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("piasy"),
                pressKey(KeyEvent.KEYCODE_ENTER));
        closeSoftKeyboard();
        sleep(1500);

        // then, boilerplate code to get, cast view, then get property, then assert it...
        final RecyclerView recyclerView = ButterKnife.findById(activity, R.id.mRvSearchResult);
        assertEquals(1, recyclerView.getChildCount());
        final CardView cardView = (CardView) recyclerView.getChildAt(0);
        final LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
        final IconTextView tvUsername = (IconTextView) linearLayout.getChildAt(1);
        // after setText into IconTextView, the content will be parsed as icon, the original text
        // won't exist anymore.
        assertTrue(tvUsername.getText().toString().endsWith(mSingleResult.items().get(0).login()));
    }
}
