package de.riagade.modular.chess;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PieceMovementTest {

	@Nested
	class Pawn {
		private Board board;

		@BeforeEach
		void setup() {
			board = new Board();
		}

		@Test
		void move1Field() {
			// Given
			var from = new BoardPosition('E', 2);
			var to = new BoardPosition('E', 3);
			var pawn = board.getPiece(from).orElseThrow();

			// When
			board.move(pawn, to);

			// Then
			assertEquals(to, pawn.getPosition());
		}

		@Test
		void move2Fields_atFirst() {
			// Given
			var from = new BoardPosition('E', 2);
			var to = new BoardPosition('E', 4);
			var pawn = board.getPiece(from).orElseThrow();

			// When
			board.move(pawn, to);

			// Then
			assertEquals(to, pawn.getPosition());
		}

		@Test
		void move2Fields_notAfterAlreadyMoved() {
			// Given
			var fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2";
			board = new Board(fen);
			var from = new BoardPosition('E', 4);
			var to = new BoardPosition('E', 6);
			var pawn = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(pawn, to));

			// Then
			assertEquals(from, pawn.getPosition());
		}

		@Test
		void cantMove3Fields() {
			// Given
			var from = new BoardPosition('E', 2);
			var to = new BoardPosition('E', 5);
			var pawn = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(pawn, to));

			// Then
			assertEquals(from, pawn.getPosition());
		}

		@Test
		void cantMoveSideways() {
			// Given
			var from = new BoardPosition('E', 2);
			var to = new BoardPosition('D', 3);
			var pawn = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(pawn, to));

			// Then
			assertEquals(from, pawn.getPosition());
		}

		@Test
		void takeSideways() {
			// Given
			var fen = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2";
			board = new Board(fen);
			var from = new BoardPosition('E', 4);
			var to = new BoardPosition('D', 5);
			var pawn = board.getPiece(from).orElseThrow();

			// When
			board.move(pawn, to);

			// Then
			assertEquals(to, pawn.getPosition());
		}

		@Test
		void cantMoveBackwards() {
			// Given
			var fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2";
			board = new Board(fen);
			var from = new BoardPosition('E', 4);
			var to = new BoardPosition('E', 3);
			var pawn = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(pawn, to));

			// Then
			assertEquals(from, pawn.getPosition());
		}

		@Test
		void take_enPassant() {
			// Given
			var fen = "rnbqkbnr/pp1ppppp/8/8/2pP4/8/PPP1PPPP/RNBQKBNR b KQkq d3 0 2";
			board = new Board(fen);
			var from = new BoardPosition('C', 4);
			var to = new BoardPosition('D', 3);
			var pawn = board.getPiece(from).orElseThrow();

			// When
			board.move(pawn, to);

			// Then
			assertEquals(to, pawn.getPosition());
		}
	}
}
