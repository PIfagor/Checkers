package controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import constant.CT;

public class MatrixBoard {

	private int[][] board;

	private int countGames;
	private int countWins;
	private int countLoses;
	private int amountWhites;
	private int amountBlacks;
	private boolean white = true;
	private boolean terminal = false;
	// private boolean kings;
	private int countMoves;
	private Random rand = new Random();
	private ArrayList<MatrixBoard> nextMoves;

	public MatrixBoard(int[][] inBoard) {
		board = arrayCopy(inBoard);
		// startBoardEight();
	}

	public MatrixBoard() {
		int N = CT.SIZE_BOARD;

		board = new int[N][N];

		N /= 2;
		amountWhites = N * (N - 2);
		amountBlacks = amountWhites;
		
		startBoardEight();
	}

	private void startBoardEight() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (i < 3 && (i + j) % 2 != 0)
					board[i][j] = -1;
				if (i > 4 && (i + j) % 2 != 0)
					board[i][j] = 1;
			}
		}
	}

	private void makeNextMoves() {

		nextMoves = new ArrayList<>();
		if (white == true) {
			// check enemies
			boolean shouldBeat = false;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j] == 1) {
						int ai = i - 1;
						int aj = j - 1;
						if (inBounds(ai, aj))
							if (board[ai][aj] < 0) {
								if (inBounds(ai - 1, aj - 1))
									if (board[ai - 1][aj - 1] == 0) {
										shouldBeat = true;
										nextMoves.add(new MatrixBoard(beatIJ(i,
												j, ai, aj, ai - 1, aj - 1)));
									}
							}

						int bi = i - 1;
						int bj = j + 1;
						if (inBounds(bi, bj))
							if (board[bi][bj] < 0) {
								if (inBounds(bi - 1, bj + 1))
									if (board[bi - 1][bj + 1] == 0) {
										shouldBeat = true;
										nextMoves.add(new MatrixBoard(beatIJ(i,
												j, bi, bj, bi - 1, bj + 1)));
									}
							}

					}
				}
			}

			if (!shouldBeat) {
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board.length; j++) {
						if (board[i][j] == 1) {
							int ai = i - 1;
							int aj = j - 1;
							if (inBounds(ai, aj))
								if (board[ai][aj] == 0) {
									nextMoves.add(new MatrixBoard(moveIJ(i, j,
											ai, aj)));

								}

							int bi = i - 1;
							int bj = j + 1;
							if (inBounds(bi, bj))
								if (board[bi][bj] == 0) {
									nextMoves.add(new MatrixBoard(moveIJ(i, j,
											bi, bj)));
								}

						}
					}
				}
				white = false;
			}

		} else {
			// check enemies
			boolean shouldBeat = false;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j] == -1) {
						int ci = i + 1;
						int cj = j - 1;
						if (inBounds(ci, cj))
							if (board[ci][cj] > 0) {
								if (inBounds(ci + 1, cj - 1))
									if (board[ci + 1][cj - 1] == 0) {
										shouldBeat = true;
										nextMoves.add(new MatrixBoard(beatIJ(i,
												j, ci, cj, ci + 1, cj - 1)));
									}
							}

						int di = i + 1;
						int dj = j + 1;
						if (inBounds(di, dj))
							if (board[di][dj] > 0) {
								if (inBounds(di + 1, dj + 1))
									if (board[di + 1][dj + 1] == 0) {
										shouldBeat = true;
										nextMoves.add(new MatrixBoard(beatIJ(i,
												j, di, dj, di + 1, dj + 1)));
									}
							}

					}
				}
			}

			if (!shouldBeat) {
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board.length; j++) {
						if (board[i][j] == -1) {
							int ci = i + 1;
							int cj = j - 1;
							if (inBounds(ci, cj))
								if (board[ci][cj] == 0) {
									nextMoves.add(new MatrixBoard(moveIJ(i, j,
											ci, cj)));

								}

							int di = i + 1;
							int dj = j + 1;
							if (inBounds(di, dj))
								if (board[di][dj] == 0) {
									nextMoves.add(new MatrixBoard(moveIJ(i, j,
											di, dj)));
								}

						}
					}
				}
				white = true;
			}

		}

	}

	public int[][] getNextMove() {
		if (nextMoves == null)
			makeNextMoves();
		return nextMoves.get(getBestMove()).BOARD();

	}

	private int getBestMove() {

		if (nextMoves != null) {

		}
		return getRandInt();
	}

	private int getRandInt() {
		int randInt = rand.nextInt(nextMoves.size());
		return randInt;
	}

	// public void initWhiteRoot()
	// {
	// white = true;
	// nextMoves.add(new)
	// }

	private int[][] moveIJ(int fi, int fj, int ti, int tj) {
		int[][] res = arrayCopy(board);
		int temp = res[fi][fj];
		res[fi][fj] = res[ti][tj];

		// make king
		if (temp == 1 && ti == 0)
			res[ti][tj] = 2;
		else if (temp == -1 && ti == CT.SIZE_BOARD)
			res[ti][tj] = -2;
		else
			res[ti][tj] = temp;

		return res;
	}

	private int[][] beatIJ(int fi, int fj, int mi, int mj, int ti, int tj) {
		int[][] res = arrayCopy(board);
		int temp = res[fi][fj];
		res[fi][fj] = res[ti][tj];

		// make king
		if (temp == 1 && ti == 0)
			res[ti][tj] = 2;
		else if (temp == -1 && ti == CT.SIZE_BOARD)
			res[ti][tj] = -2;
		else
			res[ti][tj] = temp;

		res[mi][mj] = 0;

		return res;
	}

	public int[][] BOARD() {
		return board;
	}

	private boolean inBounds(int i, int j) {
		if (i < 0 || j < 0 || i >= CT.SIZE_BOARD || j >= CT.SIZE_BOARD)
			return false;
		return true;
	}

	private int[][] arrayCopy(int[][] inBoard) {
		int N = CT.SIZE_BOARD;
		int[][] result = new int[N][N];
		for (int i = 0; i < inBoard.length; i++)
			for (int j = 0; j < inBoard.length; j++)
				result[i][j] = inBoard[i][j];
		return result;
	}

	public void showNextMove() {
		for (MatrixBoard mb : nextMoves) {
			System.out.println(mb);
		}
	}

	@Override
	public String toString() {
		String ss = "Move#" + countMoves;
		ss += "\nG: " + countGames;
		ss += "\nW: " + countWins;
		ss += "\nL: " + countLoses;
		ss += "\n";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				ss += board[i][j] + " ";
			}
			ss += "\n";
		}
		return ss;
	}

}
