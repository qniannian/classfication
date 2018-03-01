package NativeBayes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.DoubleToLongFunction;

import util.readfile;

/**
 * 朴素贝叶斯算法场景调用类
 */
public class Client {
	public static void main(String[] args){
		readfile read = new readfile();
		//训练集数据
		String filePath = "D:\\气象数据\\合并后\\训练集.txt";
		String testPath = "D:\\气象数据\\合并后\\测试集.txt";
		NaiveBayesTool tool = new NaiveBayesTool(filePath);
		List<String> testData = read.readtestData(testPath);
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < testData.size(); i++) {
			String string = tool.naiveBayesClassificate(testData.get(i));
			System.out.println(string);
			result.add(string);
		}
		
		//读取标准结果
		ArrayList<List<String>> biaozhuan = read.readsecondData("D:\\气象数据\\合并后\\测试集标准.txt");
		List<String> biao = new ArrayList<String>();
		Iterator<List<String>> ite = biaozhuan.iterator();
		while (ite.hasNext()) {
			List<String> list = ite.next();
			biao.add(list.get(7));
			
		}
		//计算准确率
		int truedata = 0;
		if (result.size()==biao.size()) {
			for(int i = 0; i < result.size(); i++)
			{
				if (result.get(i).equals(biao.get(i))) {
					truedata++;
				}
			}
		}
		
		double p = (double)truedata/result.size();
		System.out.println("准确率为："+p);
	}
}