package com.casper.testdrivendevelopment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) view.findViewById(R.id.MapView);

        BaiduMap baiduMap=mMapView.getMap();


        //设定中心点坐标
        LatLng cenpt =  new LatLng(22.255925,113.541112);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                //放大地图到18倍
                .zoom(18)
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);



        LatLng latLng = baiduMap.getMapStatus().target;
        //准备 marker 的图片
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name);
        //准备 marker option 添加 marker 使用
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(latLng);
        //获取添加的 marker 这样便于后续的操作
        Marker marker = (Marker) baiduMap.addOverlay(markerOptions);
        //添加文字
        OverlayOptions textOption = new TextOptions().bgColor(0x00000000)
                .fontSize(50).fontColor(0xFFFF0000).text("暨南大学珠海校区").rotate(0).position(cenpt);
        baiduMap.addOverlay(textOption);


        //响应事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker arg0) {
            Toast.makeText(getContext(), "你点击了暨南大学！！", Toast.LENGTH_SHORT).show();
            return false;
        }
    });



        return view;
    }
    private MapView mMapView = null;
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

}
