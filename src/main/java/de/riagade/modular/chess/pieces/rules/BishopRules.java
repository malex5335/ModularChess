package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
public class BishopRules {
	private BoardPosition position;
	private Player player;

	public BishopRules(Bishop bishop) {
		setPosition(bishop.getPosition());
		setPlayer(bishop.getPlayer());
	}

	public void onlyAllowDiagonal(BoardPosition newPosition) throws UnsupportedOperationException {
		var toX = newPosition.x();
		var toY = newPosition.y();
		var fromX = getPosition().x();
		var fromY = getPosition().y();
		var distanceX = Math.abs(fromX - toX);
		var distanceY = Math.abs(fromY - toY);
		if(distanceX != distanceY) {
			throw new UnsupportedOperationException("only allowed to move diagonal");
		}
	}

	public void noPieceBetween(BoardPosition newPosition, Board board) throws UnsupportedOperationException {
		var positionsBetween = diagonalPathSteps(getPosition(), newPosition);
		var occupiedPositions = positionsBetween.stream()
				.filter(bp -> board.getPiece(bp).isPresent())
				.toList();
		if(!occupiedPositions.isEmpty())
			throw new UnsupportedOperationException("there are pieces between that can not be jumped");
	}

	private List<BoardPosition> diagonalPathSteps(BoardPosition oldPosition, BoardPosition newPosition) throws UnsupportedOperationException {
		onlyAllowDiagonal(newPosition);
		var steps = new ArrayList<BoardPosition>();
		var directionX = toSingleStep(newPosition.x() - oldPosition.x());
		var directionY = toSingleStep(newPosition.y() - oldPosition.y());
		var betweenPosition = nextPosition(oldPosition, directionX, directionY);
		while(!betweenPosition.equals(newPosition)) {
			steps.add(betweenPosition);
			betweenPosition = nextPosition(betweenPosition, directionX, directionY);
		}
		return steps;
	}

	private int toSingleStep(int distance) {
		return Integer.compare(distance, 0);
	}

	private BoardPosition nextPosition(BoardPosition oldPosition, int distanceX, int distanceY) {
		var nextX = (char) (oldPosition.x() + distanceX);
		var nextY = oldPosition.y() + distanceY;
		return new BoardPosition(nextX, nextY);
	}
}
