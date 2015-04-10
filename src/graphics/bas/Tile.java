package graphics.bas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import constant.CS;
import constant.CT;
import constant.Side;
import controler.MatrixBoard;
import controler.Utils;

public class Tile extends JPanel implements MouseListener {

	private BufferedImage white;
	private BufferedImage black;
	private BufferedImage whiteKing;
	private BufferedImage blackKing;
	private BufferedImage possMove;
	private BufferedImage enemyTile;
	private BufferedImage currentTile;
	private static MatrixBoard game;
	private static byte[][] board;
	private int I;
	private int J;
	private int ID;

	private static int currentID = 0;
	private static boolean check = false;
	private static int tempI;
	private static int tempJ;
	private static Color lastColor;

	public Tile() {

		this.setSize(new Dimension(100, 100));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		loadImages();
		this.ID = currentID++;
		

		addMouseListener(this);
	}
	
	public void initTileFirst (MatrixBoard mb) {
		game = mb;
		game.initBoard(game);
		board = Utils.arrayCopy(game.BOARD());
	}
	
	public void initTile(MatrixBoard mb)
	{
		game = mb;
		
		board = Utils.arrayCopy(game.BOARD());
	}
	
	public Tile(int i, int j) {
		this();
		I = i;
		J = j;
		makeBlackAndWhite();

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (board[I][J] == 1)
			g.drawImage(white, 0, 0, this.getWidth(), this.getHeight(), this);
		else if (board[I][J] == -1)
			g.drawImage(black, 0, 0, this.getWidth(), this.getHeight(), this);
		if (board[I][J] == 2)
			g.drawImage(whiteKing, 0, 0, this.getWidth(), this.getHeight(),
					this);
		else if (board[I][J] == -2)
			g.drawImage(blackKing, 0, 0, this.getWidth(), this.getHeight(),
					this);
		else if (board[I][J] == 0)
			g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);

	}

	public void makeBlackAndWhite() {
		if ((I + J) % 2 != 0)
			this.setBackground(CS.BLACK);
		else
			this.setBackground(CS.WHITE);
	}

	private int fXYtID(int i, int j) {
		return i * CT.SIZE_BOARD + j;
	}

	private void showPosibleMove() {
		int ai = I - 1;
		int aj = J + 1;

		int bi = I - 1;
		int bj = J - 1;

		if (aj < CT.SIZE_BOARD && ai > 0 && board[ai][aj] == -1) {
			this.getParent().getComponents()[fXYtID(ai, aj)]
					.setBackground(CS.MAROON);
			showPosibleMove(ai, aj, Side.UPRIGTH);
		}

		if (bj < CT.SIZE_BOARD && bi > 0 && board[bi][bj] == -1) {
			this.getParent().getComponents()[fXYtID(bi, bj)]
					.setBackground(CS.MAROON);
			showPosibleMove(bi, bj, Side.UPLEFT);
		}

		if (ai < CT.SIZE_BOARD && aj > 0 && board[ai][aj] == 0)
			this.getParent().getComponents()[fXYtID(ai, aj)]
					.setBackground(CS.GOLD);
		if (bi < CT.SIZE_BOARD && bj > 0 && board[bi][bj] == 0)//
			this.getParent().getComponents()[fXYtID(bi, bj)]
					.setBackground(CS.GREY);

	}

	private void showPosibleMove(int i, int j, Side s) {
		if (s == Side.UPRIGTH) {
			int ai = i - 1;
			int aj = j + 1;

			if (aj < CT.SIZE_BOARD && ai > 0 && board[ai][aj] == 0)//
				this.getParent().getComponents()[fXYtID(ai, aj)]
						.setBackground(CS.GOLD);
		} else if (s == Side.UPLEFT) {
			int bi = i - 1;
			int bj = j - 1;

			if (bj < CT.SIZE_BOARD && bi > 0 && board[bi][bj] == 0)//
				this.getParent().getComponents()[fXYtID(bi, bj)]
						.setBackground(CS.GOLD);
		}

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		// System.out.println(game.getNextMove());

		// System.out.println();
		// game.showNextMove();
		boolean makeMove = false;
		if (board[I][J] != 0 || check) {
			if (!check) {
				check = true;
				tempI = I;
				tempJ = J;
				// this.setBackground(CS.LIME);
				// this.drawImage(blackKing, 0, 0, null);
				// showPosibleMove();

			} else {
				check = false;
				byte[][] temp = Utils.arrayCopy(board);
				int i = I;
				int j = J;
				byte t = temp[tempI][tempJ];
				byte t2 = temp[i][j];
				// neck for attack enemies
				if ((tempI - I) % 2 == 0 && (tempJ - J) % 2 == 0) {
					int ix = (tempI + I) / 2;
					int jx = (tempJ + J) / 2;
					//System.out.println("i:" + ix + " j:" + jx);
					temp[ix][jx] = 0;
				}
				// neck for kings
				if (I == 0 && t == 1)
					t = 2;
				if (I == CT.SIZE_BOARD - 1 && t == -1)
					t = -2;
				temp[I][J] = t;
				temp[tempI][tempJ] = t2;

				((Tile) this.getParent().getComponents()[fXYtID(tempI, tempJ)])
						.makeBlackAndWhite();
				makeBlackAndWhite();

				if (isCanMove(temp)) {
					makeMove = true;
					this.getParent().repaint();
				}

			}
			if (makeMove && game.endGame() == 0) {
				MatrixBoard temp = game.getNextTurn();
				if (temp != null) {
					game = temp;
					board = game.BOARD();
				}

			}
			System.out.println("********************");
			game.showNextMove();
			System.out.println("********************");
			this.getParent().repaint();
		}

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// while(play())
		// {
		// try {
		// Thread.sleep(200);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// }
		// }).run();

	}

	private boolean isCanMove(byte[][] board) {
		game.callMakeNextTurns();
		if (game.isEnemyHit()||game.endGame()!=0)
			return true;
		ArrayList<MatrixBoard> nt = game.getNextMoves();
		// System.out.println("&&&&&&&&&&&&&&");
		// Utils.showArray(board);
		// System.out.println("&&&&&&&&&&&&&&");
		for (int i = 0; i < nt.size(); i++) {
			// Utils.showArray(nt.get(i).BOARD());
			if (Utils.arrayEquals(board, nt.get(i).BOARD())) {

				//Utils.showArray(nt.get(i).BOARD());
				// this.board = Utils.arrayCopy();
				this.game = game.getUserMove(i);
				this.board = game.BOARD();

				return true;
			}

		}
		return false;
	}

	private void loadImages() {
		try {
			white = ImageIO.read(new File("resoursec/img/white.png"));
			black = ImageIO.read(new File("resoursec/img/black.png"));
			whiteKing = ImageIO.read(new File("resoursec/img/whiteKing.png"));
			blackKing = ImageIO.read(new File("resoursec/img/blackKing.png"));
			possMove = ImageIO.read(new File("resoursec/img/poss_move.png"));
			enemyTile = ImageIO.read(new File("resoursec/img/enemy.png"));
			currentTile = ImageIO.read(new File("resoursec/img/me.png"));

		} catch (IOException ex) {
			// handle exception...
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// if (!check)
		// {
		// check=true;
		// tempI=I;
		// tempJ=J;
		// }else if (isCanMove())
		// {
		// check=false;
		// int i = I;
		// int j = J;
		// board[I][J]= board[tempI][tempJ];
		// board[tempI][tempJ]= board[i][j];
		// }
		// repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
