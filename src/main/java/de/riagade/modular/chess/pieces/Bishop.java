package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import lombok.*;

import static de.riagade.modular.chess.FenHelper.*;

@Getter
@Setter
public class Bishop implements Piece {
	private BoardPosition position;
	private PieceType pieceType;
	private Player player;

	public Bishop(PieceType pieceType, BoardPosition position) {
		setPieceType(pieceType);
		setPosition(position);
		setPlayer(getMyPlayer(pieceType));
	}

	@Override
	public boolean canMove(BoardPosition newPosition, Board board) {
		return false;
	}
}
