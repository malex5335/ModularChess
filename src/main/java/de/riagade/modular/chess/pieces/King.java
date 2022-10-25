package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.rules.*;
import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

import static de.riagade.modular.chess.pieces.rules.GeneralRules.*;
import static de.riagade.modular.chess.util.PositionUtil.*;

@Getter
@Setter
public class King implements Piece {
	private BoardPosition position;
	private PieceType pieceType;
	private Player player;

	public King(PieceType pieceType, BoardPosition position, Player player) {
		setPieceType(pieceType);
		setPosition(position);
		setPlayer(player);
	}

	@Override
	public boolean canMove(BoardPosition newPosition, Board board) {
		var rules = new KingRules(this);
		try {
			notOccupiedByOwnPiece(newPosition, board, getPlayer());
			rules.oneFieldOrCastling(newPosition, board);
			notOccupied(shortestPathBetween(getPosition(), newPosition), board);
			/*
			 * We don't need to check if the King would self check in the enemies move.
			 * This also prevents a recursive call on weather we are in check or not
			 */
			if(board.getPlayer().equals(getPlayer()))
				rules.cantCheckSelf(newPosition, board);
		} catch (UnsupportedOperationException e) {
			return false;
		}
		return true;
	}
}
