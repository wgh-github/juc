package com.lx.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
  ConcurrentHashMap 同步容器类是java5新增加的一个线程安全的hash表，对于多线程操作介于hashmap和hashtable之间，
    内部采用所分段机制替代了hashtable的独占锁。线程并行，进而提高性能
    
  java.util.concurrent此包下还提供了多线程上下文中的collection实现
 */
public class TestConcurrentHashMap {

	public static void main(String[] args) {
		//和hashmap一样操作
		ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
		concurrentHashMap.put("ss", "ds");
		Set<Entry<Object,Object>> entrySet = concurrentHashMap.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			System.out.println(entry.getKey());
		}
		
		HelloThread helloThread = new HelloThread();
		for (int i = 0; i < 10; i++) {
			new Thread(helloThread).start();
		}
		
	}
}

class HelloThread implements Runnable{
	//复合操作， 出现并发修改异常 java.util.ConcurrentModificationException
//	static List<Object> list = Collections.synchronizedList(new ArrayList<>());
	
	//没有出现异常   每次写入时都会复制成一个新的列表，每次写入复制数据彼此独立，不会出现异常
	//添加多时，效率低，因为添加时都会进行复制，开销非常大，并发迭代多时可选择
	static CopyOnWriteArrayList<Object> list=new CopyOnWriteArrayList<>();
	
	static {
		list.add("AA");
		list.add("BB");
		list.add("CC");
	}
	@Override
	public void run() {
		Iterator<Object> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
			list.add("EE");
		}
		System.out.println();
	}
}
