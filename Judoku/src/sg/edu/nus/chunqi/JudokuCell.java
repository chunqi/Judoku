package sg.edu.nus.chunqi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * 
 * @author Zhu Chunqi
 * @since 2015-04-27
 * @version 0.1
 */
public class JudokuCell extends JPanel {
	private static final long serialVersionUID = 5996354760185434010L;
	
	/*
	 * Keeps track of which instance this is to draw the proper borders
	 */
	private static int NUM_INSTANCE = 0;
	
	/*
	 * Constants for drawing cells at different sizes
	 */
	protected static final int	SMALL_CELL_SIZE	= 50;
	protected static final int	LARGE_CELL_SIZE = 100;
	
	/*
	 * Common styles for all cells
	 */
	private static final int FONT_SIZE					= 24;
	private static final Border BORDER_DEFAULT			= BorderFactory.createLineBorder(Color.BLACK);
	private static final Border BORDER_BOTTOM			= BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK);
	private static final Border BORDER_RIGHT			= BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK);
	private static final Border BORDER_CORNER			= BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK);
	private static final Color COLOR_NO_CONFLICT		= Color.BLACK;
	private static final Color COLOR_CONFLICT			= Color.RED;
	private static final Color COLOR_BG_ACTIVE_INPUT	= Color.YELLOW;
	private static final Color COLOR_BG_NORMAL			= Color.WHITE;
	
	/*
	 * Member variables
	 */
	private JLabel m_label = new JLabel();
	
	/*
	 * Cell states
	 */
	private int m_number = 1;
	private boolean m_isFixed;
	
	/**
	 * Constructor styles the JPanel and adds the label
	 */
	public JudokuCell(int number, boolean isFixed) {
		m_number = number;
		m_isFixed = isFixed;
		_styleLabel();
		_updateLabel();
		
		super.setLayout(new GridBagLayout());
		super.setPreferredSize(new Dimension(SMALL_CELL_SIZE, SMALL_CELL_SIZE));
		super.setBorder(_generateBorders());
		super.setBackground(COLOR_BG_NORMAL);
		super.add(m_label);
		
		NUM_INSTANCE++;
	}
	
	public boolean isFixed() {
		return m_isFixed;
	}
	
	public void setIsFixed(boolean isFixed) {
		m_isFixed = isFixed;
		_styleLabel();
	}
	
	public int getNumber() {
		return m_number;
	}
	
	public void setNumber(int number) {
		m_number = number;
		_updateLabel();
	}
	
	public void markConflict() {
		m_label.setForeground(COLOR_CONFLICT);
	}
	
	public void unmarkConflict() {
		m_label.setForeground(COLOR_NO_CONFLICT);
	}
	
	public void markActiveInput() {
		super.setBackground(COLOR_BG_ACTIVE_INPUT);
	}
	
	public void unmarkActiveInput() {
		super.setBackground(COLOR_BG_NORMAL);
	}
	
	/*
	 * Private methods
	 */
	
	private Border _generateBorders() {
		int row = NUM_INSTANCE / 9;
		int col = NUM_INSTANCE % 9;
		if((row == 2 || row == 5) && (col == 2 || col == 5)) {
			return new CompoundBorder(BORDER_DEFAULT, BORDER_CORNER);
		}
		else if(row == 2 || row == 5) {
			return new CompoundBorder(BORDER_DEFAULT, BORDER_BOTTOM);
		}
		else if(col == 2 || col == 5) {
			return new CompoundBorder(BORDER_DEFAULT, BORDER_RIGHT);
		}
		else return BORDER_DEFAULT;
	}
	
	private void _styleLabel() {
		if(m_isFixed == JudokuGame.FIXED_CELL) {
			m_label.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		}
		else {
			m_label.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
		}
	}
	
	private void _updateLabel() {
		if(m_number == 0) m_label.setText("");
		else m_label.setText(m_number + "");
	}
}
