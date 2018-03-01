package discretization;

import java.util.ArrayList;
import java.util.List;

import util.readfile;
import util.writeFile;

public class rain {
		
	//离散化降雨量，序号是5，6，7的数据需处理
	private ArrayList<List<String>> disRain(ArrayList<List<String>> data)
	{
		for (List<String> list : data) {
			for (int i = 0; i < list.size();i++) {
				if (i==5 ||i == 6 || i == 7) {
					//把string转换成double,便于比较
					double d = Double.parseDouble(list.get(i));
					if (d <= 0.1) {
						list.set(i, "无雨");
					}
					else if (d > 0.1 && d <= 10) {
						list.set(i, "小雨");
					}
					else if (d > 10 && d <= 25) {
						list.set(i, "中雨");
					}
					else if (d > 25 && d <= 50) {
						list.set(i, "大雨");
					}
					else if (d > 50 && d <= 100) {
						list.set(i, "暴雨");
					}
					else if (d > 100 && d <= 250) {
						list.set(i, "大暴雨");
					}
					else if (d == 32700 ) {
						list.set(i, "微量");
					}
					else if (d == 32766 ) {
						list.set(i, "缺省");
					}
					else {
						list.set(i, "特大暴雨");
					}
				}
			}
		}
		return data;
	}
	
	public static void  main(String args[])
	{
		readfile read = new readfile();
		rain rain = new rain();
		writeFile wFile = new writeFile();
		ArrayList<List<String>> data = read.readsecondData("D:\\气象数据\\整理一次的数据\\降水");
		data = rain.disRain(data);
		wFile.write("D:\\气象数据\\气象数据离散化\\降水1.txt", data);
	}

}