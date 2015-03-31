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

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import constant.CS;
import constant.CT;
import controler.MatrixBoard;

public class Tile  extends JPanel implements MouseListener {
	
	private BufferedImage white;
	private BufferedImage black;
	private static  MatrixBoard board;
	private int I;
	private int J;
	private int ID;
	
	private static int currentID=0;
	private static boolean check = false;
	private static int tempI;
	private static int tempJ;
	private static Color  lastColor;
	
	private boolean isCanMove(){
		return true;
	}
	
	
	private void loadImages()
	{
		try {                
			white = ImageIO.read(new File("resoursec/img/white.png"));
			black = ImageIO.read(new File("resoursec/img/black.png"));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	}
	
	
	public Tile () {
		
		this.setSize(new Dimension(100, 100));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		loadImages();
		this.ID=currentID++;
		board = new MatrixBoard();
		addMouseListener(this);
	}
	
	public Tile (int i,int j) {
		this();
		I=i;
		J=j;
		makeBlackAndWhite();
		
	}
	
		@Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if(board.BOARD()[I][J]==1)
	        	g.drawImage(white, 0, 0, null);
	        else if(board.BOARD()[I][J]==-1)
	        	g.drawImage(black, 0, 0, null);    
	        else if(board.BOARD()[I][J]==0)
	        	g.drawImage(null, 0, 0, null); 
	    }

		public  void makeBlackAndWhite()
		{
			if ((I+J)%2 != 0)
				this.setBackground(CS.BLACK);
			else 
				this.setBackground(CS.WHITE );
		}

		private int fXYtID(int i,int j)
		{
			return i*CT.SIZE_BOARD+j;
		}
		
		
		private void showPosibleMove()
		{
			int ai = I-1;
			int aj = J+1;
			
			int bi = I-1;
			int bj = J-1;
			
				if (aj<CT.SIZE_BOARD&&ai>0&&board.BOARD()[ai][aj]==-1){
					this.getParent().getComponents()[fXYtID(ai,aj)].setBackground(CS.MAROON);
					showPosibleMove( ai, aj);
				}
				
				if (bj<CT.SIZE_BOARD&&bi>0&&board.BOARD()[bi][bj]==-1){
					this.getParent().getComponents()[fXYtID(bi,bj)].setBackground(CS.MAROON);
					showPosibleMove( bi, bj);
				}
				
				if (ai<CT.SIZE_BOARD&&aj>0&&board.BOARD()[ai][aj]==0)
					this.getParent().getComponents()[fXYtID(ai,aj)].setBackground(CS.GOLD);
				if (bi<CT.SIZE_BOARD&&bj>0&&board.BOARD()[bi][bj]==0)//
					this.getParent().getComponents()[fXYtID(bi,bj)].setBackground(CS.GOLD);
			
			
		}
		private void showPosibleMove(int i, int j)
		{
			int ai = i-1;
			int aj = j+1;
			
			int bi = i-1;
			int bj = j-1;
			
			if (aj<CT.SIZE_BOARD&&ai>0&&board.BOARD()[ai][aj]==0)//
				this.getParent().getComponents()[fXYtID(ai,aj)].setBackground(CS.GOLD);
			if (bj<CT.SIZE_BOARD&&bi>0&&board.BOARD()[bi][bj]==0)//
				this.getParent().getComponents()[fXYtID(bi,bj)].setBackground(CS.GOLD);
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			if (!check)
			{
				check=true;
				tempI=I;
				tempJ=J;
				this.setBackground(CS.LIME);
				showPosibleMove();
				
				
			}else if (isCanMove())
			{
				check=false;
				int i = I;
				int j = J;
				int t = board.BOARD()[tempI][tempJ];
				int t2 =  board.BOARD()[i][j];
				board.BOARD()[I][J]= t;
				board.BOARD()[tempI][tempJ]=t2;
				((Tile) this.getParent().getComponents()[fXYtID(tempI,tempJ)]).makeBlackAndWhite();
				makeBlackAndWhite();
			}
			this.getParent().repaint();
			//re
		}


		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
//			if (!check)
//			{
//				check=true;
//				tempI=I;
//				tempJ=J;
//			}else if (isCanMove())
//			{
//				check=false;
//				int i = I;
//				int j = J;
//				board.BOARD()[I][J]= board.BOARD()[tempI][tempJ];
//				board.BOARD()[tempI][tempJ]= board.BOARD()[i][j];
//			}
//			repaint();
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
