package com.lx.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 
 *1.Callable 较runnable接口的方式，方法可以有返回值，并且可以跑出异常
  2.需要FutureTask类支持，用于接收运算结果 。FutureTask是Future接口的实现，可用于闭锁操作
 */
public class TestCallable {
	
	public static void main(String[] args) {
		callableDemo callableDemo = new callableDemo();
		FutureTask<Integer> futureTask=new FutureTask<>(callableDemo);
		new Thread(futureTask).start();
		
		try {
			Integer integer = futureTask.get();
			System.out.println(integer);
			//运行期间并没有打印，说明在等待线程执行完毕，即有闭锁效果
			System.out.println("------------");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}

class callableDemo implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		int sum=0;
		for (int i = 1; i <=Integer.MAX_VALUE; i++) {
			sum+=i;
		}
		return sum;
	}
	
}
