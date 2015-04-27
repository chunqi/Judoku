package sg.edu.nus.chunqi;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Judoku extends JFrame implements ActionListener, MouseListener, KeyListener {
	private static final long serialVersionUID = 7774126205706579152L;
	
	/*
	 * Enumerated commands
	 */
	private static final String ACTION_NEW_GAME	= "New Game";
	private static final String ACTION_QUIT		= "Quit";
	
	/*
	 * Member variables
	 */
	private JudokuGame m_game;
	
	/**
	 * Constructor initializes the window and the 9x9 JudokuCells then starts a new game on easy mode
	 * TODO: Implement difficulties
	 */
	public Judoku() {
		//Hook up close button to exit the app
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Set app title
		super.setTitle("Judoku");
		
		//Add menu bar
		super.setJMenuBar(_createJMenuBar());
		
		//Grid layout
		super.setLayout(new GridLayout(JudokuGame.BOARD_SIZE, JudokuGame.BOARD_SIZE));
		super.setResizable(false);
		
		//Initialize the game
		m_game = new JudokuGame(this);
		
		//Register self as KeyEvent listener
		super.addKeyListener(this);
		
		//Pack components and show
		super.pack();
		super.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Judoku();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case ACTION_QUIT:
				System.exit(0);
				break;
			case ACTION_NEW_GAME:
				m_game.restart();
				break;
			default:
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() instanceof JudokuCell) {
			JudokuCell cell = (JudokuCell) e.getSource();
			m_game.setActiveInputCell(cell);
		}
		super.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			m_game.clearActiveInputCell();
		}
		else if(e.getKeyCode() >= KeyEvent.VK_1
				&& e.getKeyCode() <= KeyEvent.VK_9) {
			m_game.setActiveInputCellNumber(e.getKeyCode() - '0');
		}
		else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			m_game.setActiveInputCellNumber(0);
		}
		super.repaint();
	}
	
	/*
	 * Private methods
	 */
	private JMenuBar _createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		//Main app menu {New Game, Quit}
		JMenu menuJudoku = new JMenu("Judoku");
		
		JMenuItem menuJudokuNewGame = new JMenuItem(ACTION_NEW_GAME);
		menuJudokuNewGame.addActionListener(this);
		
		JMenuItem menuJudokuQuit = new JMenuItem(ACTION_QUIT);
		menuJudokuQuit.addActionListener(this);
		
		menuJudoku.add(menuJudokuNewGame);
		menuJudoku.add(menuJudokuQuit);
		
		//Game difficulty menu {Easy, Medium, Hard}
		JMenu menuDifficulty = new JMenu("Change Difficulty");
		ButtonGroup difficultyGroup = new ButtonGroup();
		JRadioButtonMenuItem menuDifficultyEasy = new JRadioButtonMenuItem("Easy");
		JRadioButtonMenuItem menuDifficultyMedium = new JRadioButtonMenuItem("Medium");
		JRadioButtonMenuItem menuDifficultyHard = new JRadioButtonMenuItem("Hard");
		difficultyGroup.add(menuDifficultyEasy);
		difficultyGroup.add(menuDifficultyMedium);
		difficultyGroup.add(menuDifficultyHard);
		menuDifficulty.add(menuDifficultyEasy);
		menuDifficulty.add(menuDifficultyMedium);
		menuDifficulty.add(menuDifficultyHard);
		
		menuBar.add(menuJudoku);
		menuBar.add(menuDifficulty);
		
		return menuBar;
	}
	
	/*
	 * Unused MouseEvent handlers
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	/*
	 * Unused KeyEvent handlers
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
