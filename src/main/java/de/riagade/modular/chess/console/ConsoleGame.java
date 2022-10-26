package de.riagade.modular.chess.console;

import de.riagade.modular.chess.*;

import java.util.Collections;

import static de.riagade.modular.chess.console.ConsoleUtil.*;

public class ConsoleGame implements BaseGame {

	Board board = new Board();

	public static void main(String[] args) {
		var winner = new ConsoleGame().runGame();
		System.out.println("Game is over.");
		var winnerName = switch (winner) {
			case WHITE -> String.format(WHITE_PIECE, winner);
			case BLACK -> String.format(BLACK_PIECE, winner);
			default -> winner.toString();
		};
		System.out.printf("%s won the game.", winnerName);
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
		Collections.sort(getBoard().getAllPositions());
		for(var position : getBoard().getAllPositions()) {
			if(position.x() == 'A') {
				background = nextBackGround(background);
				stringBuilder.append("\n");
				stringBuilder.append(String.format("%d |", position.y()));
			}
			var output = " ";
			var piece = getBoard().getPiece(position);
			if(piece.isPresent()) {
				var actualPiece = piece.get();
				output = ConsoleUtil.getOutput(actualPiece);
				if(actualPiece.getPlayer().equals(Player.WHITE)) {
					output = String.format(WHITE_PIECE, output);
				} else {
					output = String.format(BLACK_PIECE, output);
				}
			}
			stringBuilder.append(String.format("%s|", String.format(background, output)));
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

	@Override
	public void wrongInputError() {
		System.out.println("Can't validate your input");
	}
}
