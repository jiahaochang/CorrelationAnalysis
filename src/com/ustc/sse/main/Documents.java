package com.ustc.sse.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.ustc.sse.util.KeyOfConstantMap;
import com.ustc.sse.util.KeyOfMap;
import com.ustc.sse.util.ListComparator;

/**
 * @author Jiahao Zhang
 * @school University of Science and Technology of China
 * @mail jiahao_zhang@qq.com
 */

public class Documents {
	List<ArrayList<Integer>> twoDimensionalX;
	List<Integer> T;
	Map<KeyOfConstantMap, Integer> dCountMapConstant; // 初始时求出的矩阵M（x,y）=value,保持不变
	Map<KeyOfMap, Integer> dCount;
	List<ArrayList<Integer>> C;
	List<ArrayList<Integer>> outPut;
	int n;

	public Documents() {
		twoDimensionalX = new ArrayList<ArrayList<Integer>>();
		T = new ArrayList<Integer>();
		dCountMapConstant = new HashMap<KeyOfConstantMap, Integer>();
		C = new ArrayList<ArrayList<Integer>>();
		dCount = new HashMap<KeyOfMap, Integer>();
		outPut = new ArrayList<ArrayList<Integer>>();
	}

	static List<Integer> aryChange(String temp) {// 字符串数组解析成int数组

		/**
		 * .trim()可以去掉首尾多余的空格 .split("\\s+") 表示用正则表达式去匹配切割字符串,
		 * \\s+表示匹配一个或两个以上的空白符
		 */
		String[] ss = temp.trim().split("\\s+");

		List<Integer> oneDimensionalX = new ArrayList<Integer>();
		for (int i = 0; i < ss.length; i++) {
			// 解析数组的每行n个元素
			oneDimensionalX.add(Integer.parseInt(ss[i]));
		}
		return oneDimensionalX;// 返回n个int数组
	}

	/**
	 * 用于读入二维向量X
	 */
	public void readInputX(String docsPath) throws Exception {

		// 使用BufferedReader 最大好处是可以按行读取,每次读取一行
		BufferedReader br = new BufferedReader(new FileReader(docsPath));
		// 定义字符串,用于保存每行读取到的数据
		String temp;
		while ((temp = br.readLine()) != null) {
			List<Integer> ary = aryChange(temp);// 通过函数吧字符串数组解析成整数数组
			twoDimensionalX.add((ArrayList<Integer>) ary);
		}

		br.close();// 关闭输入流
	}

	/**
	 * 用于读入一维向量T
	 */
	public void readInputT(String docsPath) throws Exception {
		// 使用BufferedReader 最大好处是可以按行读取,每次读取一行
		BufferedReader br = new BufferedReader(new FileReader(docsPath));
		// 定义字符串,用于保存每行读取到的数据
		String temp;
		while ((temp = br.readLine()) != null) {
			List<Integer> ary = aryChange(temp);
			T.addAll(ary);// 通过函数吧字符串数组解析成整数数组
		}

		for (int i = 0; i < T.size(); i++) {
			ArrayList<Integer> ti = new ArrayList<Integer>();
			ti.add(T.get(i));
			C.add(ti);
		}

		br.close();// 关闭输入流
	}

	/**
	 * 求X中每个一维向量中元素的组合情况
	 */
	public void seekCombinationsAndCountM() {// 求X中每个一维向量中元素的组合情况

		for (int i = 0; i < T.size(); i++) {
			for (int j = i + 1; j < T.size(); j++) {
				ArrayList<Integer> tempCiCj = new ArrayList<Integer>();
				tempCiCj.add(T.get(i));
				tempCiCj.add(T.get(j));
				int count = countIncludeNumber(tempCiCj);

				// 构建dCount的key
				ArrayList<Integer> tempArrayList = new ArrayList<Integer>();
				tempArrayList.add(tempCiCj.get(0));
				ArrayList<Integer> tempArrayList1 = new ArrayList<Integer>();
				tempArrayList1.add(tempCiCj.get(1));
				KeyOfMap tempTreeSet = new KeyOfMap(tempArrayList, tempArrayList1);

				// 往M映射中加入元素
				dCount.put(tempTreeSet, count);
			}
		}

	}

