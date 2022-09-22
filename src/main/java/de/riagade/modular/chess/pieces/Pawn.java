package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import lombok.*;

import static de.riagade.modular.chess.FenHelper.*;

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
			diagonalOnlyForTakeOrEnPassant(board, newPosition);
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

	private void diagonalOnlyForTakeOrEnPassant(Board board, BoardPosition newPosition) throws UnsupportedOperationException {
		var fromX = getPosition().x();
		var toX = newPosition.x();
		var moveDiagonal = fromX != toX;
		if(!moveDiagonal)
			return;
		if(isEnPassant(board, newPosition))
			return;
		var optionalEncounter = board.getPiece(newPosition);
		if(optionalEncounter.isPresent()) {
			var opponent = optionalEncounter.get().getPlayer();
			if(getPlayer().equals(opponent))
				throw new UnsupportedOperationException("cannot take own piece");
		} else {
			throw new UnsupportedOperationException("cannot move diagonally");
		}
	}

	private boolean isEnPassant(Board board, BoardPosition newPosition) {
		var currentPos = getPosition();
		var correctField = board.getEnPassant().equals(newPosition);
		var correctMoveDirection = switch (getPlayer()) {
			case WHITE -> (currentPos.y() + 1) == newPosition.y();
			case BLACK -> (currentPos.y() - 1) == newPosition.y();
		};
		return correctField && correctMoveDirection && xNextTo(newPosition);
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
}
