package threads;

import java.util.ArrayList;

import javax.swing.JButton;

import domain.Snake;
import logic.Logic;

public class ThreadFood extends Thread {

	private Snake snake;
	private Logic log;
	private JButton matrix[][];
	private boolean suspend;
	private int [] food;
	private ArrayList<int[]> obstacles;

	public ThreadFood(Logic log, JButton matrix[][],int [] food,Snake snake,ArrayList<int[]> obstacles) {
		this.log = log;
		this.matrix = matrix;
		this.food = food;
		this.snake = snake;
		this.obstacles =obstacles;
		
		suspend = false;
	}

	public void run() { 
		try {
			while (true) {
				
				Thread.sleep(50);
				if(food[0] == 0) {
				 log.spawnFood(matrix, food, snake, obstacles);
				 suspendThread();
				}
				
				synchronized (this) {
					if (suspend) {
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void suspendThread() {
		suspend = true;
	}

	public synchronized void resumeThread() {
		suspend = false;
		notify();
	}

}
