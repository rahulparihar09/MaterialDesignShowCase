package git.rahulparihar.materialdesignshowcase;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import git.rahulparihar.materialdesignshowcase.adapter.ApplicationAdapter;
import git.rahulparihar.materialdesignshowcase.itemanimator.CustomItemAnimator;
import git.rahulparihar.materialdesignshowcase.model.AppInfo;


public class RecyclerSampleActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    Fragment fragment;
    FragmentManager fragmentManager;
    private ApplicationAdapter mAdapter;
    private ImageButton mFabButton;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<AppInfo> applicationList = new ArrayList<AppInfo>();
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mFabButton = (ImageButton) findViewById(R.id.fab_button);

        // Handle ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new CustomItemAnimator());
        //mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>(), R.layout.row_application, this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_set_1_accent));
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new InitializeApplicationsTask().execute();
            }
        });

        new InitializeApplicationsTask().execute();

        //show progress
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private class InitializeApplicationsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mAdapter.clearApplications();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            applicationList.clear();

            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
            for (ResolveInfo ri : ril) {
                applicationList.add(new AppInfo(RecyclerSampleActivity.this, ri));
            }
            Collections.sort(applicationList);

            for (AppInfo appInfo : applicationList) {
                //load icons before shown. so the list is smoother
                appInfo.getIcon();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //handle visibility
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

            //set data for list
            mAdapter.addApplications(applicationList);
            mSwipeRefreshLayout.setRefreshing(false);

            super.onPostExecute(result);
        }
    }

    public void animateActivity(AppInfo appInfo, View appIcon) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("appInfo", appInfo.getComponentName());
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create((View) mFabButton, "fab"), Pair.create(appIcon, "appIcon"));
        startActivity(i, transitionActivityOptions.toBundle());
    }

}

