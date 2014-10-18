package com.snakeremake.main;

import com.snakeremake.views.LevelView;

public class Clock extends Thread {

    private Level level;

    public double getTicksPerSecond() {
        return ticksPerSecond;
    }

    private double ticksPerSecond;
	private boolean running;
	private boolean stopped;
	private double realTPS = 0;

	public Clock(double ticksPerSecond) {
		super();
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
		if(level != null) level.onTick();
	}

	public double getRealTPS() {
		return realTPS;
	}

    public void setLevel(Level level) { this.level = level; }

}
