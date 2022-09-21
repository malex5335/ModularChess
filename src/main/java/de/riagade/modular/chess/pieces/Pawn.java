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

	public Pawn(PieceType pieceType, BoardPosition position) {
		setPieceType(pieceType);
		setPosition(position);
		setPlayer(getMyPlayer(pieceType));
	}

	@Override
	public boolean canMove(BoardPosition newPosition, Board board) {
		try {
			neverMoveBackwards(newPosition);
			noMoreThanTwo(newPosition);
			jumpOnlyFistTime(newPosition);
			diagonalOnlyForTake(board, newPosition);
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

	private void diagonalOnlyForTake(Board board, BoardPosition newPosition) throws UnsupportedOperationException {
		var fromX = getPosition().x();
		var toX = newPosition.x();
		var moveDiagonal = fromX != toX;
		if(!moveDiagonal)
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

	private int stepsTo(BoardPosition newPosition) {
		var fromY = getPosition().y();
		var toY = newPosition.y();
		return switch(getPlayer()) {
			case WHITE -> toY - fromY;
			case BLACK -> fromY - toY;
		};
	}
}
