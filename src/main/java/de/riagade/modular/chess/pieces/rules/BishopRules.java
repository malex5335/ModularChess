package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.*;
import lombok.*;

@Getter
@Setter
public class BishopRules {
	private BoardPosition position;
	private Player player;

	public BishopRules(Bishop bishop) {
		setPosition(bishop.getPosition());
		setPlayer(bishop.getPlayer());
	}

	public void onlyAllowDiagonal(BoardPosition newPosition) throws UnsupportedOperationException {
		var toX = newPosition.x();
		var toY = newPosition.y();
		var fromX = getPosition().x();
		var fromY = getPosition().y();
		var distanceX = Math.abs(fromX - toX);
		var distanceY = Math.abs(fromY - toY);
		if(distanceX != distanceY) {
			throw new UnsupportedOperationException("only allowed to move diagonal");
		}
	}
}
