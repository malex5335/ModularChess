package de.riagade.modular.chess;

public enum Player {
	WHITE('w'), BLACK('b');

	final char value;

	Player(char value) {
		this.value = value;
	}
}
