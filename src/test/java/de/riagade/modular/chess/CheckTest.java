package de.riagade.modular.chess;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CheckTest {

	@Test
	void whiteInCheck() {
		// Given
		var fen = "8/8/8/8/8/2q6/8/K7 w - - 0 1";
		var board = new Board(fen);

		// When
		var checked = board.isCheck();

		// Then
		assertTrue(checked);
	}

	@Test
	void whiteNotInCheck() {
		// Given
		var fen = "8/8/8/8/8/1q6/8/K7 w - - 0 1";
		var board = new Board(fen);

		// When
		var checked = board.isCheck();

		// Then
		assertFalse(checked);
	}

	@Test
	void blackInCheck() {
		// Given
		var fen = "k7/8/2Q6/8/8/8/8/8 b - - 0 1";
		var board = new Board(fen);

		// When
		var checked = board.isCheck();

		// Then
		assertTrue(checked);
	}

	@Test
	void blackNotInCheck() {
		// Given
		var fen = "k7/8/1Q6/8/8/8/8/8 b - - 0 1";
		var board = new Board(fen);

		// When
		var checked = board.isCheck();

		// Then
		assertFalse(checked);
	}
}
