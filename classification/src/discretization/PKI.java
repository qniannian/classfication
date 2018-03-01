package discretization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.readfile;
import util.writeFile;
/**
 * 对某个文件的某一列数据进行离散化处理
 * @author Administrator
 *
 */
public class PKI {
	
	/**处理步骤：
	 * 1. 统计出总个数，开根号，得出区间大小和区间数
	   2. 根据待处理数据的序号，将该数据进行排序。
	   3. 根据pki原理，得到由区间点的值构成的数组
	   4. 根据第三步所得的数组，对数据进行标记
	 */
	private int dis; //区间大小
	private String flag; //属性标识
	private int i; //待处理数据的序号
	private ArrayList<List<String>> data; //待处理数据
	private List<Double> OrderNode; //存放，区间节点的值 
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public ArrayList<List<String>> getData() {
		return data;
	}

	public void setData(ArrayList<List<String>> data) {
		this.data = data;
	}
	
	//根据待处理数据的序号，将该数据进行排序。
	private List<Double> SortList()
	{   //统计出总个数，开根号，得出区间大小和区间数
		dis = (int) Math.sqrt(data.size());
		//从data中获取待排序数据
		List<Double> ToSort = new ArrayList<Double>();
		for (List<String> list : data) {
			for (int j = 0; j < list.size(); j++) {
				if (j==i) {
					double d = Double.parseDouble(list.get(j));
					ToSort.add(d);
					j = list.size();
				}
			}
		}
		//获取数据完毕
		//利用collection工具类对数据进行排序
		Collections.sort(ToSort);
		return ToSort;
	}
	
	//根据pki原理，得到由区间点的值构成的数组
	private void PKI()
	{
		//获得排序后的数据
		List<Double> sort = SortList();
		//存储结果
		OrderNode = new ArrayList<Double>();
		//中间变量
		int current = -1;
		while(current < sort.size())
		{
			if (current+dis >= sort.size()) {
				int a = sort.size()-1;
				double b = sort.get(a);
				OrderNode.add(sort.get(a));
				current = sort.size();
			}
			else {
				double d = sort.get(current+dis);
				OrderNode.add(d);
				if (d==32766) {
					current = data.size();
				}
				else{
					//保证相同的值，被分配在同一区间
					int j = current+dis+1;
					for(; j < data.size(); j++)
					{
						if (sort.get(j) > d) {
							current  = j;
							j = data.size()+1;
						}
					}
				}
			}
		}
		//dayin
		for (Double double1 : OrderNode) {
			System.out.println(double1);
		}
		System.out.println("共计："+OrderNode.size());
	}
	//根据第三步所得的数组，对数据进行标记
	public ArrayList<List<String>> setDataFlag()
	{
		PKI();
		for (List<String> list : data)
		{
			for (int j = 0; j < list.size(); j++) {
				if (j==i) {
					double d = Double.parseDouble(list.get(j));
					if (d == 32766) {
						list.set(j, "缺省");
					}
					else {
						int a = getOrder(d);
						list.set(j, flag+getOrder(d));
					}
					j = list.size();
				}
			}
		}
		return data;
	}
	
	private int getOrder(double d)
	{
		
		int min, max, mid;
		min = 0;
		max = OrderNode.size();
		if (d < OrderNode.get(min)) {
			max = 0;
		}
		else {
			while(max-min != 1)
			{
				mid  = (min+max)/2;
				if (d > OrderNode.get(mid)) {
					min = mid;
				}
				else {
					max = mid;
				}
			}
		}
		return max;
	}
	public static void  main(String args[])
	{
		readfile read = new readfile();
		writeFile wFile = new writeFile();
		PKI pki = new PKI();
		//本站气压  风向风速   气温   日照   相对湿度  蒸发
		ArrayList<List<String>> data = read.readsecondData("D:\\气象数据\\整理一次的数据\\蒸发");
		pki.setI(5);
		pki.setData(data);
		pki.setFlag("蒸发");
		data = pki.setDataFlag();
		
		pki.setI(6);
		pki.setData(data);
		data = pki.setDataFlag();
//		
//		pki.setI(8);
//		pki.setData(data);
//		data = pki.setDataFlag();
		
		wFile.write("D:\\气象数据\\气象数据离散化\\蒸发.txt", data);
	}
}