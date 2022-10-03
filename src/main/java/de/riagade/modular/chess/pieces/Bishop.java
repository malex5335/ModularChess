package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.rules.*;
import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

@Getter
@Setter
public class Bishop implements Piece {
	private BoardPosition position;
	private PieceType pieceType;
	private Player player;

	public Bishop(PieceType pieceType, BoardPosition position, Player player) {
		setPieceType(pieceType);
		setPosition(position);
		setPlayer(player);
	}

	@Override
	public boolean canMove(BoardPosition newPosition, Board board) {
		var rules = new BishopRules(this);
		var generalRules = new GeneralRules(this);
		try {
			generalRules.notOccupiedByOwnPiece(newPosition, board, getPlayer());
			rules.onlyAllowDiagonal(newPosition);
			rules.noPieceBetween(newPosition, board);
			return true;
		} catch(UnsupportedOperationException e) {
			return false;
		}
	}
}
