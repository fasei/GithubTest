package com.zt.wc.githubtest.base;

public abstract class BaseLoopTask implements Runnable {
	private boolean isRunning = false;// 当前状态，是否执行
	private boolean flag = false;// 是否执行循环任务

	public void run() {
		isRunning = true;
		flag=true;
		onPrepare();
		while (flag) {
			onLoopTask();
		}
		onFinish();
		isRunning = false;
	}

	/**
	 * 准备任务，或者执行一次性的任务
	 */
	protected abstract void onPrepare();

	/**
	 * 重复任务，仅处理重复执行的动作此处不处理异常，由子类处理
	 */
	protected abstract void onLoopTask();

	protected  abstract  void onFinish();

	protected  void goFinish(){
		flag=false;
	}

	public void close() {
		flag = false;// 停止任务
	}

}
