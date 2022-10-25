package de.riagade.modular.chess;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StaleMateTest {

	@Test
	void roundStart() {
		// Given
		var board = new Board();

		// When
		var staleMated = board.isStaleMate();

		// Then
		assertFalse(staleMated);
	}


	@Test
	void whiteInStaleMate() {
		// Given
		var fen = "8/8/8/8/1r6/p7/P7/K7 w - - 0 1";
		var board = new Board(fen);

		// When
		var staleMated = board.isStaleMate();

		// Then
		assertTrue(staleMated);
		assertFalse(board.isCheck());
		assertFalse(board.isCheckMate());
	}

	@Test
	void blackInStaleMate() {
		// Given
		var fen = "k7/8/1QB6/8/8/8/8/8 b - - 0 1";
		var board = new Board(fen);

		// When
		var checkMated = board.isCheckMate();

		// Then
		assertTrue(checkMated);
		assertTrue(board.isCheck());
	}
}
