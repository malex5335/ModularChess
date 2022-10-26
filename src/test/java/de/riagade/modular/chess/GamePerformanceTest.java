package de.riagade.modular.chess;

import de.riagade.modular.chess.mocks.GameMock;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static de.riagade.modular.chess.mocks.util.MovementUtil.toMovement;
import static org.junit.jupiter.api.Assertions.*;

class GamePerformanceTest {
	GameMock game;
	List<GameMock.Movement> moves;
	static final long MAX_PERFORMANCE_MILLIS = TimeUnit.SECONDS.toMillis(1);

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
		var start = Instant.now();

		// When
		var winner = game.runGame();

		// Then
		var end = Instant.now();
		var duration = ChronoUnit.MILLIS.between(start, end);
		assertEquals(Player.WHITE, winner);
		assertTrue(duration <= MAX_PERFORMANCE_MILLIS);
	}

	@Test
	void twoMoveWinForBlack() {
		// Given
		moves.add(toMovement("F2", "F3"));
		moves.add(toMovement("E7", "E5"));
		moves.add(toMovement("G2", "G4"));
		moves.add(toMovement("D8", "H4"));
		var start = Instant.now();

		// When
		var winner = game.runGame();

		// Then
		var end = Instant.now();
		var duration = ChronoUnit.MILLIS.between(start, end);
		assertEquals(Player.BLACK, winner);
		assertTrue(duration <= MAX_PERFORMANCE_MILLIS);
	}
}
