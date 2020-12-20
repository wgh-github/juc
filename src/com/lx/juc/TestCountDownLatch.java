package com.lx.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 
CountDownLatch:闭锁：在完成某些运算时，只有在其他所有线程的运算全部完成时，当前运算才继续执行
 */
public class TestCountDownLatch {

	public static void main(String[] args) {
		final CountDownLatch countDownLatch = new CountDownLatch(5);
		
		CountDownLatchThread countDownLatchThread = new CountDownLatchThread(countDownLatch);
		long start = System.currentTimeMillis();
		System.out.println(start);
		for (int i = 0; i < 5; i++) {
			new Thread(countDownLatchThread).start();
		}
		
		try {
			//闭锁等待上面的线程执行完毕，才执行下面
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//主线程都是同时执行没办法计算执行时间
		long end = System.currentTimeMillis();
		System.out.println(end);
		
		System.out.println("耗费时间为:"+ (end-start));
	}
}

class CountDownLatchThread implements Runnable{
	
	private  CountDownLatch countDownLatchp;
	public CountDownLatchThread(CountDownLatch countDownLatchp) {
		this.countDownLatchp = countDownLatchp;
	}

	@Override
	public void run() {
		//加锁保安全
		synchronized (this) {
			try {
				for (int i = 0; i < 5000; i++) {
					if (i%2==0) {
						System.out.println(i);
					}
				}
			} catch (Exception e) {
			}finally {
				//必须保证每次减一
				//每个线程执行完毕减1,为0则执行完毕
				countDownLatchp.countDown();
			}
		}
	}
}
