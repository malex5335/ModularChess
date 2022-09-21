package de.riagade.modular.chess;

import de.riagade.modular.chess.pieces.*;

public class FenHelper {
	public static void loadFenSettings(Board board, String fen) {
		if(!isValidFen(fen))
			return;
		var fenParts = fen.split(" ");
		var pieces = fenParts[0];
		var player = fenParts[1];
		var castling = fenParts[2];
		var enPassant = fenParts[3];
		var halfMoves = fenParts[4];
		var moves = fenParts[5];
		setPiecesFromFen(board, pieces);
		setPlayerFromFen(board, player);
		setCastlingFromFen(board, castling);
		setEnPassantFromFen(board, enPassant);
		setHalfMovesFromFen(board, halfMoves);
		setMovesFromFen(board, moves);
	}

	private static void setPiecesFromFen(Board board, String pieces) {
		var xStart = 'A';
		var x = xStart;
		var y = 8;
		for(var pos : pieces.toCharArray()) {
			if(Character.isDigit(pos)) {
				x += Character.getNumericValue(pos);
			} else if(Character.isLetter(pos)) {
				board.createPieceAt(pos, x, y);
				x++;
			} else if(pos == '/') {
				x = xStart;
				y--;
			} else {
				break;
			}
		}
	}

	private static boolean isValidFen(String fen) {
		var slashes = count(fen, '/');
		var spaces = count(fen, ' ');
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
		return Character.isUpperCase(pieceType.value) ? Player.WHITE : Player.BLACK;
	}
}
