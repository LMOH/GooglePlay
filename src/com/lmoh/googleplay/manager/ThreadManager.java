package com.lmoh.googleplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理器,限制线程个数，优化
 * 
 * @author PC-LMOH
 *
 */
public class ThreadManager {
	
	private static ThreadPool mThreadPool;

	/**获取一个线程池对象，单例，线程安全
	 * @return
	 */
	public static ThreadPool getThreadPool(){
		if (mThreadPool == null) {
			synchronized (ThreadManager.class) {
				if (mThreadPool == null) {
					//获取CPU数量
					int cpuCount = Runtime.getRuntime().availableProcessors();// 获取cpu数量
					//System.out.println("cup个数:" + cpuCount);
					
					int corePoolSize = 10;
					int maximumPoolSize = 10;
					long keepAliveTime = 1L;
					mThreadPool = new ThreadPool(corePoolSize, maximumPoolSize, keepAliveTime);
				}
			}
		}
		return mThreadPool;
	}

	// 线程池
	public static class ThreadPool {

		int corePoolSize;
		int maximumPoolSize;
		long keepAliveTime;
		private ThreadPoolExecutor executor;

		private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = keepAliveTime;
		}

		/**线程执行者
		 * corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
		 * threadFactory, handler
		 * 参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
		 */
		public void execute(Runnable r) {
			
			if (executor == null) {
				executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new AbortPolicy());
			}
			// 线程池执行一个Runnable对象, 具体运行时机线程池说了算
			executor.execute(r);
		}
		
		/**从队列移除一个任务
		 * @param r
		 */
		public void cancel(Runnable r){
			if (executor != null) {
				executor.getQueue().remove(r);
			}
		}
	}
}
