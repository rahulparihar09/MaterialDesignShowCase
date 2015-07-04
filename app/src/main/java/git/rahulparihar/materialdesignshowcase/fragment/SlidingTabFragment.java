package git.rahulparihar.materialdesignshowcase.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import git.rahulparihar.materialdesignshowcase.R;
import git.rahulparihar.materialdesignshowcase.adapter.SamplePagerAdapter;
import git.rahulparihar.materialdesignshowcase.widget.SlidingTabLayout;

public class SlidingTabFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dialer_sliding, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter(getActivity()));

        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }
}
