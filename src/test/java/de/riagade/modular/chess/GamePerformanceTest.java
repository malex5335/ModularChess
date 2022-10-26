package de.riagade.modular.chess;

import de.riagade.modular.chess.mocks.GameMock;
import org.junit.jupiter.api.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static de.riagade.modular.chess.mocks.util.MovementUtil.toMovement;
import static org.junit.jupiter.api.Assertions.*;

class GamePerformanceTest {
	GameMock game;
	List<GameMock.Movement> moves;

	@BeforeEach
	void setup() {
		game = new GameMock();
		moves = game.getMovements();
	}

	@Test
	void twoMoveWinForWhite() {
		// Given
		moves.add(toMovement("E2", "E4"));
		moves.add(toMovement("F7", "F6"));
		moves.add(toMovement("D1", "H5"));
		var maxDuration = estimatedDuration(moves);

		// When
		var winner = game.runGame();

		// Then
		assertEquals(Player.WHITE, winner);
		assertTrue(game.getMillisDuration() <= maxDuration);
	}

	@Test
	void twoMoveWinForBlack() {
		// Given
		moves.add(toMovement("F2", "F3"));
		moves.add(toMovement("E7", "E5"));
		moves.add(toMovement("G2", "G4"));
		moves.add(toMovement("D8", "H4"));
		var maxDuration = estimatedDuration(moves);

		// When
		var winner = game.runGame();

		// Then
		assertEquals(Player.BLACK, winner);
		assertTrue(game.getMillisDuration() <= maxDuration);
	}

	/**
	 * we estimate a duration of 120ms per move as an indicator of "good performance"
	 * @param moves the moves to count
	 * @return the estimate in milliseconds
	 */
	public long estimatedDuration(List<GameMock.Movement> moves) {
		return TimeUnit.MILLISECONDS.toMillis(120) * moves.size();
	}
}
