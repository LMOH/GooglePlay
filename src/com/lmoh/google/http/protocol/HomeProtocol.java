package com.lmoh.google.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lmoh.googleplay.domain.AppInfo;

/**首页网络数据解析
 * @author PC-LMOH
 *
 */
public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>> {

	private ArrayList<String> picList;

	@Override
	public String getKey() {
		return "home";
	}

	@Override
	public String getParam() {
		return "";
	}

	@Override
	public ArrayList<AppInfo> parseData(String result) {
		ArrayList<AppInfo> appInfoList = new ArrayList<AppInfo>();
		picList = new ArrayList<String>();
	//	System.out.println("getresult"+result);
	//	System.out.println("picList:"+picList);
		//解析json数据
		try {
			JSONObject jo = new JSONObject(result);
			//解析应用信息
			JSONArray ja = jo.getJSONArray("list");
			//解析轮播图图片地址
			JSONArray ja1 = jo.getJSONArray("picture");
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
				appInfoList.add(info);
			}
			
			for (int i = 0; i < ja1.length(); i++) {
				//System.out.println(ja1.getString(i));
				picList.add(ja1.getString(i));
			}
			return appInfoList;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @return 轮播图片集合
	 */
	public ArrayList<String> getPicList(){
		return picList;
	}
}
