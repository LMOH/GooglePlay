package com.lmoh.google.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lmoh.googleplay.domain.AppInfo;

public class AppProtocol extends BaseProtocol<ArrayList<AppInfo>> {

	@Override
	public String getKey() {
		return "app";
	}

	@Override
	public String getParam() {
		return "";
	}

	@Override
	public ArrayList<AppInfo> parseData(String result) {
		
		try {
			JSONArray ja = new JSONArray(result);
			ArrayList<AppInfo> list = new ArrayList<AppInfo>();
			for (int i = 0; i < ja.length(); i++) {
				//获取每个json数组下的jsonobject
				JSONObject jo1 = ja.getJSONObject(i);
				AppInfo info = new AppInfo();
				
				//从每个jsonobject里获取内容到javabean
				info.des = jo1.getString("des");
				info.downloadUrl = jo1.getString("downloadUrl");
				info.iconUrl = jo1.getString("iconUrl");
				info.id = jo1.getString("id");
				info.name = jo1.getString("name");
				info.packageName = jo1.getString("packageName");
				info.size = jo1.getLong("size");
				info.stars = (float) jo1.getDouble("stars");

				//把每个javabean添加到list中
				list.add(info);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
