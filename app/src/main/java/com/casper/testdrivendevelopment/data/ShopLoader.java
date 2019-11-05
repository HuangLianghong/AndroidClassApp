package com.casper.testdrivendevelopment.data;

import android.util.Log;

import com.casper.testdrivendevelopment.data.model.Shop;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Myself on 2019/11/5.
 */

public class ShopLoader {
    public  ArrayList<Shop> getShops(){return shops;}

    private ArrayList<Shop> shops = new ArrayList<>();
    public String download(String urlStr){
        HttpURLConnection connection = null;
        try {
            // 调用URL对象的openConnection方法获取HttpURLConnection的实例
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            // 设置请求方式，GET或POST
            connection.setRequestMethod("GET");
            // 设置连接超时、读取超时的时间，单位为毫秒（ms）
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            // 设置是否使用缓存  默认是true
            connection.setUseCaches(false);
            //设置请求头里面的属性
            //connection.setRequestProperty();
            // 开始连接
            Log.i("HttpURLConnection.GET","开始连接");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                Log.i("HttpURLConnection.GET", "请求成功");
                InputStream in = connection.getInputStream();
                // 使用BufferedReader对象读取返回的数据流
                // 按行读取，存储在StringBuider对象response中
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }else{
                Log.i("HttpURLConnection.GET", "请求失败");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (connection != null){
                // 结束后，关闭连接
                connection.disconnect();
            }
        }
        return "";
    }

    public void parseJson(String content)
    {
        shops.clear();
        try {
            //这里的text就是上边获取到的数据，一个String.
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonDatas = jsonObject.getJSONArray("shops");
            for (int i = 0; i < jsonDatas.length(); i++) {
                JSONObject shopJson = jsonDatas.getJSONObject(i);
                Shop shop = new Shop();
                shop.setLatitude(shopJson.getDouble("latitude"));
                shop.setName(shopJson.getString("name"));
                shop.setLongitude(shopJson.getDouble("longitude"));
                shop.setMemo(shopJson.getString("memo"));
                shops.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
