package logic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.Snake;
import gui.GUIGame;
import threads.ThreadFood;
import threads.ThreadMovements;

public class Logic {

	public void setPanelGame(JButton[][] matrix, JPanel panel) {
		int x = 0;
		int y = 54;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = new JButton();
				matrix[i][j].setBackground(new Color(0, 128, 0));
				matrix[i][j].setBorderPainted(false);
				matrix[i][j].setBounds(x, y, 11, 11);
				x += 11;
				panel.add(matrix[i][j]);
			}
			y += 11;
			x = 0;
		}
	}

	public void delimitBorders(JButton[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (i == 0 || i == matrix.length - 1 || j == 0 || j == matrix.length - 1) {
					matrix[i][j].setIcon(new ImageIcon("SnakeGame/src/img/pared.png"));
					matrix[i][j].setBorderPainted(false);
				}
			}
		}
	}

	public void printSnake(Snake snake, JButton[][] matrix) {

		for (int[] dot : snake.getSnake()) {
			matrix[dot[0]][dot[1]].setBackground(Color.YELLOW);
		}

	}

	public void printClean(Snake snake, JButton[][] matrix) {

		for (int[] dot : snake.getSnake()) {
			matrix[dot[0]][dot[1]].setBackground(new Color(0, 128, 0));
		}
	}

	public void spawnFood(JButton[][] matrix, int[] food, Snake snake, ArrayList<int[]> obstacles) {

		boolean flag = true;

		while (flag) {
			food[0] = ThreadLocalRandom.current().nextInt(1, matrix.length - 2);
			food[1] = ThreadLocalRandom.current().nextInt(1, matrix.length - 2);

			if (verifyFoodNotSpawnOnObstacles(food[0], food[1], obstacles)) {
				
				for (int[] dot : snake.getSnake()) {
					if (food[0] == dot[0] & food[1] == dot[1]) {
						break;
					} else {
						flag = false;
					}
				}
			}
		}
		matrix[food[0]][food[1]].setIcon(new ImageIcon("SnakeGame/src/img/apple.png"));
	}

	public boolean verifyFoodNotSpawnOnObstacles(int num, int num2, ArrayList<int[]> obstacles) {
		boolean flag = false;

		for (int[] is : obstacles) {
			if (is[0] == num && is[1] == num2) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		return flag;
	}

	public void snakeEatFood(int[] food, Snake snake, ThreadFood threadFood, int mov, GUIGame game,
			JButton[][] matrix) {

		int temp[] = new int[2];

		if (snake.getSnake().get(0)[0] == food[0] & snake.getSnake().get(0)[1] == food[1]) {

			matrix[food[0]][food[1]].setIcon(null);

			switch (mov) {
			case 1:
				temp[0] = food[0];
				temp[1] = food[1]++;
				break;

			case 2:
				temp[0] = food[0]++;
				temp[1] = food[1];
				break;
			case 3:
				temp[0] = food[0];
				temp[1] = food[1]--;
				break;

			case 4:
				temp[0] = food[0]--;
				temp[1] = food[1];
				break;
			}

			snake.getSnake().add(0, temp);
			ThreadMovements.score++;
			game.getTxtScore().setText("Score: " + ThreadMovements.score);

			threadFood.resumeThread();

			food[0] = 0;
			food[1] = 0;

		}
	}

	public boolean verifyColisions(Snake snake, JButton matrix[][]) {
		boolean flag = false;
		if (snake.getSnake().get(0)[0] == 0 || snake.getSnake().get(0)[0] == matrix.length - 1
				|| snake.getSnake().get(0)[1] == 0 || snake.getSnake().get(0)[1] == matrix.length - 1) {
			flag = true;
		}
		return flag;
	}

	public boolean verifyColisionsWhitObstacles(Snake snake, ArrayList<int[]> obstacles) {
		boolean flag = false;

		for (int[] is : obstacles) {
			if (is[0] == snake.getSnake().get(0)[0] && is[1] == snake.getSnake().get(0)[1]) {
				flag = true;
				break;
			}
		}

		return flag;
	}

	public void printObstacles(ArrayList<int[]> obstacles, JButton[][] matrix) {
		for (int[] is : obstacles) {
			ImageIcon icon = new ImageIcon("SnakeGame/src/img/obstacle.png");
			matrix[is[0]][is[1]].setIcon(icon);
		}
	}

	public void clearObstacles(ArrayList<int[]> obstacles, JButton[][] matrix) {
		for (int[] is : obstacles) {
			matrix[is[0]][is[1]].setIcon(null);
		}
	}

	public boolean verifyColisionsWithSnake(Snake snake) {
		boolean flag = false;

		for (int i = 2; i < snake.getSnake().size(); i++) {
			if (snake.getSnake().get(0)[0] == snake.getSnake().get(i)[0]
					&& snake.getSnake().get(0)[1] == snake.getSnake().get(i)[1]) {
				flag = true;
			}
		}
		return flag;
	}

	public String getPath() {
		String path = System.getProperty("user.dir");
		return path;
	}

	public void setObstacles(ArrayList<int[]> obtacles, JButton[][] matrix) {

		for (int i = 0; i < 30 ; i++) {

			int rand0 = ThreadLocalRandom.current().nextInt(4, matrix.length - 3);
			int rand1 = ThreadLocalRandom.current().nextInt(4, matrix.length - 3);
			int rand2 = ThreadLocalRandom.current().nextInt(1, 2);
			if (rand0 != ((matrix.length / 2) - 1) || rand0 != ((matrix.length / 2) - 2)) {
				switch (rand2) {
				case 1:
					getObstracle(rand0, rand1, obtacles);
					break;
				case 2:
					getObstracle1(rand0, rand1, obtacles);
					break;
				}

			} else {
				i--;
			}
		}

	}

	public void getObstracle1(int num, int num1, ArrayList<int[]> obstacles) {

		int[] obstacle1 = { num, num1 };

		obstacles.add(0, obstacle1);

	}

	public void getObstracle(int num, int num1, ArrayList<int[]> obstacles) {

		int[] obstacle1 = { num, num1 };
		int[] obstacle2 = { num, num1-- };

		obstacles.add(0, obstacle1);
		obstacles.add(0, obstacle2);

	}
}
