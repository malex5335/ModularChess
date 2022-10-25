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
			try {
				rules.onlyOneField(newPosition);
			} catch (UnsupportedOperationException e) {
				if(!isCastling(newPosition, board))
					throw e;
			}
			notOccupied(shortestPathBetween(getPosition(), newPosition), board);
		} catch (UnsupportedOperationException e) {
			return false;
		}
		return true;
	}

	private boolean isCastling(BoardPosition newPosition, Board board) {
		var castlingOptions = board.getCastling().getCastlingOptions().get(getPlayer());
		if(castlingOptions.equals(CastlingOptions.NONE))
			return false;
		if(getPosition().y() - newPosition.y() != 0)
			return false;
		if(castlingOptions.equals(CastlingOptions.KING))
			return newPosition.x() == getPosition().x() + 2;
		if(castlingOptions.equals(CastlingOptions.QUEEN))
			return newPosition.x() == getPosition().x() - 2;
		if(castlingOptions.equals(CastlingOptions.BOTH))
			return Math.abs(getPosition().x() - newPosition.x()) == 2;
		return false;
	}
}
