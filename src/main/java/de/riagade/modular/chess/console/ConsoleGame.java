package de.riagade.modular.chess.console;

import de.riagade.modular.chess.*;

import static de.riagade.modular.chess.console.ConsoleUtil.*;

public class ConsoleGame implements BaseGame {

	Board board = new Board();

	public static void main(String[] args) {
		var winner = new ConsoleGame().runGame();
		System.out.println("Game is over.");
		System.out.printf("%s won the game.", winner);
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public BoardPosition getFrom() {
		System.out.print("Piece to move: ");
		return askForPos();
	}

	@Override
	public BoardPosition getTo() {
		System.out.print("Position to move to: ");
		return askForPos();
	}

	@Override
	public void updateBoard() {
		var stringBuilder = new StringBuilder();
		var background = DARK_BACKGROUND;
		getBoard().getAllPositions().sort(ConsoleUtil::sortList);
		for(var position : getBoard().getAllPositions()) {
			if(position.x() == 'A') {
				background = nextBackGround(background);
				stringBuilder.append("\n");
				stringBuilder.append(String.format("%d |", position.y()));
			}
			var output = background;
			var piece = getBoard().getPiece(position);
			if(piece.isPresent())
				output = ConsoleUtil.getOutput(piece.get());
			stringBuilder.append(String.format("%s|", output));
			background = nextBackGround(background);
		}
		stringBuilder.append("\n   A B C D E F G H");
		System.out.println(stringBuilder);
		if(board.isCheck())
			System.out.println("Check, you need to save your King!");
	}

	@Override
	public void noPieceSelectedError() {
		System.out.println("That's not a piece...");
	}

	@Override
	public void wrongPlayerError() {
		System.out.println("That's not your piece");
	}

	@Override
	public void moveNotPossibleError() {
		System.out.println("That move is not possible!");
	}
}
