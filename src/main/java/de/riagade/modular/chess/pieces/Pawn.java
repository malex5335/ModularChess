package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import lombok.*;

@Getter
@Setter
public class Pawn implements Piece {
	private BoardPosition position;
	private PieceType pieceType;
	private Player player;

	public Pawn(PieceType pieceType, BoardPosition position, Player player) {
		setPieceType(pieceType);
		setPosition(position);
		setPlayer(player);
	}

	@Override
	public boolean canMove(BoardPosition newPosition, Board board) {
		try {
			neverMoveBackwards(newPosition);
			noMoreThanTwo(newPosition);
			jumpOnlyFistTime(newPosition);
			if(!regularTake(board, newPosition) && !isEnPassant(board, newPosition))
				dontAllowDiagonalMovement(newPosition);
			return true;
		} catch (UnsupportedOperationException e) {
			return false;
		}
	}

	private void neverMoveBackwards(BoardPosition newPosition) throws UnsupportedOperationException {
		var fromY = getPosition().y();
		var toY = newPosition.y();
		var rightDirection = switch(getPlayer()) {
			case WHITE -> fromY < toY;
			case BLACK -> fromY > toY;
		};
		if(!rightDirection)
			throw new UnsupportedOperationException("not allowed to move backwards");
	}

	private void noMoreThanTwo(BoardPosition newPosition) throws UnsupportedOperationException {
		var steps = stepsTo(newPosition);
		if(steps > 2)
			throw new UnsupportedOperationException("can't move more than 2 spaces at once");
	}

	private void jumpOnlyFistTime(BoardPosition newPosition) throws UnsupportedOperationException {
		var steps = stepsTo(newPosition);
		var fromY = getPosition().y();
		var firstTimeMove = switch (getPlayer()) {
			case WHITE -> fromY == 2;
			case BLACK -> fromY == 7;
		};
		if(steps >= 2 && !firstTimeMove)
			throw new UnsupportedOperationException("can only move 2 spaces on the first move");
	}

	private boolean regularTake(Board board, BoardPosition newPosition) throws UnsupportedOperationException {
		if(!movesDiagonal(newPosition))
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

	private boolean isEnPassant(Board board, BoardPosition newPosition) {
		var fromY = getPosition().y();
		var toY = newPosition.y();
		var correctMoveDirection = fromY + getPlayerDirection() == toY;
		var correctField = newPosition.equals(board.getEnPassant());
		return correctMoveDirection && correctField && xNextTo(newPosition);
	}

	private void dontAllowDiagonalMovement(BoardPosition newPosition) throws UnsupportedOperationException {
		if(movesDiagonal(newPosition))
			throw new UnsupportedOperationException("cannot move diagonally");
	}

	private boolean movesDiagonal(BoardPosition newPosition) {
		var fromY = getPosition().y();
		var toY = newPosition.y();
		var moveHorizontal = fromY + getPlayerDirection() == toY;
		var moveVertical = xNextTo(newPosition);
		return moveHorizontal && moveVertical;
	}

	private boolean xNextTo(BoardPosition newPosition) {
		var sourceX = getPosition().x();
		var targetX = newPosition.x();
		return sourceX == targetX + 1 || sourceX == targetX - 1;
	}

	private int stepsTo(BoardPosition newPosition) {
		var fromY = getPosition().y();
		var toY = newPosition.y();
		return switch(getPlayer()) {
			case WHITE -> toY - fromY;
			case BLACK -> fromY - toY;
		};
	}

	private int getPlayerDirection() {
		return switch (getPlayer()) {
			case WHITE -> 1;
			case BLACK -> -1;
		};
	}
}
