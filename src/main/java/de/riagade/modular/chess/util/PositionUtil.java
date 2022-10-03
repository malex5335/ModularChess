package de.riagade.modular.chess.util;

import de.riagade.modular.chess.*;

public class PositionUtil {

	public static boolean movesDiagonal(BoardPosition from, BoardPosition to) {
		var yMovement = Math.abs(from.y() - to.y());
		var xMovement = Math.abs(from.x() - to.x());
		return yMovement == xMovement;
	}
	public static boolean isNextTo(BoardPosition from, BoardPosition to) {
		return Math.abs(xStepsBetween(from, to)) == 1;
	}

	public static int xStepsBetween(BoardPosition from, BoardPosition to) {
		return to.x() - from.x();
	}

	public static int yStepsBetween(BoardPosition from, BoardPosition to, Player player) {
		return (to.y() - from.y()) * getPlayerDirection(player);
	}

	public static int getPlayerDirection(Player player) {
		return switch (player) {
			case WHITE -> 1;
			case BLACK -> -1;
		};
	}
}