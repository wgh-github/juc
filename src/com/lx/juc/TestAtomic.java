package com.lx.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
  一： 测试数据原子性
  	 i++的原子性问题,
  	 volatile不能保证原子性问题
  二：原子变量-jdk1.5后 java.util.concurrent.atomic 包下提供了常用的原子变量
     1.volatile 保证内存可见性
     2.CAS(compare-and-swap) 算法保证数据的原子性
       CAS算法是硬件对于并发操作共享数据的支持
       CAS包含了三个操作数：
              内存值 V
              预估值 A
              更新值 B
              
       当且仅当V==A时，V=B 否则将不会做任何操作
              
       CAS线程不会阻塞，会重复取执行CAS算法，失败就再次去操作，直到成功
       
 */
public class TestAtomic {

	public static void main(String[] args) {
		AtomicDemo atomicDemo = new AtomicDemo();
		new Thread(atomicDemo).start();
		for (int i = 0; i < 10; i++) {
			new Thread(atomicDemo).start();
		}
	}

}

class AtomicDemo implements Runnable {

//	private volatile int value = 0;
	AtomicInteger value=new AtomicInteger();

	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"=="+getValue());
	}

	private int getValue() {
		return value.getAndIncrement();
	}

}
