package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.*;
import lombok.*;

import static de.riagade.modular.chess.util.PositionUtil.*;

@Getter
@Setter
public class PawnRules {
	private BoardPosition position;
	private Player player;

	public PawnRules(Pawn pawn) {
		setPosition(pawn.getPosition());
		setPlayer(pawn.getPlayer());
	}
	public void neverMoveBackwards(BoardPosition newPosition) throws UnsupportedOperationException {
		var fromY = getPosition().y();
		var toY = newPosition.y();
		var rightDirection = switch(getPlayer()) {
			case WHITE -> fromY < toY;
			case BLACK -> fromY > toY;
			default -> throw new UnsupportedOperationException("can not check if no player is active");
		};
		if(!rightDirection)
			throw new UnsupportedOperationException("not allowed to move backwards");
	}

	public void noMoreThanTwo(BoardPosition newPosition) throws UnsupportedOperationException {
		var steps = yStepsBetween(getPosition(), newPosition, getPlayer());
		if(steps > 2)
			throw new UnsupportedOperationException("can't move more than 2 spaces at once");
	}

	public void jumpOnlyFistTime(BoardPosition newPosition) throws UnsupportedOperationException {
		var steps = yStepsBetween(getPosition(), newPosition, getPlayer());
		var fromY = getPosition().y();
		var firstTimeMove = switch (getPlayer()) {
			case WHITE -> fromY == 2;
			case BLACK -> fromY == 7;
			default -> throw new UnsupportedOperationException("can not check if no player is active");
		};
		if(steps >= 2 && !firstTimeMove)
			throw new UnsupportedOperationException("can only move 2 spaces on the first move");
	}

	public boolean regularTake(Board board, BoardPosition newPosition) throws UnsupportedOperationException {
		if(!movesDiagonal(getPosition(), newPosition))
			return false;
		var optionalEncounter = board.getPiece(newPosition);
		if(optionalEncounter.isPresent()) {
			var opponent = optionalEncounter.get().getPlayer();
			if(getPlayer().equals(opponent))
				throw new UnsupportedOperationException("cannot take own piece");
			return true;
		}
		return false;
	}

	public boolean isEnPassant(Board board, BoardPosition newPosition) {
		var fromY = getPosition().y();
		var toY = newPosition.y();
		var correctMoveDirection = fromY + getPlayerDirection(getPlayer()) == toY;
		var correctField = newPosition.equals(board.getEnPassant());
		return correctMoveDirection && correctField && isNextTo(getPosition(), newPosition);
	}

	public void dontAllowDiagonalMovement(BoardPosition newPosition) throws UnsupportedOperationException {
		if(movesDiagonal(getPosition(), newPosition))
			throw new UnsupportedOperationException("cannot move diagonally");
	}
}
