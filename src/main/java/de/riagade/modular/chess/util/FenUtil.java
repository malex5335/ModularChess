package de.riagade.modular.chess.util;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.util.*;

import java.util.function.*;

public class FenUtil {
	public static final String DEFAULT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	public static final char FEN_PART_SPLITTER = ' ';
	public static final char FEN_ROW_SPLITTER = '/';

	public static void loadFenSettings(Board board, String fen) {
		if(!isValidFen(fen))
			throw new IllegalArgumentException("the fen string seems to be invalid");
		var fenParts = fen.split(String.valueOf(FEN_PART_SPLITTER));
		setPiecesFromFen(board, fenParts[0]);
		setPlayerFromFen(board, fenParts[1]);
		setCastlingFromFen(board, fenParts[2]);
		setEnPassantFromFen(board, fenParts[3]);
		setHalfMovesFromFen(board, fenParts[4]);
		setMovesFromFen(board, fenParts[5]);
	}

	private static boolean isValidFen(String fen) {
		var slashes = count(fen, FEN_ROW_SPLITTER);
		var spaces = count(fen, FEN_PART_SPLITTER);
		return slashes == 7 && spaces == 5;
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

	private static void setPlayerFromFen(Board board, String player) {
		board.setPlayer(Player.from(player.charAt(0)));
	}

	private static void setCastlingFromFen(Board board, String castling) {
		board.setCastling(new Castling());
		var options = board.getCastling().getCastlingOptions();
		var whiteOptions = findCharacteristicOptions(castling, Character::isUpperCase);
		if(!whiteOptions.isEmpty())
			options.put(Player.WHITE, CastlingOptions.fromValue(whiteOptions));
		var blackOptions = findCharacteristicOptions(castling, Character::isLowerCase);
		if(!blackOptions.isEmpty())
			options.put(Player.BLACK, CastlingOptions.fromValue(blackOptions));
	}

	private static String findCharacteristicOptions(String castling, IntPredicate function) {
		return castling
				.chars()
				.filter(function)
				.collect(StringBuilder::new,
						StringBuilder::appendCodePoint,
						StringBuilder::append)
				.toString();
	}

	private static void setEnPassantFromFen(Board board, String enPassant) {
		if(enPassant.length() < 2)
			return;
		var x = Character.toUpperCase(enPassant.charAt(0));
		var y = Character.getNumericValue(enPassant.charAt(1));
		board.setEnPassant(new BoardPosition(x, y));
	}

	private static void setHalfMovesFromFen(Board board, String halfMoves) {
		board.setHalfMoves(Integer.parseInt(halfMoves));
	}

	private static void setMovesFromFen(Board board, String moves) {
		try {
			board.setMoves(Math.max(1, Integer.parseInt(moves)));
		} catch (NumberFormatException e) {
			board.setMoves(1);
		}
	}

	private static int count(String str, char c) {
		var count = 0;
		for(var s : str.toCharArray()) {
			if(s==c)
				count++;
		}
		return count;
	}
}
