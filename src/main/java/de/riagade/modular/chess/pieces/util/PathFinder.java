package de.riagade.modular.chess.pieces.util;

import de.riagade.modular.chess.*;

import java.util.*;

public class PathFinder {

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