	/**
	 * 计算X中有多少元素个包含Ci
	 */
	public int countIncludeNumber(ArrayList<Integer> Ci) {
		int count = 0;

		for (ArrayList<Integer> tempx : twoDimensionalX) {
			if (tempx.containsAll(Ci)) {
				count++;
			}
		}

		return count;
	}

	/**
	 * 工具方法，用于line19的操作，用来删除F中符合条件的项
	 */
	public Map<KeyOfMap, Double> removeElements(KeyOfMap key, Map<KeyOfMap, Double> F) {
		ArrayList<Integer> firstElement = key.first();
		ArrayList<Integer> secondElement = key.last();
		Map<KeyOfMap, Double> currentF = new HashMap<KeyOfMap, Double>();
		currentF.putAll(F);
		;
		Set<KeyOfMap> keyset = F.keySet();

		for (KeyOfMap tempKey : keyset) {
			ArrayList<Integer> firstElementOfTempKey = tempKey.first();
			ArrayList<Integer> secondElementOfTempKey = tempKey.last();
			KeyOfMap tempKeyCopy = new KeyOfMap(firstElementOfTempKey, secondElementOfTempKey);
			if (firstElement.equals(firstElementOfTempKey) || firstElement.equals(secondElementOfTempKey)
					|| secondElement.equals(firstElementOfTempKey) || secondElement.equals(secondElementOfTempKey)) {
				currentF.remove(tempKeyCopy);
			}
		}
		return currentF;
	}

	/**
	 * 大while循环
	 */
	public void polymerization(double alpha, double beta, int gamma) {
		n = T.size();
		double m = n;
		Map<KeyOfMap, Double> F = new HashMap<KeyOfMap, Double>();
		while (n > gamma) {
			F.clear();
			for (int i = 0; i < n - 1; i++) {
				for (int j = i + 1; j < n; j++) {
					// System.out.println(i+" "+j);
					ArrayList<Integer> Ci, Cj;
					Ci = C.get(i);
					Cj = C.get(j);
					// System.out.println(Ci+" "+Cj);

					KeyOfMap tempM = new KeyOfMap(Ci, Cj);
					if (dCount.get(tempM) != null) {
						int M = dCount.get(tempM);
						// System.out.println(M);

						double f = M / m;
						if ((float) (M / m) >= alpha) {
							F.put(tempM, f);
						}
					}

				}
			}

			/*
			 * for (KeyOfMap a : dCount.keySet()) { System.out.println(a.first()
			 * + ":" + a.last() + "=" + dCount.get(a)); }
			 */

			int flag = 0;

			// while (!F.isEmpty()) {

			// 取出F中的最大value对应的key。思想：先获取value数组进行排序，之后取出最大的值一个key即可
			Collection<Double> c = F.values();
			Object[] obj = c.toArray();

			double maxFValue = 0;
			// maxFValue = seekMaxValue(obj);

			Arrays.sort(obj);
			maxFValue = (double) obj[obj.length - 1];

			/*
			 * System.out.println(F.size());
			 * 
			 * for(Map.Entry<KeyOfMap, Double> entry : F.entrySet()){
			 * System.out.println(entry.getKey().first()+":"+entry.getKey().last
			 * ()+"="+entry.getValue()); }
			 */

			// 根据最大value获取对应的key
			ArrayList<Integer> templist1 = new ArrayList<Integer>();
			ArrayList<Integer> templist2 = new ArrayList<Integer>();
			KeyOfMap key = new KeyOfMap(templist1, templist1);
			// Set<KeyOfMap> kset = F.keySet();
			/*
			 * for (KeyOfMap ks : kset) { if (((Double)
			 * maxFValue).equals(F.get(ks))) { key = ks; break; } }
			 */
			for (Map.Entry<KeyOfMap, Double> entry : F.entrySet()) {
				if (((Double) maxFValue).equals(entry.getValue())) {
					key = entry.getKey();
					break;
				}
			}

			// System.out.println(key.first()+":"+key.last());
			// 从F中删除这个f(Ci,Cj)
			// F.remove(key);
			// 从F中删除key中包含Ci,Cj的项
			// F = removeElements(key, F);
			/*
			 * for(Entry<KeyOfMap, Integer> entry : dCount.entrySet()){
			 * System.out.println("M("+entry.getKey().first()+":"+entry.getKey()
			 * .last()+")="+entry.getValue()); }
			 */

			int N = twoDimensionalX.size();

			int M_i_j = dCount.get(key);
			int psiCi = 0, psiCj = 0;

			ArrayList<Integer> Ci, Cj;
			Ci = key.first();
			Cj = key.last();

			psiCi = countIncludeNumber(Ci);
			psiCj = countIncludeNumber(Cj);
			int indexOfCi = C.indexOf(Ci);

			if ((psiCi * psiCj) != 0 && Math.max(psiCi, psiCj) != 0 && ((double) (N * M_i_j) / (psiCi * psiCj) > 1)
					&& ((double) M_i_j / Math.max(psiCi, psiCj) >= beta)) {

				flag = 1;

				// 把Ci,Cj合并到Ci中;
				if (!Ci.containsAll(Cj)) {
					Ci.addAll(Cj);
				}

				C.set(indexOfCi, Ci);
				// 重新整理M(Ci，Cj)
				C.remove(Cj);

				n--;

				// removeElementsOfM(key, dCount);
				dCount = removeElementsOfM(key, dCount);

				for (ArrayList<Integer> tempCj : C) {
					ArrayList<Integer> tempCiCj = new ArrayList<Integer>();
					tempCiCj.addAll(Ci);
					tempCiCj.addAll(tempCj);
					int tempCount = countIncludeNumber(tempCiCj);

					KeyOfMap newkey = new KeyOfMap(Ci, tempCj);
					dCount.put(newkey, tempCount);
				}

			}
			// }

			if (flag == 0) {
				break;
			}

		}
	}

