package com.example.myapplication.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.api.Api;
import com.example.myapplication.api.ApiConfig;
import com.example.myapplication.api.TtitCallback;
import com.example.myapplication.entity.CategoryEntity;
import com.example.myapplication.entity.VideoCategoryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        viewPager = mRootView.findViewById(R.id.fixedViewPager);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
    }

    protected void initData() {

    }
//    @Override
//    protected void initData() {
//        getVideoCategoryList();
//    }

//    private void getVideoCategoryList() {
//        HashMap<String, Object> params = new HashMap<>();
//        Api.config(ApiConfig.VIDEO_CATEGORY_LIST, params).getRequest(getActivity(), new TtitCallback() {
//            @Override
//            public void onSuccess(final String res) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        VideoCategoryResponse response = new Gson().fromJson(res, VideoCategoryResponse.class);
//                        if (response != null && response.getCode() == 0) {
//                            List<CategoryEntity> list = response.getPage().getList();
//                            if (list != null && list.size() > 0) {
//                                mTitles = new String[list.size()];
//                                for (int i = 0; i < list.size(); i++) {
//                                    mTitles[i] = list.get(i).getCategoryName();
//                                    mFragments.add(VideoFragment.newInstance(list.get(i).getCategoryId()));
//                                }
//                                viewPager.setOffscreenPageLimit(mFragments.size());
//                                viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
//                                slidingTabLayout.setViewPager(viewPager);
//                            }
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//            }
//        });
//    }
}