package graphics;

import graphics.bas.BasJMenuBar;
import graphics.bas.BasJPanel;
import graphics.bas.Tile;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
//			   ObjectOutputStream out = null;
//	           ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	           out = new ObjectOutputStream(bos);   
//	           out.writeObject(temp);
//	           byte[] data = bos.toByteArray();
//	           BufferedOutputStream boss = new BufferedOutputStream(new FileOutputStream(fileName),1024);
//	           boss.write(data);
//	           out.close();
//	           boss.close();
		}

	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//new Runer().setVisible(true);
		//MatrixBoard root = new MatrixBoard();
		//MatrixBoard temp = root;
		 
		String fileNamString = "zzz10k.txt";
		MatrixBoard temp  = new MatrixBoard();
		boolean inFile = false;
		boolean outFile = false;
		
		//inInint(temp,inFile,fileNamString);
		
		long start = System.nanoTime();
		temp = temp.readFromeFile(temp,
	                new DataInputStream(
	                new FileInputStream(fileNamString)));
		temp.initBoard(temp);
		outInit(temp,outFile,fileNamString);
		
		long end = System.nanoTime();
		long time =  (end -start)/1000000; 
        System.out.println("END OF READ: "+time);
			
	     
	     
	     System.out.println(temp);
	      
		for (int i = 0; i < 10000; i++) {
			while (temp.endGame()==0) {
				temp = temp.getNextTurn();
				//System.out.println(temp);
			}
			//System.out.println("------------");
			//System.out.println(temp);
			temp = temp.getRoot();
	
			
		}
		System.out.println("______________________________________________");
		System.out.println(temp);
		temp.showNextMove();
		 start = System.nanoTime();
		 temp.writeToFile(temp,
	                new DataOutputStream(
	                new FileOutputStream(fileNamString)));
		 outInit(temp,outFile,fileNamString);
		 end = System.nanoTime();
		 time =  (end -start)/1000; 
          System.out.println("END OF TIME: "+time);
          
          
	}

}
