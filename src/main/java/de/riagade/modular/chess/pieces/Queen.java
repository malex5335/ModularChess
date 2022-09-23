package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

@Getter
@Setter
public class Queen implements Piece {
	private BoardPosition position;
	private PieceType pieceType;
	private Player player;

	public Queen(PieceType pieceType, BoardPosition position, Player player) {
		setPieceType(pieceType);
		setPosition(position);
		setPlayer(player);
	}

	@Override
	public boolean canMove(BoardPosition newPosition, Board board) {
		return false;
	}
}
