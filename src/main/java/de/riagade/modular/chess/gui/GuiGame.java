package de.riagade.modular.chess.gui;

import de.riagade.modular.chess.*;

public class GuiGame implements BaseGame {
	Board board = new Board();

	public static void main(String[] args) {
		var winner = new GuiGame().runGame();
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public BoardPosition getFrom() {
		return null;
	}

	@Override
	public BoardPosition getTo() {
		return null;
	}

	@Override
	public void updateBoard() {

	}

	@Override
	public void noPieceSelectedError() {

	}

	@Override
	public void wrongPlayerError() {

	}

	@Override
	public void moveNotPossibleError() {

	}
}
