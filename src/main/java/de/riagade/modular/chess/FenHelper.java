package de.riagade.modular.chess;

import de.riagade.modular.chess.pieces.*;

public class FenHelper {
	public static final char FEN_PART_SPLITTER = ' ';
	public static final char FEN_ROW_SPLITTER = '/';

	public static void loadFenSettings(Board board, String fen) {
		if(!isValidFen(fen))
			return;
		var fenParts = fen.split(String.valueOf(FEN_PART_SPLITTER));
		setPiecesFromFen(board, fenParts[0]);
		setPlayerFromFen(board, fenParts[1]);
		setCastlingFromFen(board, fenParts[2]);
		setEnPassantFromFen(board, fenParts[3]);
		setHalfMovesFromFen(board, fenParts[4]);
		setMovesFromFen(board, fenParts[5]);
	}

	private static void setPiecesFromFen(Board board, String pieces) {
		var xStart = 'A';
		var x = xStart;
		var y = 8;
		for(var pos : pieces.toCharArray()) {
			if(Character.isDigit(pos)) {
				x += Character.getNumericValue(pos);
			} else if(Character.isLetter(pos)) {
				var pieceType = PieceType.from(pos);
				board.createPieceAt(pieceType, x, y);
				x++;
			} else if(pos == FEN_ROW_SPLITTER) {
				x = xStart;
				y--;
			} else {
				break;
			}
		}
	}

	private static boolean isValidFen(String fen) {
		var slashes = count(fen, FEN_ROW_SPLITTER);
		var spaces = count(fen, FEN_PART_SPLITTER);
		return slashes == 7 && spaces == 5;
	}

	private static void setPlayerFromFen(Board board, String player) {
		board.setPlayer(Player.from(player.charAt(0)));
	}

	private static void setCastlingFromFen(Board board, String castling) {
	}

	private static void setEnPassantFromFen(Board board, String enPassant) {
	}

	private static void setHalfMovesFromFen(Board board, String halfMoves) {
		board.setHalfMoves(Integer.parseInt(halfMoves));
	}

	private static void setMovesFromFen(Board board, String moves) {
		board.setMoves(Integer.parseInt(moves));
	}

	private static int count(String str, char c) {
		var count = 0;
		for(var s : str.toCharArray()) {
			if(s==c)
				count++;
		}
		return count;
	}

	public static Player getMyPlayer(PieceType pieceType) {
		return Character.isUpperCase(pieceType.getValue()) ? Player.WHITE : Player.BLACK;
	}
}
