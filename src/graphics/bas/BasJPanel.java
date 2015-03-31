package graphics.bas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import constant.CT;

public class BasJPanel extends JPanel {
	public BasJPanel () {
		this.setSize(new Dimension(800, 800));
		this.setBackground(new Color(51, 153, 255));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(CT.SIZE_BOARD,CT.SIZE_BOARD));
		
	}


	
	

}
