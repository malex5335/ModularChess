package de.riagade.modular.chess;

import lombok.Getter;

@Getter
public enum Player {
	WHITE('w'), BLACK('b'), NO_ONE('x');

	final char value;

	Player(char value) {
		this.value = value;
	}

	public static Player from(char value) {
		for(var player : values()) {
			if(player.value == value) {
				return player;
			}
		}
		throw new IllegalArgumentException("no piece found by this value");
	}

	public Player next() {
		return this.equals(WHITE) ? Player.BLACK : Player.WHITE;
	}
}
