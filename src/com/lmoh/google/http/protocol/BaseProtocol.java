package com.lmoh.google.http.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.lmoh.googleplay.http.HttpHelper;
import com.lmoh.googleplay.http.HttpHelper.HttpResult;
import com.lmoh.googleplay.utils.IOUtils;
import com.lmoh.googleplay.utils.UIUtils;

/**从服务器获取数据并返回，其中解析数据具体由子类实现
 * @author PC-LMOH
 *
 * @param <T> 
 */
public abstract class BaseProtocol<T> {

	public T getData(int index) {

		String result = getCache(index);
		if (result == null) {
			// 若缓存为空从服务器获取数据
			result = getDataFromServer(index);
		}
		//解析数据
		if (result != null) {
			T data = parseData(result);
			return data;
		}
		return null;
	}

	/**
	 * 从服务器获取数据(记得加权限)
	 * 
	 * @param index
	 *            ： 获取数据的起始位置(从此获取20条数据，用于分页)
	 * @return 
	 */
	private String getDataFromServer(int index) {
		String url = HttpHelper.URL + getKey() + "?index=" + index + getParam();
		//System.out.println("url是：  " + url);
		HttpHelper helper = new HttpHelper();
		HttpResult httpResult = helper.get(url);
		if (httpResult != null) {
			String result = httpResult.getString();
			//System.out.println("获取结果：  " + result);
			if (result != null) {
				setCache(index, result);
			}
			return result;
		}
		return null;
	}

	// 获取页面链接关键字，子类实现
	public abstract String getKey();

	// 获取页面链接参数，子类实现
	public abstract String getParam();

	/** 写缓存，读缓存 **/

	/**
	 * 写缓存
	 * 
	 * @param index
	 * @param json
	 */
	public void setCache(int index, String json) {
		// 获取本应用缓存文件夹
		File cacheDir = UIUtils.getContext().getCacheDir();
		// 生成文件
		File cacheFile = new File(cacheDir,getKey() + "?index=" + index + getParam());
		// 写入json数据
		FileWriter writer = null;
		try {
			writer = new FileWriter(cacheFile);
			// 设置缓存有效期,写在文件第一行，换行写json
			long deadTime = System.currentTimeMillis() + 30 * 60 * 1000; // 半个小时
			writer.write(deadTime + "\n");
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(writer);
		}
	}

	/**读缓存
	 * @param index
	 * @return  
	 */
	public String getCache(int index) {
		// 获取本应用缓存文件夹
		File cacheDir = UIUtils.getContext().getCacheDir();
		// 生成文件File(文件夹地址，文件名)
		File cacheFile = new File(cacheDir,getKey() + "?index=" + index + getParam());
		//判断缓存是否存在
		if (cacheFile.exists()) {
			//判断缓存是否有效
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(cacheFile));
				String readLine = reader.readLine();
				long deadTime = Long.parseLong(readLine);
				if (System.currentTimeMillis() < deadTime) {
					//还有效，继续读
					String line;
					StringBuffer sb = new StringBuffer();
					while((line = reader.readLine()) != null){
						sb.append(line);
					}
					return sb.toString();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				IOUtils.close(reader);
			}
		}
		return null;
	}
	
	public abstract  T parseData(String result);
}
