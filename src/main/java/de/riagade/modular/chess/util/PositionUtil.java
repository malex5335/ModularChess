package de.riagade.modular.chess.util;

import de.riagade.modular.chess.*;

import java.util.*;

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
			default -> throw new UnsupportedOperationException("can not check if no player is active");
		};
	}

	public static List<BoardPosition> shortestPathBetween(BoardPosition from, BoardPosition to) {
		var steps = new ArrayList<BoardPosition>();
		var directionX = toSingleStep(to.x() - from.x());
		var directionY = toSingleStep(to.y() - from.y());
		var betweenPosition = nextPosition(from, directionX, directionY);
		while(!betweenPosition.equals(to)) {
			steps.add(betweenPosition);
			betweenPosition = nextPosition(betweenPosition, directionX, directionY);
		}
		return steps;
	}

	private static int toSingleStep(int distance) {
		return Integer.compare(distance, 0);
	}

	private static BoardPosition nextPosition(BoardPosition oldPosition, int distanceX, int distanceY) {
		var nextX = (char) (oldPosition.x() + distanceX);
		var nextY = oldPosition.y() + distanceY;
		return new BoardPosition(nextX, nextY);
	}
}
