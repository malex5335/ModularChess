package de.riagade.modular.chess.gui;

import de.riagade.modular.chess.*;
import lombok.*;

@Getter
@Setter
public class GuiGame implements BaseGame {
	Board board;
	GuiWindow window;
	Move move;
	public record Move(BoardPosition from, BoardPosition to){}

	public GuiGame() {
		setBoard(new Board());
		setWindow(new GuiWindow(getBoard()));
		getWindow().setVisible(true);
	}

	public static void main(String[] args) {
		var game = new GuiGame();
		game.printWinner(game.runGame());
	}

	private void printWinner(Player winner) {
	}

	@Override
	public BoardPosition getFrom() {
		listenForMove();
		return getMove().from();
	}

	@Override
	public BoardPosition getTo() {
		listenForMove();
		return getMove().to();
	}

	private void listenForMove() {
		do {
			for( var button : getWindow().getButtons(getBoard().getPlayer())) {
				if(button.isCursorSet()) {
					getWindow().repaint();
				}
			}
			setMove(getWindow().getMove());
		} while (getMove() == null);
	}

	@Override
	public void updateBoard() {
		getWindow().repaint();
		setMove(null);
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
