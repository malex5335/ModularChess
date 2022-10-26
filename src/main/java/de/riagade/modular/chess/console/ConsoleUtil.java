package de.riagade.modular.chess.console;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.util.*;

import java.util.*;

public class ConsoleUtil {
	public static final String DARK_BACKGROUND = "#";
	public static final String LIGHT_BACKGROUND = "_";

	public static String getOutput(Piece piece) {
		var value = piece.getPieceType().getValue();
		return String.valueOf(value);
	}

	public static String nextBackGround(String background) {
		return switch (background) {
			case DARK_BACKGROUND -> LIGHT_BACKGROUND;
			case LIGHT_BACKGROUND -> DARK_BACKGROUND;
			default -> "";
		};
	}

	public static BoardPosition askForPos() throws UnsupportedOperationException {
		var scanner = new Scanner(System.in);
		var pos = scanner.nextLine().toUpperCase();
		if(pos.length() >= 2) {
			return new BoardPosition(pos.charAt(0), Character.getNumericValue(pos.charAt(1)));
		}
		throw new UnsupportedOperationException("position not valid");
	}
}
