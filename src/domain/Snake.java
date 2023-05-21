package domain;

import java.util.ArrayList;

public class Snake {

	private ArrayList<int []> snake;

	public Snake() {
	
	}

	public Snake(ArrayList<int[]> snake) {
		super();
		this.snake = snake;
	}

	public ArrayList<int[]> getSnake() {
		return snake;
	}

	public void setSnake(ArrayList<int[]> snake) {
		this.snake = snake;
	}
}
