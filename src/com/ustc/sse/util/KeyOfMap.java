package com.ustc.sse.util;

import java.util.ArrayList;
import java.util.TreeSet;

public class KeyOfMap {

	TreeSet<ArrayList<Integer>> key;

	public KeyOfMap(ArrayList<Integer> list1, ArrayList<Integer> list2) {
		key = new TreeSet<ArrayList<Integer>>(new ListComparator());
		key.add(list1);
		key.add(list2);
	}

	@Override
	public boolean equals(Object obj) {
		KeyOfMap obj1 = (KeyOfMap) obj;
		if (key.first().containsAll(obj1.first())&&obj1.first().containsAll(key.first()) 
				&& key.last().containsAll(obj1.last())&&obj1.last().containsAll(key.last())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int x = 0;
		for (int i = 0; i < key.first().size(); i++) {
			x = x + 100 * key.first().get(i);
		}
		for (int i = 0; i < key.last().size(); i++) {
			x = x + 200 * key.last().get(i);
		}
		return x;
	}

	public ArrayList<Integer> first() {
		return key.first();
	}

	public ArrayList<Integer> last() {
		return key.last();
	}

}
