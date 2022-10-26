package de.riagade.modular.chess.mocks.util;

import de.riagade.modular.chess.BoardPosition;
import de.riagade.modular.chess.mocks.GameMock;

public class MovementUtil {

	public static GameMock.Movement toMovement(String fromStr, String toStr) {
		return new GameMock.Movement(toBoardPosition(fromStr), toBoardPosition(toStr));
	}

	public static BoardPosition toBoardPosition(String str) {
		var parts = str.toCharArray();
		var x = parts[0];
		var y = Character.getNumericValue(parts[1]);
		return new BoardPosition(x, y);
	}
}
