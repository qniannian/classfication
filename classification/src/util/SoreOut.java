package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * 作用：把每个类似D:\\气象数据\\201501-201712\\蒸发下的 所有文件调用readDataFile函数，在写入指定目录
 * 结果：D:\\气象数据\\201501-201712\\蒸发目录下所有文件的内容全部经过处理，并写入指定目录下的一个文件中。
 * @author Administrator
 *
 */

public class SoreOut {
	public static void main(String args[])
	{
		readfile read = new readfile();
		writeFile wFile = new writeFile();
		String path = "D:\\气象数据\\201501-201712\\蒸发";
		//本站气压  风向风速  降水  气温   日照   相对湿度  蒸发
		
		String wpath = "D:\\气象数据\\整理一次的数据\\蒸发.txt";
		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null) {
			System.out.println("该目录下为空");
		}
		for (File f : files) {
			String filepath = f.getPath();
			ArrayList<List<String>> data = read.readDataFile(filepath);
			int i=wFile.write(wpath, data);
			if (i==1) {
				System.out.println("成功");
			}
		}
		
	}

}