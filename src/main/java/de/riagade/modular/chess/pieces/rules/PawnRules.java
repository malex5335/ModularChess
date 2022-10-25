package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.*;
import lombok.*;

import static de.riagade.modular.chess.pieces.rules.GeneralRules.notOccupied;
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

	public boolean regularTake(Board board, BoardPosition newPosition) throws UnsupportedOperationException {
		var distanceX = Math.abs(getPosition().x() - newPosition.x());
		var distanceY = Math.abs(getPosition().x() - newPosition.x());
		if(!(distanceX == distanceY && distanceX == 1))
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

	public void dontAllowHorizontalMovement(BoardPosition newPosition) throws UnsupportedOperationException {
		if(getPosition().x() != newPosition.x())
			throw new UnsupportedOperationException("cannot move horizontal");
	}

	public void xDistanceMax(BoardPosition newPosition) {
		var distance = xStepsBetween(getPosition(), newPosition);
		if(distance > 1)
			throw new UnsupportedOperationException("cannot move more than 1 field horizontal");
	}

	public void yDistanceMax(BoardPosition newPosition, Board board) {
		var distance = yStepsBetween(getPosition(), newPosition, getPlayer());
		var fromY = getPosition().y();
		var firstTimeMove = switch (getPlayer()) {
			case WHITE -> fromY == 2;
			case BLACK -> fromY == 7;
			default -> throw new UnsupportedOperationException("can not check if no player is active");
		};
		if(firstTimeMove && isOccupied(new BoardPosition(getPosition().x(), getPosition().y() + getPlayerDirection(getPlayer())), board))
			throw new UnsupportedOperationException("space is occupied, cannot move there");
		if(firstTimeMove ? distance > 2 : distance > 1)
			throw new UnsupportedOperationException(String.format("cannot move %d field/s vertical from here", distance));
	}

	private boolean isOccupied(BoardPosition position, Board board) {
		return board.getPiece(position).isPresent();
	}
}
