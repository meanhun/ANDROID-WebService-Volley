package com.share4happy.service;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.share4happy.model.Location;
import com.share4happy.model.ModelCommom;
import com.share4happy.model.OverviewInfo;
import com.share4happy.model.Today;
import com.share4happy.model.Total;
import com.share4happy.model.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class VolleyService {
    public static void getRequest(Context context, final VolleyResponseListener listener){
        // Khai báo object để hứng dữ liệu
        ModelCommom modelCommom = new ModelCommom();
        // Khai báo url của API
        String url = ServiceInfo.Base_URL+"data.json";

        //Nếu request thành công chúng ta có thể lấy ra đối tượng jsonobjet
        //trong phương thức onResponse
        //Ngược lại chúng ta có thông tin lỗi trong phương thức onErrooResponse

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                url, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {
                // Lần lượt lấy các jsonobject tương ứng
                if (response!=null){
                    JSONObject total = response.optJSONObject("total");
                    JSONObject today = response.optJSONObject("today");
                    JSONArray overview = response.optJSONArray("overview");
                    JSONArray locations = response.optJSONArray("locations");
                    //Lần lượt ép các jsonarray và jsonObject trở thành đối tượng tương ứng
                    Gson gson = new Gson();
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = parser.parse(String.valueOf(total));
                    Total objectTotal = gson.fromJson(mJson,Total.class);
                    mJson = parser.parse(String.valueOf(total));
                    Today objectToday = gson.fromJson(mJson,Today.class);

                    mJson = parser.parse(String.valueOf(overview));
                    OverviewInfo[] objectOverviewInfo = gson.fromJson(mJson,
                            OverviewInfo[].class);

                    ArrayList<OverviewInfo> overviewInfos = new ArrayList<>();
                    Arrays.stream(objectOverviewInfo).forEach(overviewInfos::add);

                    mJson = parser.parse(String.valueOf(locations));
                    Location[] objectLocation =  gson.fromJson(mJson,Location[].class);

                    ArrayList<Location> locationList = new ArrayList<>();
                    Arrays.stream(objectLocation).forEach(locationList::add);

                    //Đổ dữ liệu vào các Object tương ứng

                    modelCommom.setToday(objectToday);
                    modelCommom.setTotal(objectTotal);
                    modelCommom.setLocations(locationList);
                    modelCommom.setOverview(overviewInfos);
                    //Lắng nghe các response tiếp tục
                    listener.onResponse(modelCommom);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG",error.toString());
            }
        }
        );
        //Đợi các request tiếp theo
        requestQueue.add(jsonObjectRequest);
    }
}
