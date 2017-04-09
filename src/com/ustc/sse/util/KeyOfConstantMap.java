package com.ustc.sse.util;

import java.util.TreeSet;

/**
 * @author Jiahao Zhang
 * @school University of Science and Technology of China
 * @mail jiahao_zhang@qq.com
 */

public class KeyOfConstantMap {
	TreeSet<Integer> key;

	public KeyOfConstantMap(int a, int b) {
		key = new TreeSet<Integer>();
		key.add(a);
		key.add(b);
	}

	@Override
	public boolean equals(Object obj) {
		KeyOfConstantMap obj1 = (KeyOfConstantMap) obj;
		if (key.first() == obj1.first() && key.last() == obj1.last()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int x = 0;
		x = key.first() + key.last()*100;
		return x;
	}

	public int first() {
		return key.first();
	}

	public int last() {
		return key.last();
	}
}
