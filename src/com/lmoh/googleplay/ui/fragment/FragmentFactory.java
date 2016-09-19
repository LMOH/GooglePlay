package com.lmoh.googleplay.ui.fragment;

import java.util.HashMap;

public class FragmentFactory {
	// 保存Fragment集合,方便复用
	private static HashMap<Integer, BaseFragment> fragmentMap = new HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int pos) {

		BaseFragment fragment = fragmentMap.get(pos);
		if (fragment == null) {
			// 根据指针位置,生产相应的Fragment
			switch (pos) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new RecommendFragment();
				break;
			case 5:
				fragment = new CategoryFragment();
				break;
			case 6:
				fragment = new HotFragment();
				break;

			default:
				break;
			}
			//每新建一个则保存至map中
			fragmentMap.put(pos, fragment);
		}
		return fragment;
	}
}
