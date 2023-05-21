package threads;
import java.util.ArrayList;

import javax.swing.JButton;

import domain.Snake;
import gui.GUIGame;
import logic.Logic;

public class ThreadMovements extends Thread {

	private Snake snake;
	private Logic log;
	private JButton matrix[][];
	private GUIGame game;
	private boolean suspend;
	private int[] food;
	private ArrayList<int[]> obstacles;
	private ThreadFood tFood;
	public static int mov = 1;
	public static int score = 0;

	public ThreadMovements(Snake snake, Logic log, JButton matrix[][], GUIGame game, int[] food, ThreadFood tFood,
			ArrayList<int[]> obstacles) {
		this.snake = snake;
		this.log = log;
		this.matrix = matrix;
		this.game = game;
		this.food = food;
		this.tFood = tFood;
		this.obstacles = obstacles;
		suspend = false;
	}

	public void run() {
		while (true) {

			log.printClean(snake, matrix);

			switch (mov) {
				case 1:
					moveRigth();
					break;
				case 2:
					moveDown();
					break;

				case 3:
					moveLeft();
					break;

				case 4:
					moveUp();
					break;
			}

			if (food != null) {
				log.snakeEatFood(food, snake, tFood, mov, game, matrix);
			}

			log.printSnake(snake, matrix);

			if (log.verifyColisions(snake, matrix) || log.verifyColisionsWithSnake(snake)
					|| log.verifyColisionsWhitObstacles(snake, obstacles)) {
				suspendThread();
				game.getBtnPauseAndResume().setEnabled(false);
			}
			try {
				Thread.sleep(160);
				synchronized (this) {
					if (suspend) {
						wait();
					}
				}
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

	private void moveRigth() {

		int[] temp = { snake.getSnake().get(0)[0], snake.getSnake().get(0)[1] };
		temp[1]++;

		snake.getSnake().add(0, temp);
		snake.getSnake().remove(snake.getSnake().size() - 1);

	}

	private void moveLeft() {

		int[] temp = { snake.getSnake().get(0)[0], snake.getSnake().get(0)[1] };
		temp[1]--;

		snake.getSnake().add(0, temp);
		snake.getSnake().remove(snake.getSnake().size() - 1);

	}

	private void moveDown() {
		int[] temp = { snake.getSnake().get(0)[0], snake.getSnake().get(0)[1] };
		temp[0]++;

		snake.getSnake().add(0, temp);
		snake.getSnake().remove(snake.getSnake().size() - 1);

	}

	private void moveUp() {
		int[] temp = { snake.getSnake().get(0)[0], snake.getSnake().get(0)[1] };
		temp[0]--;

		snake.getSnake().add(0, temp);
		snake.getSnake().remove(snake.getSnake().size() - 1);

	}

	public synchronized void suspendThread() {
		suspend = true;
	}

	public synchronized void resumeThread() {
		suspend = false;
		notify();
	}

}
