package NativeBayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleToLongFunction;

/**
 * 朴素贝叶斯算法工具类
 */
public class NaiveBayesTool {
	// 类标记符，这里分为2类，YES和NO
	private String C1 = "无雨";  
	private String C2 = "微量";
	private String C3 = "小雨";
	private String C4 = "中雨";
	private String C5 = "大雨";
	private String C6 = "暴雨";
	private String C7 = "大暴雨";
	private String C8 = "特大暴雨";

	// 已分类训练数据集文件路径
	private String filePath;
	// 属性名称数组
	private String[] attrNames;
	// 训练数据集
	private String[][] data;

	// 每个属性的值所有类型
	private HashMap<String, ArrayList<String>> attrValue;

	public NaiveBayesTool(String filePath) {
		this.filePath = filePath;

		readDataFile();
		initAttrValue();
	}

	/**
	 * 从文件中读取数据
	 */
	private void readDataFile() {
		File file = new File(filePath);
		ArrayList<String[]> dataArray = new ArrayList<String[]>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			String[] tempArray;
			while ((str = in.readLine()) != null) {
				tempArray = str.split(" ");  
				dataArray.add(tempArray);
			} 
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}

		data = new String[dataArray.size()][];
		dataArray.toArray(data);
		attrNames = data[0];
		    
	}

	/**
	 * 首先初始化每种属性的值的所有类型，用于后面的子类熵的计算时用
	 */
	private void initAttrValue() {
		attrValue = new HashMap<>();
		ArrayList<String> tempValues;

		// 按照列的方式，从左往右找
		for (int j = 0; j < attrNames.length; j++) {
			// 从一列中的上往下开始寻找值
			tempValues = new ArrayList<>();
			for (int i = 1; i < data.length; i++) {
				if (!tempValues.contains(data[i][j])) {
					// 如果这个属性的值没有添加过，则添加
					tempValues.add(data[i][j]);
				}
			}
  
			// 一列属性的值已经遍历完毕，复制到map属性表中
			attrValue.put(data[0][j], tempValues);
		}

	}

	/**
	 * 在classType的情况下，发生condition条件的概率。即似然概率
	 * 
	 * @param condition
	 *            属性条件
	 * @param classType
	 *            分类的类型
	 * @return
	 */
	private double computeConditionProbably(String condition, String classType) {
		// 条件计数器
		int count = 0;
		// 条件属性的索引列
		int attrIndex = 1;
		// yes类标记符数据
		ArrayList<String[]> C1ClassData = new ArrayList<>();
		ArrayList<String[]> C2ClassData = new ArrayList<>();
		ArrayList<String[]> C3ClassData = new ArrayList<>();
		ArrayList<String[]> C4ClassData = new ArrayList<>();
		ArrayList<String[]> C5ClassData = new ArrayList<>();
		ArrayList<String[]> C6ClassData = new ArrayList<>();
		ArrayList<String[]> C7ClassData = new ArrayList<>();
		ArrayList<String[]> C8ClassData = new ArrayList<>();
		
		ArrayList<String[]> classData;

		for (int i = 0; i < data.length; i++) {
			// data数据按照yes和no分类
			if (data[i][attrNames.length - 1].equals(C1)) {
				C1ClassData.add(data[i]);
			} 
			else if (data[i][attrNames.length - 1].equals(C2)) {
				C2ClassData.add(data[i]);
			}
			else if (data[i][attrNames.length - 1].equals(C3)) {
				C3ClassData.add(data[i]);
			}
			else if (data[i][attrNames.length - 1].equals(C4)) {
				C4ClassData.add(data[i]);
			}
			else if (data[i][attrNames.length - 1].equals(C5)) {
				C5ClassData.add(data[i]);
			}
			else if (data[i][attrNames.length - 1].equals(C6)) {
				C6ClassData.add(data[i]);
			}
			else if (data[i][attrNames.length - 1].equals(C7)) {
				C7ClassData.add(data[i]);
			}else {
				C8ClassData.add(data[i]);
			}
		}

		if (classType.equals(C1)) {
			classData = C1ClassData;
		} 
		else if (classType.equals(C2)) {
			classData = C2ClassData;
		}
		else if (classType.equals(C3)) {
			classData = C3ClassData;
		}
		else if (classType.equals(C4)) {
			classData = C4ClassData;
		}
		else if (classType.equals(C5)) {
			classData = C5ClassData;
		}
		else if (classType.equals(C6)) {
			classData = C6ClassData;
		}
		else if (classType.equals(C7)) {
			classData = C7ClassData;
		}else {
			classData = C8ClassData;
		}

		// 如果没有设置条件则，计算的是纯粹的类事件概率
		if (condition == null) {
			return 1.0 * classData.size() / (data.length - 1);
		}

		// 寻找此条件的属性列
		attrIndex = getConditionAttrName(condition);

		for (String[] s : classData) {
			if (s[attrIndex].equals(condition)) {
				count++;
			}
		}
		double a=1.0 * count / classData.size();
		return a;
	}

	/**
	 * 根据条件值返回条件所属属性的列值
	 * 
	 * @param condition
	 *            条件
	 * @return
	 */
	private int getConditionAttrName(String condition) {
		// 条件所属属性名
		String attrName = "";
		// 条件所在属性列索引
		int attrIndex = 1;
		// 临时属性值类型
		ArrayList<String[]> valueTypes;
		for (Map.Entry entry : attrValue.entrySet()) {
			valueTypes = (ArrayList<String[]>) entry.getValue();
			if (valueTypes.contains(condition)
					&& !((String) entry.getKey()).equals("BuysComputer")) {
				attrName = (String) entry.getKey();
			}
		}

		for (int i = 0; i < attrNames.length - 1; i++) {
			if (attrNames[i].equals(attrName)) {
				attrIndex = i;
				break;
			}
		}

		return attrIndex;
	}

	/**
	 * 进行朴素贝叶斯分类
	 * 
	 * @param data
	 *            待分类数据
	 */
	public String naiveBayesClassificate(String data) {
		// 测试数据的属性值特征
		String[] dataFeatures;
		// 在yes的条件下，x事件发生的概率
		double WhenC1 = 1.0;
		double WhenC2 = 1.0;
		double WhenC3 = 1.0;
		double WhenC4 = 1.0;
		double WhenC5 = 1.0;
		double WhenC6 = 1.0;
		double WhenC7 = 1.0;
		double WhenC8 = 1.0;
		// 最后也是yes和no分类的总概率，用P(X|Ci)*P(Ci)的公式计算
		double [] p = new double[]{1,1,1,1,1,1,1,1};

		dataFeatures = data.split(" ");
		for (int i = 0; i < dataFeatures.length; i++) {
			// 因为朴素贝叶斯算法是类条件独立的，所以可以进行累积的计算
			WhenC1 *= computeConditionProbably(dataFeatures[i], C1);
			WhenC2 *= computeConditionProbably(dataFeatures[i], C2);
			WhenC3 *= computeConditionProbably(dataFeatures[i], C3);
			WhenC4 *= computeConditionProbably(dataFeatures[i], C4);
			WhenC5 *= computeConditionProbably(dataFeatures[i], C5);
			WhenC6 *= computeConditionProbably(dataFeatures[i], C6);
			WhenC7 *= computeConditionProbably(dataFeatures[i], C7);
			WhenC8 *= computeConditionProbably(dataFeatures[i], C8);
			
		}

		p[0] = WhenC1 * computeConditionProbably(null, C1);
		p[1] = WhenC2 * computeConditionProbably(null, C2);
		p[2] = WhenC3 * computeConditionProbably(null, C3);
		p[3] = WhenC4 * computeConditionProbably(null, C4);
		p[4] = WhenC5 * computeConditionProbably(null, C5);
		p[5] = WhenC6 * computeConditionProbably(null, C6);
		p[6] = WhenC7 * computeConditionProbably(null, C7);
		p[7] = WhenC8 * computeConditionProbably(null, C8);
		
		double max = 0;
		int flag = 0;
		for(int i = 0 ; i < p.length; i++)
		{
			if (p[i] > max) {
				max = p[i];
				flag = i;
			}
		}
		if (flag==0) {
			return C1;
		}
		if (flag==1) {
			return C2;
		}
		if (flag==2) {
			return C3;
		}
		if (flag==3) {
			return C4;
		}
		if (flag==4) {
			return C5;
		}
		if (flag==5) {
			return C6;
		}
		if (flag==6) {
			return C7;
		}
		else {
			return C8;
		} 
	}

}