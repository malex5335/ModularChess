package de.riagade.modular.chess;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PieceMovementTest {
	private Board board;

	@BeforeEach
	void setup() {
		board = new Board();
	}

	@Nested
	class Pawn {

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
			var fen = "8/8/8/8/4P3/8/8/8 w KQkq c6 0 2";
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
			var fen = "8/8/8/3p4/4P3/8/8/8 w KQkq c6 0 2";
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
			var fen = "8/8/8/8/4P3/8/8/8 w KQkq c6 0 2";
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
			var fen = "8/8/8/8/2pP4/8/8/8 b KQkq d3 0 2";
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

	@Nested
	class Bishop {

		@Nested
		class RegularMovement {
			@BeforeEach
			void setup() {
				var fen = "8/8/8/8/8/8/3B4/8 w KQkq - 0 1";
				board = new Board(fen);
			}

			@Test
			void moveLeftUp() {
				// Given
				var from = new BoardPosition('D', 2);
				var to = new BoardPosition('C', 3);
				var bishop = board.getPiece(from).orElseThrow();

				// When
				board.move(bishop, to);

				// Then
				assertEquals(to, bishop.getPosition());
			}

			@Test
			void moveLeftDown() {
				// Given
				var from = new BoardPosition('D', 2);
				var to = new BoardPosition('C', 1);
				var bishop = board.getPiece(from).orElseThrow();

				// When
				board.move(bishop, to);

				// Then
				assertEquals(to, bishop.getPosition());
			}
			@Test
			void moveRightUp() {
				// Given
				var from = new BoardPosition('D', 2);
				var to = new BoardPosition('E', 3);
				var bishop = board.getPiece(from).orElseThrow();

				// When
				board.move(bishop, to);

				// Then
				assertEquals(to, bishop.getPosition());
			}

			@Test
			void moveRightDown() {
				// Given
				var from = new BoardPosition('D', 2);
				var to = new BoardPosition('E', 1);
				var bishop = board.getPiece(from).orElseThrow();

				// When
				board.move(bishop, to);

				// Then
				assertEquals(to, bishop.getPosition());
			}
		}

		@Test
		void movementBlocked() {
			// Given
			var fen = "8/8/8/8/8/8/3B4/4K3 w KQkq - 0 1";
			board = new Board(fen);
			var from = new BoardPosition('D', 2);
			var to = new BoardPosition('E', 1);
			var bishop = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(bishop, to));

			// Then
			assertEquals(from, bishop.getPosition());
		}

		@Test
		void takeEnemy() {
			// Given
			var fen = "8/8/8/6p1/8/8/8/2B5 w KQkq - 0 1";
			board = new Board(fen);
			var from = new BoardPosition('C', 1);
			var to = new BoardPosition('G', 5);
			var bishop = board.getPiece(from).orElseThrow();

			// When
			board.move(bishop, to);

			// Then
			assertEquals(to, bishop.getPosition());
		}
	}
}
