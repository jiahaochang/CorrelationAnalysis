package com.ustc.sse.util;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Jiahao Zhang
 * @school University of Science and Technology of China
 * @mail jiahao_zhang@qq.com
 */

public class ListComparator implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		ArrayList<Integer> list1 = (ArrayList<Integer>) arg0;
		ArrayList<Integer> list2 = (ArrayList<Integer>) arg1;
		if (!list1.isEmpty() && !list2.isEmpty()) {
			if (list1.size() < list2.size()) {
				return 1;
			} else if (list1.size() > list2.size()) {
				return -1;
			} else if (list1.size() == 0 || list2.size() == 0) {
				return 1;
			} else {
				for (int i = 0; i < list1.size(); i++) {
					if (list1.get(i) - list2.get(i) <= 0) {
						return 1;
					} else {
						return -1;
					}
				}
			}
		}

		return 0;
	}

}
