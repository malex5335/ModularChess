package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

@Getter
@Setter
public class GeneralRules {
	private BoardPosition position;
	private Player player;

	public GeneralRules(Piece piece) {
		setPosition(piece.getPosition());
		setPlayer(piece.getPlayer());
	}

	public void notOccupiedByOwnPiece(BoardPosition newPosition, Board board, Player player) throws UnsupportedOperationException {
		var piece = board.getPiece(newPosition);
		if(piece.isPresent() && player.equals(piece.get().getPlayer())) {
			throw new UnsupportedOperationException("position already in use by own piece");
		}
	}
}
