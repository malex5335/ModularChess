package de.riagade.modular.chess;

import java.util.*;

public final class BoardPosition {
	private final char x;
	private final int y;

	public BoardPosition(char x, int y) {
		this.x = x;
		this.y = y;
	}

	public char x() {
		return x;
	}

	public int y() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (BoardPosition) obj;
		return this.x == that.x &&
				this.y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "BoardPosition[" +
				"x=" + x + ", " +
				"y=" + y + ']';
	}
}