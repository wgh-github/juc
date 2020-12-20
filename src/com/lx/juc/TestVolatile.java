package com.lx.juc;

/**
 * volatile关键字: 当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 				   相较于synchronized 是一种较为轻量级的同步策略
 *  注意：
 * 1.volatile 不具备互斥性 
 * 2.volatile 不能保证变量的"原子性"
 */
public class TestVolatile {

	public static void main(String[] args) {
		ThreadDemo threadDemo = new ThreadDemo();
		new Thread(threadDemo).start();
		while (true) {
			if (threadDemo.isFlag()) {
				System.out.println("-------------"+threadDemo.isFlag());
				break;
			}

		}
	}

}

class ThreadDemo implements Runnable {

	//volatile
	private volatile boolean flag = false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		flag = true;
		System.out.println(flag + "值");
	}

}
