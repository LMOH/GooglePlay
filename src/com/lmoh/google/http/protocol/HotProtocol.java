package com.lmoh.google.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class HotProtocol extends BaseProtocol<ArrayList<String>> {

	@Override
	public String getKey() {
		return "hot";
	}

	@Override
	public String getParam() {
		return "";
	}

	@Override
	public ArrayList<String> parseData(String result) {
		
		try {
			JSONArray jsonArray = new JSONArray(result);
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < jsonArray.length(); i++) {
				String string = jsonArray.getString(i);
				list.add(string);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
