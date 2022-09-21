package de.riagade.modular.chess;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static de.riagade.modular.chess.Player.*;
import static org.junit.jupiter.api.Assertions.*;

public class NextPlayerTest {
	private Board board;

	@BeforeEach
	void setup() {
		board = new Board();
	}

	@Test
	void whiteAlwaysBegins() {
		// When
		var player = board.getPlayer();

		// Then
		assertEquals(WHITE, player);
	}

	@Test
	void after_1_move() {
		// Given
		var oldPosition = new BoardPosition('E', 2);
		var newPosition = new BoardPosition('E', 4);
		var pieceToMove = board.getPiece(oldPosition).orElseThrow();
		board.move(pieceToMove, newPosition);

		// When
		var player = board.getPlayer();

		// Then
		assertEquals(BLACK, player);
	}

	@Test
	void after_2_moves() {
		// Given
		var piece_white = board.getPiece(new BoardPosition('E', 2)).orElseThrow();
		board.move(piece_white, new BoardPosition('E', 4));
		var piece_black = board.getPiece(new BoardPosition('E', 7)).orElseThrow();
		board.move(piece_black, new BoardPosition('E', 5));

		// When
		var player = board.getPlayer();

		// Then
		assertEquals(WHITE, player);
	}

	public static Stream<Arguments> basedOfFenParams() {
		return Stream.of(
				Arguments.of("rnbqkbnr/pppppppp/8/8/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1", WHITE),
				Arguments.of("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", BLACK)
		);
	}

	@ParameterizedTest
	@MethodSource("basedOfFenParams")
	void basedOfFen(String fenString, Player result) {
		// Given
		board = new Board(fenString);

		// When
		var player = board.getPlayer();

		// Then
		assertEquals(result, player);
	}
}
