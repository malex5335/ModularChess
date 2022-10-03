package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.rules.*;
import de.riagade.modular.chess.pieces.util.*;
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
		var rules = new PawnRules(this);
		var generalRules = new GeneralRules(this);
		try {
			generalRules.notOccupiedByOwnPiece(newPosition, board, getPlayer());
			rules.neverMoveBackwards(newPosition);
			rules.noMoreThanTwo(newPosition);
			rules.jumpOnlyFistTime(newPosition);
			if(!rules.regularTake(board, newPosition) && !rules.isEnPassant(board, newPosition))
				rules.dontAllowDiagonalMovement(newPosition);
			return true;
		} catch (UnsupportedOperationException e) {
			return false;
		}
	}
}
