package de.riagade.modular.chess.pieces.util;

import de.riagade.modular.chess.*;

import java.util.List;
import java.util.stream.Collectors;

public interface Piece {

	PieceType getPieceType();

	BoardPosition getPosition();

	void setPosition(BoardPosition position);

	Player getPlayer();

	default boolean canMove(BoardPosition newPosition, Board board) {
		return getPossibleMoves(board).contains(newPosition);
	}

	default List<BoardPosition> getPossibleMoves(Board board) {
		var positions = board.getAllPositions();
		return positions.stream()
				.filter(bp -> canMove(bp, board))
				.collect(Collectors.toList());
	}
}
