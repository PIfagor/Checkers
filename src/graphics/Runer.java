package graphics;

import graphics.bas.BasJMenuBar;
import graphics.bas.BasJPanel;
import graphics.bas.Tile;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import constant.CT;

public class Runer extends JFrame {
	private BasJMenuBar mb;
	private BasJPanel pn;
	
	private void initJMEnu() {
		mb = new BasJMenuBar();
		JMenu game = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem restart = new JMenuItem("Restart");
		game.add(newGame);
		game.add(restart);
		mb.add(game);
		JMenu info = new JMenu("Info");
		mb.add(info);
		this.add(mb,BorderLayout.NORTH);
	}
	
	private void initGrid()
	{
		pn = new BasJPanel();
		
		for (int i = 0; i < CT.SIZE_BOARD; i++) {
			for (int j = 0; j < CT.SIZE_BOARD; j++) {
				pn.add(new Tile(i,j));
			}
		}
		this.add(pn);
	};
	
	private void initStandartDemision()
	{
		setSize(new Dimension(800, 880));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	}
		
	private Runer() {
		super("Game");
		initStandartDemision();
		initJMEnu();
		initGrid();
	}

	public static void main(String[] args) {
		new Runer().setVisible(true);

	}

}