	public void outputResult(String resPath) throws IOException {
		for (int i = 0; i < n; i++) {
			outPut.add(C.get(i));
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(resPath + "output" + ".txt"));
		for (int i = 0; i < outPut.size(); i++) {
			for (int j = 0; j < outPut.get(i).size(); j++) {
				writer.write(outPut.get(i).get(j) + "\t");
			}
			writer.write("\n");
		}
		writer.close();
	}

	/**
	 * 工具方法，用于每次循环重新整理映射M
	 */
	public Map<KeyOfMap, Integer> removeElementsOfM(KeyOfMap key, Map<KeyOfMap, Integer> F) {
		ArrayList<Integer> firstElement = key.first();
		ArrayList<Integer> secondElement = key.last();
		Map<KeyOfMap, Integer> currentF = new HashMap<KeyOfMap, Integer>();
		currentF.putAll(F);

		Set<KeyOfMap> keyset = F.keySet();

		for (KeyOfMap tempKey : keyset) {
			ArrayList<Integer> firstElementOfTempKey = tempKey.first();
			ArrayList<Integer> secondElementOfTempKey = tempKey.last();
			KeyOfMap tempKeyCopy = new KeyOfMap(firstElementOfTempKey, secondElementOfTempKey);
			if (firstElement.equals(firstElementOfTempKey) || firstElement.equals(secondElementOfTempKey)
					|| secondElement.equals(firstElementOfTempKey) || secondElement.equals(secondElementOfTempKey)) {
				currentF.remove(tempKeyCopy);
			}
		}
		return currentF;
	}

	public double seekMaxValue(Object[] obj) {
		double maxValue = 0;
		for (int i = 0; i < obj.length; i++) {
			if ((double) obj[i] > maxValue) {
				maxValue = (double) obj[i];
			}
		}
		return maxValue;
	}

}
