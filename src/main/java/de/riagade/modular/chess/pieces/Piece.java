package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;

public interface Piece {

	PieceType getPieceType();

	BoardPosition getPosition();

	void setPosition(BoardPosition position);

	Player getPlayer();

	boolean canMove(BoardPosition newPosition, Board board);
}
