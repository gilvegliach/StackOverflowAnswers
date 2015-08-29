package it.gilvegliach.learning.espressoviewpager;

import android.content.Context;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {
    static final String[] FRAG_TITLES = {"All", "Filtered"};
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(final int position) {
                    actionBar.setSelectedNavigationItem(position);
                }
            });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(final ActionBar.Tab tab, final FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(final ActionBar.Tab tab, final FragmentTransaction fragmentTransaction) { }

    @Override
    public void onTabReselected(final ActionBar.Tab tab, final FragmentTransaction fragmentTransaction) { }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(final FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return FRAG_TITLES[position];
        }
    }

    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener {
        public static final String ARG_POSITION = "arg_position";
        static final String[] ITEMS = {"first item", "second item", "third item"};

        public static PlaceholderFragment newInstance(final int position) {
            Bundle args = new Bundle(1);
            args.putInt(ARG_POSITION, position);

            PlaceholderFragment f = new PlaceholderFragment();
            f.setArguments(args);
            return f;
        }

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                final Bundle savedInstanceState) {
            Context context = getActivity();
            boolean isFiltered = getArguments().getInt(ARG_POSITION) != 0;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, ITEMS);
            ListView lv = new ListView(context);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(this);
            lv.setTag(isFiltered);
            return lv;
        }

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            String msg = position == 1 ? "OK, second item clicked" : "ERROR, other item clicked";
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
