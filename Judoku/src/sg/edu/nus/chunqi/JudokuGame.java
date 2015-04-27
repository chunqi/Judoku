package sg.edu.nus.chunqi;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class JudokuGame {
	/*
	 * Seed game
	 */
	protected static final int[][] SEED_GAME = {
		{5,3,4,6,7,8,9,1,2},
		{6,7,2,1,9,5,3,4,8},
		{1,9,8,3,4,2,5,6,7},
		{8,5,9,7,6,1,4,2,3},
		{4,2,6,8,5,3,7,9,1},
		{7,1,3,9,2,4,8,5,6},
		{9,6,1,5,3,7,2,8,4},
		{2,8,7,4,1,9,6,3,5},
		{3,4,5,2,8,6,1,7,9}
	};
	
	/*
	 * Enumerated constants
	 */
	protected static final int BOARD_SIZE = 9;
	protected static final boolean FIXED_CELL = true;
	protected static final boolean VAR_CELL = false;
	
	/*
	 * Member variables
	 */
	private int[][] m_puzzle;
	private boolean[][] m_puzzleMask;
	private JudokuCell[][] m_panels = new JudokuCell[BOARD_SIZE][BOARD_SIZE];
	private JudokuCell m_activeInputCell = null;
	private JFrame m_container;
	
	public JudokuGame(JFrame container) {
		m_container = container;
		
		restart();
	}
	
	public void restart() {
		_generatePuzzle();
		_setPuzzle();
	}
	
	public void markConflicts() {
		for(int x = 0; x < BOARD_SIZE; x++) {
			for(int y = 0; y < BOARD_SIZE; y++) m_panels[x][y].unmarkConflict();
		}
		for(int x = 0; x < BOARD_SIZE; x++) {
			_markConflicts(_getRow(x));
			_markConflicts(_getCol(x));
			_markConflicts(_getQuad(x));
		}
	}
	
	public void setActiveInputCellNumber(int number) {
		if(m_activeInputCell != null) {
			m_activeInputCell.setNumber(number);
			clearActiveInputCell();
			markConflicts();
		}
	}
	
	public void clearActiveInputCell() {
		setActiveInputCell(null);
	}
	
	public void setActiveInputCell(JudokuCell activeInputCell) {
		if(m_activeInputCell != null) m_activeInputCell.unmarkActiveInput();
		m_activeInputCell = activeInputCell;
		if(m_activeInputCell != null) {
			if(!m_activeInputCell.isFixed()) m_activeInputCell.markActiveInput();
			else m_activeInputCell = null;
		}
	}
	
	/*
	 * Private methods
	 */
	
	private void _setPuzzle() {
		for(int x = 0; x < BOARD_SIZE; x++) {
			for(int y = 0; y < BOARD_SIZE; y++) {
				if(m_panels[x][y] == null) {
					m_panels[x][y] = new JudokuCell(m_puzzle[x][y], m_puzzleMask[x][y]);
					m_panels[x][y].addMouseListener((MouseListener) m_container);
					m_container.add(m_panels[x][y]);
				}
				else {
					m_panels[x][y].setNumber(m_puzzle[x][y]);
					m_panels[x][y].setIsFixed(m_puzzleMask[x][y]);
				}
			}
		}
	}
	
	private void _generatePuzzle() {
		m_puzzle = new int[BOARD_SIZE][BOARD_SIZE];
		for(int x = 0; x < BOARD_SIZE; x++) {
			System.arraycopy(SEED_GAME[x], 0, m_puzzle[x], 0, BOARD_SIZE);
		}
		
		Random random = new Random();
		m_puzzleMask = new boolean[BOARD_SIZE][BOARD_SIZE];
		for(int x = 0; x < BOARD_SIZE; x++) {
			int nineBitInt = random.nextInt(511);
			for(int y = 0; y < BOARD_SIZE; y++) {
				boolean bit = nineBitInt % 2 == 1;
				m_puzzleMask[x][y] = bit;
				if(bit == VAR_CELL) m_puzzle[x][y] = 0;
				nineBitInt >>= 1;
			}
		}
	}
	
	private void _transformPuzzle() {
		
	}
	
	private void _transpose() {
		for(int x = 0; x < BOARD_SIZE; x++) {
			for(int y = 0; y < BOARD_SIZE; y++) {
				
			}
		}
	}
	
	private ArrayList<JudokuCell> _getRow(int rowIndex) {
		ArrayList<JudokuCell> row = new ArrayList<JudokuCell>();
		for(int x = 0; x < BOARD_SIZE; x++) row.add(m_panels[rowIndex][x]);
		return row;
	}
	
	private ArrayList<JudokuCell> _getCol(int colIndex) {
		ArrayList<JudokuCell> col = new ArrayList<JudokuCell>();
		for(int x = 0; x < BOARD_SIZE; x++) col.add(m_panels[x][colIndex]);
		return col;
	}
	
	private ArrayList<JudokuCell> _getQuad(int quadIndex) {
		ArrayList<JudokuCell> quad = new ArrayList<JudokuCell>();
		int rowOffset = quadIndex / 3 * 3;
		int colOffset = quadIndex % 3 * 3;
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				quad.add(m_panels[rowOffset + x][colOffset + y]);
			}
		}
		return quad;
	}
	
	private void _markConflicts(ArrayList<JudokuCell> piece) {
		int[] count = new int[BOARD_SIZE + 1];
		for(int x = 0; x < BOARD_SIZE; x++) count[piece.get(x).getNumber()]++;
		for(int x = 0; x < BOARD_SIZE; x++) {
			if(piece.get(x).getNumber() != 0 && count[piece.get(x).getNumber()] > 1) {
				piece.get(x).markConflict();
			}
		}
	}
}
