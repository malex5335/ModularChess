package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.rules.*;
import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

import static de.riagade.modular.chess.pieces.rules.GeneralRules.*;
import static de.riagade.modular.chess.pieces.util.PathFinder.shortestPathBetween;

@Getter
@Setter
public class Rook implements Piece {
	private BoardPosition position;
	private PieceType pieceType;
	private Player player;

	public Rook(PieceType pieceType, BoardPosition position, Player player) {
		setPieceType(pieceType);
		setPosition(position);
		setPlayer(player);
	}

	@Override
	public boolean canMove(BoardPosition newPosition, Board board) {
		var rules = new RookRules(this);
		try {
			notOccupiedByOwnPiece(newPosition, board, getPlayer());
			rules.onlyAllowLinear(newPosition);
			notOccupied(shortestPathBetween(getPosition(), newPosition), board);
			return true;
		} catch (UnsupportedOperationException e) {
			return false;
		}
	}
}
