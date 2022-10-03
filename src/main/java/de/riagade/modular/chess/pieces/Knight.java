package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

import java.util.*;

import static de.riagade.modular.chess.pieces.rules.GeneralRules.*;

@Getter
@Setter
public class Knight implements Piece {
	private BoardPosition position;
	private PieceType pieceType;
	private Player player;

	public Knight(PieceType pieceType, BoardPosition position, Player player) {
		setPieceType(pieceType);
		setPosition(position);
		setPlayer(player);
	}

	@Override
	public boolean canMove(BoardPosition newPosition, Board board) {
		try {
			notOccupiedByOwnPiece(newPosition, board, getPlayer());
		} catch (UnsupportedOperationException e) {
			return false;
		}
		return validPositionsAround(getPosition()).contains(newPosition);
	}

	private List<BoardPosition> validPositionsAround(BoardPosition basePosition) {
		var positions = new ArrayList<BoardPosition>();
		var x = basePosition.x();
		var y = basePosition.y();
		positions.add(new BoardPosition(nextChar(x, 2), y + 1));
		positions.add(new BoardPosition(nextChar(x, 2), y - 1));
		positions.add(new BoardPosition(nextChar(x, 1), y + 2));
		positions.add(new BoardPosition(nextChar(x, 1), y - 2));
		positions.add(new BoardPosition(nextChar(x, -1), y + 2));
		positions.add(new BoardPosition(nextChar(x, -1), y - 2));
		positions.add(new BoardPosition(nextChar(x, -2), y + 1));
		positions.add(new BoardPosition(nextChar(x, -2), y - 1));
		return positions;
	}

	private char nextChar(char x, int add) {
		return (char) (x + add);
	}
}
