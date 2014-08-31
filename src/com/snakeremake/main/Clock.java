package com.snakeremake.main;

import com.snakeremake.views.BaseLevelView;

public class Clock extends Thread {

	private BaseLevelView view;
	private double ticksPerSecond;
	private boolean running;
	private boolean stopped;

	public Clock(BaseLevelView view, double ticksPerSecond) {
		super();
		this.view = view;
		this.ticksPerSecond = ticksPerSecond;
	}

	@Override
	public void run() {
		stopped = false;
		running = true;
		long lastTime = System.nanoTime();
		double ns = (1 / ticksPerSecond) * 1000000000;

		while (running) {
			long currentTime = System.nanoTime();
			if (currentTime - lastTime >= ns) {
				lastTime = currentTime;
				tick();
			}
		}

		// This is so pausing doesn't stop the thread
		while (!running) {}
		if (!stopped)
			run();
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

}
