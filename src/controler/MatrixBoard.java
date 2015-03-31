package controler;

import constant.CT;

public class MatrixBoard {

	int [] [] board ;
	
	public MatrixBoard ()
	{
		int N =CT.SIZE_BOARD;
		board = new int [N][N];
		startBoardEight();
	}
	private void startBoardEight()
	{
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(i<3 && (i+j)%2 != 0)
				 board[i][j] = -1;
				if(i>4 && (i+j)%2 != 0)
				 board[i][j] = 1;
			}
		}
	}
	
	public int [][] BOARD ()
	{
		return board;
	}
	
	
	
}
