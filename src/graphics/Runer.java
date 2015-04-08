package graphics;

import graphics.bas.BasJMenuBar;
import graphics.bas.BasJPanel;
import graphics.bas.Tile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

import constant.CT;
import controler.MatrixBoard;

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
		setSize(new Dimension(820, 870));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	}
		
	private Runer() {
		super("Game");
		initStandartDemision();
		initJMEnu();
		initGrid();
	}

	public static void inInint(MatrixBoard temp, boolean state, String fileName) throws IOException, ClassNotFoundException
	{
		if (state)
		{
			FileInputStream fis = new FileInputStream(fileName);
		     ObjectInputStream ois = new ObjectInputStream(fis);
		     temp.initBoard( (MatrixBoard) ois.readObject());
		     ois.close();
		}
		else 
		 temp.initBoard(temp);
	}
	
	public static void  outInit(MatrixBoard temp, boolean state, String fileName) throws FileNotFoundException, IOException {
		if (state) 
		{
			ObjectOutputStream out = null;
	         out = new ObjectOutputStream(new FileOutputStream(fileName));
	         out.writeObject(temp);
	         out.close();
		}

	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//new Runer().setVisible(true);
		//MatrixBoard root = new MatrixBoard();
		//MatrixBoard temp = root;
		 
		String fileNamString = "xxx7k40.txt";
		MatrixBoard temp  = new MatrixBoard();
		boolean inFile = false;
		boolean outFile = true;
		
		inInint(temp,inFile,fileNamString);
		
			
	     
	     
	     System.out.println(temp);
	      
		for (int i = 0; i < 7000; i++) {
			while (temp.endGame()==0) {
				temp = temp.getNextTurn();
				//System.out.println(temp);
			}
			//System.out.println("------------");
			//System.out.println(temp);
			temp = temp.getRoot();
	
			
		}
		temp.showNextMove();
		System.out.println(temp);
         
		outInit(temp,outFile,fileNamString);
          System.out.println("END OF TIME");
          
          
	}

}
