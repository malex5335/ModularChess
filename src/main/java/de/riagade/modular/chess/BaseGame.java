package de.riagade.modular.chess;

import de.riagade.modular.chess.pieces.util.*;

/**
 * Simply implement all methods and execute {@link #runGame()} in a main method of your choice
 */
public interface BaseGame {

	/**
	 * the default game loop that executes all methods of this interface
	 * in order to have a great game experience
	 */
	default Player runGame() {
		while(!getBoard().isCheckMate()) {
			updateBoard();
			var optionalPiece = getBoard().getPiece(getFrom());
			if(optionalPiece.isEmpty()) {
				noPieceSelectedError();
				continue;
			}
			var actualPiece = optionalPiece.get();
			if(!actualPiece.getPlayer().equals(getBoard().getPlayer())) {
				wrongPlayerError();
				continue;
			}
			var to = getTo();
			if(!actualPiece.canMove(to, getBoard())){
				moveNotPossibleError();
				continue;
			}
			getBoard().move(actualPiece, to);
		}
		return getBoard().getWinner();
	}

	/**
	 * the current board with all it's positions
	 * @return a created instance of board; do NOT return a new instance
	 */
	Board getBoard();

	/**
	 * you can execute any logic that you want in order to get the position from which to go
	 * @return the position of the piece that you want to move
	 */
	BoardPosition getFrom();

	/**
	 * you can execute any logic that you want in order to get the position to which to go
	 * @return the position that your piece wants to go to
	 */
	BoardPosition getTo();

	/**
	 * will execute this every time the board should be rendered anew
	 */
	void updateBoard();

	/**
	 * this will be executed if {@link #getFrom()} does not refer to a {@link Piece}
	 */
	void noPieceSelectedError();

	/**
	 * this will be executed if the {@link Piece} you try to move does not belong to yourself
	 */
	void wrongPlayerError();

	/**
	 * this will be executed if {@link #getTo()} refers to a {@link BoardPosition} this piece can not move to
	 */
	void moveNotPossibleError();
}
