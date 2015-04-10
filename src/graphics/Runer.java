package graphics;

import graphics.bas.BasJMenuBar;
import graphics.bas.BasJPanel;
import graphics.bas.Tile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.swing.*;

import constant.CT;
import controler.Learning;
import controler.MatrixBoard;

public class Runer extends JFrame {
	private BasJMenuBar mb;
	private BasJPanel pn;
	private JMenuItem newGame;
	private JMenuItem restart;
	private JMenuItem endGame;
	private JMenuItem exitGame;

	private MatrixBoard mainBoard;

	private void initJMEnu() {
		mb = new BasJMenuBar();
		JMenu game = new JMenu("Game");
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(new MenuItemListener());
		restart = new JMenuItem("Restart");
		restart.addActionListener(new MenuItemListener());
		endGame = new JMenuItem("End Game");
		endGame.addActionListener(new MenuItemListener());
		exitGame = new JMenuItem("Save & Exit Game");
		exitGame.addActionListener(new MenuItemListener());
		game.add(newGame);
		game.add(restart);
		game.add(endGame);
		game.add(exitGame);
		mb.add(game);
		JMenu info = new JMenu("Info");
		mb.add(info);
		JMenuBar jn = new JMenuBar();

		this.add(mb, BorderLayout.NORTH);
	}

	private void initGrid() {
		pn = new BasJPanel();

		for (int i = 0; i < CT.SIZE_BOARD; i++) {
			for (int j = 0; j < CT.SIZE_BOARD; j++) {
				pn.add(new Tile(i, j));
			}
		}
		this.add(pn);
	};

	private void initStandartDemision() {
		setSize(new Dimension(820, 870));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private Runer() {
		super("Game");
		initStandartDemision();
		initJMEnu();
		// gamePreparation ();
		// initGrid();
		// addMouseListener(this);
	}

	public class MenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newGame) {
				// JOptionPane.showMessageDialog(null, "Selected Item: " +
				// e.getActionCommand());

				gamePreparation();
				initGrid();

			}
			if (e.getSource() == endGame) {
				savingDataAfterAllGames();
				// initGrid();
			}
			if (e.getSource() == restart) {
				// JOptionPane.showMessageDialog(null, "Selected Item: " +
				// e.getActionCommand());
				nextGame();
				initGrid();

			}
			if (e.getSource() == exitGame) {
				JOptionPane.showMessageDialog(null,
						"Selected Item: " + e.getActionCommand());
			}
			pn.revalidate();
		}

	}

	private void gamePreparation() {
		try {
			mainBoard = Learning.ReadTreeFromStorage(true, CT.mainFileName);
			// mainBoard = new MatrixBoard();

			new Tile().initTileFirst(mainBoard);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void nextGame() {
		mainBoard.endGame();
		mainBoard = mainBoard.getRoot();
		System.out.println(mainBoard);
		new Tile().initTile(mainBoard);
	}

	private void savingDataAfterAllGames() {
		try {
			mainBoard.writeToFile(mainBoard, new DataOutputStream(
					new FileOutputStream(CT.mainFileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		if (!CT.LEARNING) {
			new Runer().setVisible(true);
		} else {
			Learning.learn();
		}

	}

}
