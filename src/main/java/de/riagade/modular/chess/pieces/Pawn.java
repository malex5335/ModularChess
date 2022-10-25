package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.rules.*;
import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

import static de.riagade.modular.chess.pieces.rules.GeneralRules.*;
import static de.riagade.modular.chess.util.PositionUtil.shortestPathBetween;

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
		try {
			notOccupiedByOwnPiece(newPosition, board, getPlayer());
			rules.xDistanceMax(newPosition);
			rules.yDistanceMax(newPosition, board);
			rules.neverMoveBackwards(newPosition);
			if(!rules.regularTake(board, newPosition) && !rules.isEnPassant(board, newPosition))
				rules.dontAllowHorizontalMovement(newPosition);
		} catch (UnsupportedOperationException e) {
			return false;
		}
		return true;
	}
}
