package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.*;
import lombok.*;

import java.util.ArrayList;

import static de.riagade.modular.chess.pieces.rules.GeneralRules.notOccupied;
import static de.riagade.modular.chess.util.PositionUtil.shortestPathBetween;

@Getter
@Setter
public class KingRules {
	private BoardPosition position;
	private Player player;

	public KingRules(King king) {
		setPosition(king.getPosition());
		setPlayer(king.getPlayer());
	}

	public void cantCheckSelf(BoardPosition newPosition, Board board) {
		if(board.isCheckAt(newPosition))
			throw new UnsupportedOperationException("you cant set yourself into check mate");
	}

	public void oneFieldOrCastling(BoardPosition newPosition, Board board) {
		var distanceX = Math.abs(getPosition().x() - newPosition.x());
		var distanceY = Math.abs(getPosition().y() - newPosition.y());
		if((distanceX > 1 || distanceY > 1) && !isCastling(newPosition, board))
			throw new UnsupportedOperationException("cant move more than one field");
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
