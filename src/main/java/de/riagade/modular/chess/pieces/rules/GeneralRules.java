package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;

import java.util.*;

public class GeneralRules {

	public static void notOccupiedByOwnPiece(BoardPosition newPosition, Board board, Player player) throws UnsupportedOperationException {
		var piece = board.getPiece(newPosition);
		if(piece.isPresent() && player.equals(piece.get().getPlayer())) {
			throw new UnsupportedOperationException("position already in use by own piece");
		}
	}

	public static void notOccupied(List<BoardPosition> positions, Board board) throws UnsupportedOperationException {
		var occupiedPositions = positions.stream()
				.filter(bp -> board.getPiece(bp).isPresent())
				.toList();
		if(!occupiedPositions.isEmpty())
			throw new UnsupportedOperationException("there are pieces between that can not be jumped");
	}
}
