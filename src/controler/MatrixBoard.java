package controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import constant.CT;

public class MatrixBoard {

	private int[][] board;

	private int countGames;
	private int countWins;
	private int countLoses;

	private static int amountWhites;
	private static int amountBlacks;
	private static int countFreeTurns;
	private static boolean afterHit = false;
	private boolean white;
	private static boolean staticWhite;
	private static boolean terminal = false;
	private static Stack<MatrixBoard> pastTurns;
	// private boolean kings;
	private int countTurns;
	private Random rand = new Random();
	private ArrayList<MatrixBoard> nextTurns;

	public MatrixBoard(int[][] inBoard) {
		board = arrayCopy(inBoard);
		white = staticWhite;
		// startBoardEight();
	}

	public MatrixBoard() {
		int N = CT.SIZE_BOARD;

		board = new int[N][N];

		N /= 2;
		amountWhites = N * (N - 1);
		amountBlacks = amountWhites;

		pastTurns = new Stack<>();

		startBoardEight();

		white = false;
		pastTurns.push(this);
	}

	private void startBoardEight() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (i < CT.SIZE_BOARD / 2 - 1 && (i + j) % 2 != 0)
					board[i][j] = -1;
				if (i > CT.SIZE_BOARD / 2 && (i + j) % 2 != 0)
					board[i][j] = 1;
			}
		}
	}

	private void makeWhiteTurns() {
		staticWhite = true;
		
		if (!canMakeWhiteBeat()) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j] > 0) {
						int ai = i - 1;
						int aj = j - 1;
						if (inBounds(ai, aj))
							if (board[ai][aj] == 0) {
								nextTurns.add(new MatrixBoard(moveIJ(i, j, ai,
										aj)));
								System.out.println(pastTurns.peek());
							}

						int bi = i - 1;
						int bj = j + 1;
						if (inBounds(bi, bj))
							if (board[bi][bj] == 0) {
								nextTurns.add(new MatrixBoard(moveIJ(i, j, bi,
										bj)));
							}
						if (board[i][j] == 2) {
							int ci = i + 1;
							int cj = j - 1;
							if (inBounds(ci, cj))
								if (board[ci][cj] == 0) {
									nextTurns.add(new MatrixBoard(moveIJ(i, j,
											ci, cj)));

								}

							int di = i + 1;
							int dj = j + 1;
							if (inBounds(di, dj))
								if (board[di][dj] == 0) {
									nextTurns.add(new MatrixBoard(moveIJ(i, j,
											di, dj)));
								}

						}
					}
				}
			}

		}
		
	}

	private void makeBlackTurns() {
		staticWhite = false;
		if (!canMakeBlackBeat()) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j] < 0) {
						int ci = i + 1;
						int cj = j - 1;
						if (inBounds(ci, cj))
							if (board[ci][cj] == 0) {
								nextTurns.add(new MatrixBoard(moveIJ(i, j, ci,
										cj)));

							}

						int di = i + 1;
						int dj = j + 1;
						if (inBounds(di, dj))
							if (board[di][dj] == 0) {
								nextTurns.add(new MatrixBoard(moveIJ(i, j, di,
										dj)));
							}
						if (board[i][j] == -2) {
							int ai = i - 1;
							int aj = j - 1;
							if (inBounds(ai, aj))
								if (board[ai][aj] == 0) {
									nextTurns.add(new MatrixBoard(moveIJ(i, j,
											ai, aj)));

								}

							int bi = i - 1;
							int bj = j + 1;
							if (inBounds(bi, bj))
								if (board[bi][bj] == 0) {
									nextTurns.add(new MatrixBoard(moveIJ(i, j,
											bi, bj)));
								}
						}
					}
				}
			}
		}
		
	}

	private boolean canMakeWhiteBeat() {
		boolean shouldBeat = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] > 0) {
					int ai = i - 1;
					int aj = j - 1;
					if (inBounds(ai, aj))
						if (board[ai][aj] < 0) {
							if (inBounds(ai - 1, aj - 1))
								if (board[ai - 1][aj - 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											ai, aj, ai - 1, aj - 1)));
								}
						}

					int bi = i - 1;
					int bj = j + 1;
					if (inBounds(bi, bj))
						if (board[bi][bj] < 0) {
							if (inBounds(bi - 1, bj + 1))
								if (board[bi - 1][bj + 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											bi, bj, bi - 1, bj + 1)));
								}
						}
					if (board[i][j] == 2) {
						int ci = i + 1;
						int cj = j - 1;
						if (inBounds(ci, cj))
							if (board[ci][cj] < 0) {
								if (inBounds(ci + 1, cj - 1))
									if (board[ci + 1][cj - 1] == 0) {
										shouldBeat = true;
										nextTurns.add(new MatrixBoard(beatIJ(i,
												j, ci, cj, ci + 1, cj - 1)));
									}
							}

						int di = i + 1;
						int dj = j + 1;
						if (inBounds(di, dj))
							if (board[di][dj] < 0) {
								if (inBounds(di + 1, dj + 1))
									if (board[di + 1][dj + 1] == 0) {
										shouldBeat = true;
										nextTurns.add(new MatrixBoard(beatIJ(i,
												j, di, dj, di + 1, dj + 1)));
									}
							}

					}

				}
			}
		}
		return shouldBeat;
	}

	private boolean canMakeBlackBeat() {
		boolean shouldBeat = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] < 0) {
					int ci = i + 1;
					int cj = j - 1;
					if (inBounds(ci, cj))
						if (board[ci][cj] > 0) {
							if (inBounds(ci + 1, cj - 1))
								if (board[ci + 1][cj - 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											ci, cj, ci + 1, cj - 1)));
								}
						}

					int di = i + 1;
					int dj = j + 1;
					if (inBounds(di, dj))
						if (board[di][dj] > 0) {
							if (inBounds(di + 1, dj + 1))
								if (board[di + 1][dj + 1] == 0) {
									shouldBeat = true;
									nextTurns.add(new MatrixBoard(beatIJ(i, j,
											di, dj, di + 1, dj + 1)));
								}
						}
					if (board[i][j] == -2) {
						int ai = i - 1;
						int aj = j - 1;
						if (inBounds(ai, aj))
							if (board[ai][aj] > 0) {
								if (inBounds(ai - 1, aj - 1))
									if (board[ai - 1][aj - 1] == 0) {
										shouldBeat = true;
										nextTurns.add(new MatrixBoard(beatIJ(i,
												j, ai, aj, ai - 1, aj - 1)));
									}
							}

						int bi = i - 1;
						int bj = j + 1;
						if (inBounds(bi, bj))
							if (board[bi][bj] > 0) {
								if (inBounds(bi - 1, bj + 1))
									if (board[bi - 1][bj + 1] == 0) {
										shouldBeat = true;
										nextTurns.add(new MatrixBoard(beatIJ(i,
												j, bi, bj, bi - 1, bj + 1)));
									}
							}

					}
				}
			}
		}
		return shouldBeat;
	}

	private void makeNextTurns() {
		this.white = pastTurns.peek().white;

		nextTurns = new ArrayList<>();

		if (afterHit) {
			if (white == true) {
				if (!canMakeWhiteBeat()) {
					afterHit = false;
					staticWhite = false;
					//makeBlackTurns();
				}

			} else {
				if (!canMakeBlackBeat()) {
					afterHit = false;
					staticWhite = true;
					//makeWhiteTurns();

				}
			}
		}
		if (!afterHit){
			if (!white)
				makeWhiteTurns();
			 else 
				 makeBlackTurns();
		}
	}

	public int[][] getNextMoveTable() {
		if (nextTurns == null)
			makeNextTurns();
		return nextTurns.get(getBestMove()).BOARD();

	}

	public MatrixBoard getNextTurn() {
		if (nextTurns == null)
			makeNextTurns();
		MatrixBoard turn = nextTurns.get(getBestMove());
		pastTurns.push(turn);

		return turn;

	}

	private int getBestMove() {

		if (nextTurns != null) {

		}
		return getRandInt();
	}

	private int getRandInt() {
		int randInt = rand.nextInt(nextTurns.size());
		return randInt;
	}

	// public void initWhiteRoot()
	// {
	// white = true;
	// nextTurns.add(new)
	// }

	private int[][] moveIJ(int fi, int fj, int ti, int tj) {
		int[][] res = arrayCopy(board);
		int temp = res[fi][fj];
		res[fi][fj] = res[ti][tj];

		// make king
		if (temp == 1 && ti == 0)
			res[ti][tj] = 2;
		else if (temp == -1 && ti == CT.SIZE_BOARD - 1)
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
		else if (temp == -1 && ti == CT.SIZE_BOARD - 1)
			res[ti][tj] = -2;
		else
			res[ti][tj] = temp;

		if (temp > 0)
			amountBlacks--;
		else
			amountWhites--;

		res[mi][mj] = 0;

		afterHit = true;
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
		for (MatrixBoard mb : nextTurns) {
			System.out.println(mb);
		}
	}

	@Override
	public String toString() {
		String ss = "Move#" + countTurns;
		ss += "\nWHITE: " + white;
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
