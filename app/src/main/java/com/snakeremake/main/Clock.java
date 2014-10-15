package com.snakeremake.main;

import android.util.Log;

import com.snakeremake.views.BaseLevelView;

public class Clock extends Thread {

	private BaseLevelView view;
	private double ticksPerSecond;
	private boolean running;
	private boolean stopped;
	private double realTPS = 0;

	public Clock(BaseLevelView view, double ticksPerSecond) {
		super();
		this.view = view;
		this.ticksPerSecond = ticksPerSecond;
	}

	@Override
	public void run() {
		stopped = false;
		running = true;
		long lastTime = System.currentTimeMillis();
		double ms = (1 / ticksPerSecond) * 1000;

		while (!stopped) {
			if (running) {
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastTime >= ms) {
					tick();
					realTPS = (1.0 / ((System.currentTimeMillis() - lastTime)) * 1000);
					//Log.i("Snake-Remake",realTPS+"");
					lastTime = currentTime;
				}
			}
		try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			} 
		}
	}
	public void pauseClock() {
		running = false;
	}

	public void resumeClock() {
		running = true;
	}

	public void stopClock() {
		stopped = true;
	}

	private void tick() {
		view.onTick();
	}

	public double getRealTPS() {
		return realTPS;
	}

}
