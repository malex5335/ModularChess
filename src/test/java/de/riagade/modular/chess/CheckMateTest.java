package de.riagade.modular.chess;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CheckMateTest {

	@Test
	void roundStart() {
		// Given
		var board = new Board();

		// When
		var checkMated = board.isCheckMate();

		// Then
		assertFalse(checkMated);
	}

	@Test
	void whiteInCheckMate() {
		// Given
		var fen = "8/8/8/8/8/1qb6/8/K7 w - - 0 1";
		var board = new Board(fen);

		// When
		var checkMated = board.isCheckMate();

		// Then
		assertTrue(checkMated);
		assertTrue(board.isCheck());
	}

	@Test
	void blackInCheckMate() {
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
