package de.riagade.modular.chess;

public enum Player {
	WHITE('w'), BLACK('b');

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
		throw new UnsupportedOperationException("no piece found by this value");
	}
}
