package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.rules.*;
import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

import java.util.ArrayList;

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

		var optionList = new ArrayList<>();
		if(castlingOptions.equals(CastlingOptions.BOTH)) {
			optionList.add(CastlingOptions.KING);
			optionList.add(CastlingOptions.QUEEN);
		} else {
			optionList.add(castlingOptions);
		}

		if(optionList.contains(CastlingOptions.KING))
			try {
				notOccupied(shortestPathBetween(getPosition(), new BoardPosition('A', getPosition().y())), board);
				return true;
			} catch (UnsupportedOperationException e) {
				return false;
			}
		if(optionList.contains(CastlingOptions.QUEEN))
			try {
				notOccupied(shortestPathBetween(getPosition(), new BoardPosition('H', getPosition().y())), board);
				return true;
			} catch (UnsupportedOperationException e) {
				return false;
			}
		return false;
	}
}
