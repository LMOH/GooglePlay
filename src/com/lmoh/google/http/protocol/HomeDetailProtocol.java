package com.lmoh.google.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.domain.AppInfo.SafeInfo;

/**首页应用详情页的网络数据，解析
 * @author PC-LMOH
 *
 */
public class HomeDetailProtocol extends BaseProtocol<AppInfo> {

	public String packageName;
	
	public HomeDetailProtocol(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String getKey() {
		return "detail";
	}

	@Override
	public String getParam() {
		return "&packageName="+packageName;
	}

	@Override
	public AppInfo parseData(String result) {
		
		try {
			JSONObject jo = new JSONObject(result);
			AppInfo info = new AppInfo();
			
			info.des = jo.getString("des");
			info.downloadUrl = jo.getString("downloadUrl");
			info.iconUrl = jo.getString("iconUrl");
			info.id = jo.getString("id");
			info.name = jo.getString("name");
			info.packageName = jo.getString("packageName");
			info.size = jo.getLong("size");
			info.stars = (float) jo.getDouble("stars");
			info.author = jo.getString("author");
			info.date = jo.getString("date");
			info.downloadNum = jo.getString("downloadNum");
			info.version = jo.getString("version");
			//解析安全数据
			JSONArray ja1 = jo.getJSONArray("safe");
			ArrayList<SafeInfo> safeInfos = new ArrayList<AppInfo.SafeInfo>();
			for (int i = 0; i < ja1.length(); i++) {
				JSONObject jo1 = ja1.getJSONObject(i);
				SafeInfo safeInfo = new SafeInfo();
				safeInfo.safeDes = jo1.getString("safeDes");
				safeInfo.safeDesUrl = jo1.getString("safeDesUrl");
				safeInfo.safeUrl = jo1.getString("safeUrl");
				safeInfos.add(safeInfo);
			}
			//添加安全数据
			info.safe = safeInfos;
			
			// 解析截图信息
			JSONArray ja2 = jo.getJSONArray("screen");
			ArrayList<String> screenInfos = new ArrayList<String>();
			for (int i = 0; i < ja1.length(); i++) {
				String pic = ja2.getString(i);
				screenInfos.add(pic);
			}
			//添加截图数据
			info.screen = screenInfos;
		
			return info;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
