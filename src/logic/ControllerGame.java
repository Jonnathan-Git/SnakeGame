package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import domain.Snake;
import gui.GUIGame;
import threads.ThreadFood;
import threads.ThreadMovements;

public class ControllerGame implements KeyListener , ActionListener{
	
	private GUIGame game;
	private Logic log;
	private JButton[][] matrix;
	private Snake snake;
	private ThreadMovements tMov;
	private ThreadFood tFood;
	private int [] food;
	private ArrayList<int[]> obtracles;
	private boolean flag;

	public ControllerGame() {
		game = new GUIGame();
		log = new Logic();
		matrix = new JButton[50][50];
		snake = new Snake(new ArrayList<>());
		obtracles = new ArrayList<>();
		food = new int[2];
		tFood = new ThreadFood(log, matrix, food, snake, obtracles);
		tMov = new ThreadMovements(snake, log, matrix, game, food, tFood, obtracles);
		

		initializer();
	}

	private void initializer() {

		game.getBtnPauseAndResume().addActionListener(this);
		game.getBtnRestart().addActionListener(this);
		
		log.setPanelGame(matrix, game.contentPane);
		
//-----------------------
		initialiceGame();
//-----------------------
		
		
		game.requestFocus();
		game.addKeyListener(this);
		
		 tMov.start();
		 tFood.start();
	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == game.getBtnPauseAndResume()) {
			
			if(flag) {
				tMov.suspendThread();
				game.getBtnPauseAndResume().setIcon(new ImageIcon("SnakeGame/src/img/play.png"));
				flag = false;
				game.requestFocus();
			}else {
				tMov.resumeThread();
				game.getBtnPauseAndResume().setIcon(new ImageIcon("SnakeGame/src/img/pause.png"));
				flag = true;
				game.requestFocus();
			}
			
		}
		
		if(e.getSource() == game.getBtnRestart()) {
			log.clearObstacles(obtracles, matrix);
			log.printClean(snake, matrix);
			initialiceGame();
			game.getBtnPauseAndResume().setEnabled(true);
			tMov.resumeThread();
			tFood.resumeThread();
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

		char keyChar = e.getKeyChar();

		if (keyChar == 'w' || keyChar == 'W') {
			ThreadMovements.mov = 4;
		} else if (keyChar == 'a' || keyChar == 'A') {
			ThreadMovements.mov = 3;
		} else if (keyChar == 's' || keyChar == 'S') {
			ThreadMovements.mov = 2;
		} else if (keyChar == 'd' || keyChar == 'D') {
			ThreadMovements.mov = 1;
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_UP) {
			ThreadMovements.mov = 4;
		} else if (keyCode == KeyEvent.VK_DOWN) {
			ThreadMovements.mov = 2;
		} else if (keyCode == KeyEvent.VK_LEFT) {
			ThreadMovements.mov = 3;
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			ThreadMovements.mov = 1;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void initialiceGame() {

		flag = true;
		
		game.getBtnPauseAndResume().setIcon(new ImageIcon("SnakeGame/src/img/pause.png"));
		game.getBtnRestart().setIcon(new ImageIcon("SnakeGame/src/img/reset.png"));
		
		snake.getSnake().clear();
		obtracles.clear();
		
		int[] temp = { (matrix.length / 2) - 1, (matrix.length / 2) - 1 };
		int[] temp2 = { (matrix.length / 2) - 1, (matrix.length / 2) - 2 };
		
		snake.getSnake().add(0,temp);
		snake.getSnake().add(1,temp2);
		
		log.delimitBorders(matrix);
		log.setObstacles(obtracles, matrix);
		log.printObstacles(obtracles, matrix);
		log.printSnake(snake, matrix);
		ThreadMovements.score = 0;
		
		game.getTxtScore().setText("Score: "+ThreadMovements.score);
		game.requestFocus();

		game.setVisible(true);

	}
	
}
