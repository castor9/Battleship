import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class TButton extends JButton {
	String name;
	char status;
	int row, column;
	boolean isHuman;
	int priority;
	
	public TButton(String name, boolean humanBool) {
		this.name = name;
		isHuman = humanBool;
		status = 'E';
	}
	public TButton(String text) {
		setText(text);
		setPriority(1);
		isHuman = false;
		status = 'E';
	}

	protected void paintComponent(Graphics g) {
		int x = (int)getSize().getWidth();
		int y = (int)getSize().getHeight();

		if (status == 'E') {
			super.paintComponent(g);
		} else if (status == 'M') {
			super.paintComponent(g);
			// BLACK CIRCLE
			g.setColor(Color.black);
			g.drawOval(x/4, y/4, x/2, y/2);
			g.fillOval(x/4, y/4, x/2, y/2);
			// WHITE CIRCLE
			g.setColor(Color.white);
			g.drawOval(x/4 + 2, y/4 + 2, x/2 - 6, y/2 - 6);
			g.fillOval(x/4 + 2, y/4 + 2, x/2 - 6, y/2 - 6);
		} else if (status == 'B') {
			super.paintComponent(g);
			// BLACK CIRCLE
			g.setColor(Color.black);
			g.fillOval(x/4, y/4, x/2, y/2);
			// BLUE CIRCLE
			g.setColor(Color.blue);
			g.drawOval(x/4 + 2, y/4 + 2, x/2 - 6, y/2 - 6);
			g.fillOval(x/4 + 2, y/4 + 2, x/2 - 6, y/2 - 6);
		} else if (status == 'H' && isHuman == false) {
			super.paintComponent(g);
			// BLACK CIRCLE
			Color black = new Color (0,0,0);
			g.setColor(black);
			g.fillOval(x/4,y/4,x/2,y/2);
			// BLUE CIRCLE
			g.setColor(Color.blue);
			g.drawOval(x/4 + 2, y/4 + 2, x/2 - 6, y/2 - 6);
			g.fillOval(x/4 + 2, y/4 + 2, x/2 - 6, y/2 - 6);
			// LITTLE BLACK CIRCLE
			g.setColor(Color.black);
			g.drawOval(x/4 + 6, y/4 + 6, x/2 - 12, y/2 - 12);
			g.fillOval(x/4 + 6, y/4 + 6, x/2 - 12, y/2 - 12);
			//LITTLE RED CIRCLE
			g.setColor(Color.red);
			g.drawOval(x/4 + 8, y/4 + 8, x/2 - 16, y/2 - 16);
			g.fillOval(x/4 + 8, y/4 + 8, x/2 - 16, y/2 - 16);
		} else if (status == 'H' && isHuman == true) {
			super.paintComponent(g);
			// BLACK CIRCLE
			g.setColor(Color.black);
			g.drawOval(x/4, y/4, x/2, y/2);
			g.fillOval(x/4, y/4, x/2, y/2);
			// RED CIRCLE
			g.setColor(Color.red);
			g.drawOval(x/4 + 2, y/4 + 2, x/2 - 6, y/2 - 6);
			g.fillOval(x/4 + 2, y/4 + 2, x/2 - 6, y/2 - 6);
		}
	}
	public void status(char tStatus) { status = tStatus;}
	public char getStatus() { return status; }
	public void row(int row) { this.row = row; }
	public void column(int column) { this.column = column; }
	public int getRow() { return row; }
	public int getColumn() { return column; }
	////// PRIORITY ///////
	public void setPriority(int priority) { 
		this.priority = priority; 
		setText("" + this.priority);
	}
	public int getPriority() { return priority; }
	public int getPriority(int row, int col) { return priority; }
	//public void setName(String name) { this.name = name; }
}
