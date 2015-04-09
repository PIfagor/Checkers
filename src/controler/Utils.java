package controler;

import constant.CT;

public class Utils {

	public static byte[][] arrayCopy(byte[][] inBoard) {
		int N = CT.SIZE_BOARD;
		byte[][] result = new byte[N][N];
		for (int i = 0; i < inBoard.length; i++)
			for (int j = 0; j < inBoard.length; j++)
				result[i][j] = inBoard[i][j];
		return result;
	}

	public static boolean arrayEquals(byte[][] first, byte[][] second) {

		if (first.length != second.length)
			return false;
		//Utils.showArray(first);
		//Utils.showArray(second);
		for (int i = 0; i < first.length; i++)
			for (int j = 0; j < first.length; j++)
				if (first[i][j] != second[i][j])
					return false;
		return true;
	}
	
	public static void showArray(byte[][] inBoard)
	{
		System.out.println("______________________");
		for (int i = 0; i < inBoard.length; i++)
		{
			for (int j = 0; j < inBoard.length; j++)
				System.out.print(inBoard[i][j]+" ");
			System.out.println();
		}
		System.out.println("----------------------");	
	}

}
