package com.example.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.example.myapplication.R;
//import com.example.myapplication.activity.WebActivity;
//import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.api.Api;
import com.example.myapplication.api.ApiConfig;
import com.example.myapplication.api.TtitCallback;
import com.example.myapplication.entity.NewsEntity;
import com.example.myapplication.entity.NewsListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NewsFragment extends BaseFragment implements LocationSource,AMapLocationListener {

//    private NewsAdapter newsAdapter;
    private List<NewsEntity> datas = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;

    private MapView mapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption clientOption;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initview(savedInstanceState,view);
        return view;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData()
    {

    }
    private void initview( Bundle savedInstanceState,View view){
        mapView= (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap==null)
        {
            aMap=mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
    }


    /**
     * 激活定位
     */

    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener=listener;
        if(locationClient==null){
            locationClient=new AMapLocationClient(getActivity());
            clientOption=new AMapLocationClientOption();
            locationClient.setLocationListener( this);
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度定位
            clientOption.setOnceLocationLatest(true);//设置单次精确定位
            locationClient.setLocationOption(clientOption);
            locationClient.startLocation();
        }

    }
    /**
     * 停止定位
     */

    public void deactivate() {
        mListener=null;
        if(locationClient!=null){
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient=null;
    }

    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null&&aMapLocation != null) {
            if (aMapLocation != null
                    &&aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }


    /**
     * 必须重写以下方法
     */
    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(locationClient!=null){
            locationClient.onDestroy();
        }
    }
}