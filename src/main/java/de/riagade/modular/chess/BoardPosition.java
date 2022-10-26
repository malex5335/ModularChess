package de.riagade.modular.chess;

public record BoardPosition(char x, int y) implements Comparable<BoardPosition> {
	@Override
	public int compareTo(BoardPosition they) {
		var compareY = they.y() - y();
		var compareX = x() - they.x();
		return compareY * 100 + compareX;
	}
}
