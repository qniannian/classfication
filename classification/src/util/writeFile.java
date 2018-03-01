package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class writeFile {
	
	/**
	 * 写文件，格式如下：
	 * 1 2 3 4
	 * 2 3 4 5 
	 */
	public int write(String path, ArrayList<List<String>> dataArray)
	{
		File file = new File(path);
		try {
			if (file.exists()) {
				// System.out.println("文件已存在，内容将追加到已存在的文件中");
			} else {
				System.out.println("文件不存在，将创建文件");
				file.createNewFile();
			}
			FileOutputStream output = new FileOutputStream(path,true);
			for (List<String> list : dataArray) {
				String word = "";
				for (String string : list) {
					if (string.equals(list.get(0))) {
						word = word+string;
					}
					else {
						word = word+" "+string;
					}
					
				}
				word = word+"\r\n";
				output.write(word.getBytes());
			}
			
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			return 0;
		} catch (IOException e) {
			System.out.println(e);
			return 0;
		}
		return 1;
	}
}