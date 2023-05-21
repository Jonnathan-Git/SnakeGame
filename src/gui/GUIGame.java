package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;

public class GUIGame extends JFrame {

	public JPanel contentPane;
	private JPanel panel;
	private JTextField txtScore;
	private JButton btnPauseAndResume;
	private JButton btnRestart;

	public GUIGame() {
		setTitle("Snake Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 640);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocationRelativeTo(null);
		
		contentPane.setLayout(null);
		contentPane.add(getTxtScore());
		
		setContentPane(contentPane);
		contentPane.add(getBtnPauseAndResume());
		contentPane.add(getBtnRestart());
	}
	public JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(10, 10, 766, 543);
		}
		return panel;
	}
	
	public JTextField getTxtScore() {
		if (txtScore == null) {
			txtScore = new JTextField();
			txtScore.setEditable(false);
			txtScore.setText("Score: 0");
			txtScore.setHorizontalAlignment(SwingConstants.CENTER);
			txtScore.setFont(new Font("OCR A Extended", Font.PLAIN, 25));
			txtScore.setBounds(10, 0, 280, 51);
			txtScore.setColumns(10);
			txtScore.setBorder(null);
			txtScore.setOpaque(false);
		}
		return txtScore;
	}
	public JButton getBtnPauseAndResume() {
		if (btnPauseAndResume == null) {
			btnPauseAndResume = new JButton();
			btnPauseAndResume.setBounds(300, 0, 60, 60);
			btnPauseAndResume.setBorderPainted(false);
			btnPauseAndResume.setContentAreaFilled(false);
		}
		return btnPauseAndResume;
	}
	public JButton getBtnRestart() {
		if (btnRestart == null) {
			btnRestart = new JButton();
			btnRestart.setBounds(398, 0, 60, 60);
			btnRestart.setBorderPainted(false);
			btnRestart.setContentAreaFilled(false);
		}
		return btnRestart;
	}
}
