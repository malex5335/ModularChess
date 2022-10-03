package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.*;
import lombok.*;

@Getter
@Setter
public class KingRules {
	private BoardPosition position;
	private Player player;

	public KingRules(King king) {
		setPosition(king.getPosition());
		setPlayer(king.getPlayer());
	}

	public void onlyOneField(BoardPosition newPosition) {
		var distanceX = Math.abs(getPosition().x() - newPosition.x());
		var distanceY = Math.abs(getPosition().y() - newPosition.y());
		if(distanceX > 1 || distanceY > 1)
			throw new UnsupportedOperationException("cant move more than one field");
	}
}
