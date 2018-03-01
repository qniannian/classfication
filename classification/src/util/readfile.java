package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 从天气数据的txt文件中读取内容
 * @author niannian
 * 
 * 函数readDataFile作用： 用于读取天气数据的原始文件（即刚从官网下载的数据），并去除多余的空格及整理日期的格式（整理之后的格式为2018-1-1）
 * 函数readsecondData作用：读取经过处理好的天气数据，格式为：  1 2 3 4 
 * 												   2 3 4 5
 *
 */
public class readfile {
	
	
	public ArrayList<List<String>> readDataFile(String filePath) {
		File file = new File(filePath);
		ArrayList<List<String>> dataArray = new ArrayList<List<String>>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			String[] tempArray;
			while ((str = in.readLine()) != null) {
				tempArray = str.split(" ");
				List<String> list = deleteSpaceBar(tempArray); //去除空格
				list = editTime(list);
				dataArray.add(list);
			} 
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		return dataArray;
	}

	//去除空格
	private List<String> deleteSpaceBar(String arry[])
	{
		List<String> list = new ArrayList<>();
		for (String string : arry)
		{
			if (!string.equals("")) {
				list.add(string);
			}
		}
		return list;
	}
	//整理时间
	private List<String> editTime(List<String> list)
	{
		String date = list.get(4)+"-"+list.get(5)+"-"+list.get(6);
		list.remove(6);
		list.remove(5);
		list.set(4, date);
		return list;
		
	}
	
	public ArrayList<List<String>> readsecondData(String filePath) {
		File file = new File(filePath);
		ArrayList<List<String>> dataArray = new ArrayList<List<String>>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			String[] tempArray;
			while ((str = in.readLine()) != null) {
				tempArray = str.split(" ");
				List<String> list = ToList(tempArray); //转为list
				dataArray.add(list);
			} 
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		return dataArray;
	}
	
	public List<String> readtestData(String filePath) {
		File file = new File(filePath);
		List<String> dataArray = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {
				dataArray.add(str);
			} 
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		return dataArray;
	}
	
	//转为list
		private List<String> ToList(String arry[])
		{
			List<String> list = new ArrayList<>();
			for (String string : arry)
			{
					list.add(string);
			}
			return list;
		}

}
	