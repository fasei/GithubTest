package com.zt.wc.githubtest.base;

public abstract class BaseLoopTimeTask extends BaseLoopTask {
	public static final long DefaultBaseTime=50;
	private long mLoopTime = 0;// 周期ms
	private long mBaseTime = 50;// 精度ms,不宜太大  大于0~2000

	@Override
	protected void onPrepare() {
		setmLoopTime(1000);
		setmBaseTime(DefaultBaseTime);
	}

	@Override
	protected void onLoopTask() {
		onLoopTimeTask();
		mSleep();
	}

	protected abstract void onLoopTimeTask();

	private void mSleep() {
		for (int i = 0; i < mLoopTime / mBaseTime; i++) {
			try {
				Thread.sleep(mBaseTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public long getmLoopTime() {
		return mLoopTime;
	}

	public void setmLoopTime(long mLoopTime) {
		this.mLoopTime = mLoopTime;
	}

	public long getmBaseTime() {
		return mBaseTime;
	}

	public void setmBaseTime(long mBaseTime) {
		this.mBaseTime = mBaseTime;
	}



}
