package de.riagade.modular.chess;

import de.riagade.modular.chess.pieces.util.*;

import java.util.*;

public class ConsoleGame {


	private static final String DARK_BACKGROUND = "#";
	private static final String LIGHT_BACKGROUND = "_";

	public static void main(String[] args) {
		var board = new Board();
		while(board.isRunning()) {
			printBoard(board);
			try {
				System.out.printf("\nNext move player %s\n\n", getOutput(board.getPlayer()));
				System.out.print("Piece to move: ");
				var from = askForPos();
				var potentialPiece = board.getPiece(from);
				if (potentialPiece.isEmpty()) {
					System.out.println("that's not a piece...");
					continue;
				}
				var piece = potentialPiece.get();
				System.out.printf("Position to move %s to: ", getOutput(piece));
				var to = askForPos();
				if(!piece.canMove(to, board)) {
					System.out.println("move is not possible!");
					continue;
				}
				board.move(potentialPiece.get(), to);
			} catch (UnsupportedOperationException e) {
				System.out.println("Your message was not valid!");
			}
		}
	}

	private static BoardPosition askForPos() throws UnsupportedOperationException {
		var scanner = new Scanner(System.in);
		var pos = scanner.nextLine().toUpperCase();
		if(pos.length() >= 2){
			return new BoardPosition(pos.charAt(0), Character.getNumericValue(pos.charAt(1)));
		}
		throw new UnsupportedOperationException("position not valid");
	}

	private static void printBoard(Board board) {
		var stringBuilder = new StringBuilder();
		var background = DARK_BACKGROUND;
		board.getAllPositions().sort(ConsoleGame::sortList);
		for(var position : board.getAllPositions()) {
			if(position.x() == 'A') {
				background = nextBackGround(background);
				stringBuilder.append("\n");
				stringBuilder.append(String.format("%d |", position.y()));
			}
			var output = background;
			var piece = board.getPiece(position);
			if(piece.isPresent())
				output = getOutput(piece.get());
			stringBuilder.append(String.format("%s|", output));
			background = nextBackGround(background);
		}
		stringBuilder.append("\n   A B C D E F G H");
		System.out.println(stringBuilder);
	}

	private static int sortList(BoardPosition me, BoardPosition they) {
		var compareY = they.y() - me.y();
		var compareX = me.x() - they.x();
		return compareY * 100 + compareX;
	}

	private static String nextBackGround(String background) {
		return switch (background) {
			case DARK_BACKGROUND -> LIGHT_BACKGROUND;
			case LIGHT_BACKGROUND -> DARK_BACKGROUND;
			default -> "";
		};
	}

	private static String getOutput(Piece piece) {
		var value = piece.getPieceType().getValue();
		return String.valueOf(value);
	}

	private static String getOutput(Player player) {
		return player.toString();
	}
}
