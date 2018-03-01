package util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.xml.crypto.Data;

/**
 * 把多个文件的属性和并
 * @author Administrator
 *
 */
public class combine {
	public static void main(String args[])
	{
		readfile read = new readfile();
		writeFile wFile = new writeFile();
		//本站气压  风向风速  降水  气温   日照   相对湿度  蒸发
		ArrayList<List<String>> dataqiya = read.readsecondData("D:\\气象数据\\气象数据离散化\\本站气压.txt");
		ArrayList<List<String>> datafengsu = read.readsecondData("D:\\气象数据\\气象数据离散化\\风向风速.txt");
		ArrayList<List<String>> datajiangshui = read.readsecondData("D:\\气象数据\\气象数据离散化\\降水.txt");
		ArrayList<List<String>> dataqiwen = read.readsecondData("D:\\气象数据\\气象数据离散化\\气温.txt");
		ArrayList<List<String>> datarizhao = read.readsecondData("D:\\气象数据\\气象数据离散化\\日照.txt");
		ArrayList<List<String>> datashidu = read.readsecondData("D:\\气象数据\\气象数据离散化\\相对湿度.txt");
		
		ArrayList<List<String>> data = new ArrayList<List<String>>();
		for (int i= 0; i < dataqiya.size() ;i++) {
			int flag = 0;
			List<String> list = new ArrayList<>();
			list.add(dataqiya.get(i).get(5)); 
			list.add(datafengsu.get(i).get(5)); 
			list.add(dataqiwen.get(i).get(5)); 
			list.add(dataqiwen.get(i).get(6)); 
			list.add(dataqiwen.get(i).get(7)); 
			list.add(datarizhao.get(i).get(5)); 
			list.add(datashidu.get(i).get(5)); 
			list.add(datajiangshui.get(i).get(5)); 
			for (String string : list) {
				if (string.equals("缺省")) {
					flag =1;
				}
			}
			if (flag==0) {
				data.add(list);
			}
		}
		
//		ArrayList<List<String>> train = new ArrayList<List<String>>();
//		for (int i= 0; i < 211000 ;i++) {
//			List<String> list = data.get(i);
//			train.add(list);
//		}
		ArrayList<List<String>> test = new ArrayList<List<String>>();
		for (int i= 211000; i < data.size() ;i++) {
			List<String> list = data.get(i);
		//	list.remove(7);
			test.add(list);
		}
//		int t1=wFile.write("D:\\气象数据\\合并后\\训练集.txt", train);
//		if (t1 ==1) {
//			System.out.println("成功");
//		}
		int t2=wFile.write("D:\\气象数据\\合并后\\测试集标准.txt", test);
		if (t2 ==1) {
			System.out.println("成功");
		}
	}
	
	
	
	
	
	
}
