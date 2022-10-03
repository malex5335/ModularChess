package de.riagade.modular.chess.pieces.rules;

import de.riagade.modular.chess.*;
import de.riagade.modular.chess.pieces.*;
import lombok.*;

@Getter
@Setter
public class RookRules {
	private BoardPosition position;
	private Player player;

	public RookRules(Rook rook) {
		setPosition(rook.getPosition());
		setPlayer(rook.getPlayer());
	}

	public void onlyAllowLinear(BoardPosition newPosition) throws UnsupportedOperationException {
		var differenceX = getPosition().x() - newPosition.x();
		var differenceY = getPosition().y() - newPosition.y();
		if(!(differenceX == 0 || differenceY == 0))
			throw new UnsupportedOperationException("only allowed to move linear");
	}
}
