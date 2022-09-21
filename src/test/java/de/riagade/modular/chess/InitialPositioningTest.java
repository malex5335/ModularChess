package de.riagade.modular.chess;

import org.junit.jupiter.api.*;

import java.util.*;

import static de.riagade.modular.chess.PieceType.*;
import static org.junit.jupiter.api.Assertions.*;

class InitialPositioningTest {
	private PieceType pieceType;
	private Board board;

	@BeforeEach
	void setup() {
		board = new Board();
	}

	@Nested
	class White {

		@Test
		void rooks() {
			// Given
			pieceType = ROOK_W;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			var position_left = new BoardPosition('A', 1);
			var position_right = new BoardPosition('H', 1);
			assertEquals(2, positions.size());
			assertTrue(positions.containsAll(List.of(position_left, position_right)));
		}

		@Test
		void knights() {
			// Given
			pieceType = KNIGHT_W;

			// When
			var positions= board.getPositions(pieceType);

			// Then
			var position_left = new BoardPosition('B', 1);
			var position_right = new BoardPosition('G', 1);
			assertEquals(2, positions.size());
			assertTrue(positions.containsAll(List.of(position_left, position_right)));
		}

		@Test
		void bishops() {
			// Given
			pieceType = BISHOP_W;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			var position_left = new BoardPosition('C', 1);
			var position_right = new BoardPosition('F', 1);
			assertEquals(2, positions.size());
			assertTrue(positions.containsAll(List.of(position_left, position_right)));
		}

		@Test
		void queen() {
			// Given
			pieceType = QUEEN_W;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			var position = new BoardPosition('D', 1);
			assertEquals(1, positions.size());
			assertTrue(positions.contains(position));
		}

		@Test
		void king() {
			// Given
			pieceType = KING_W;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			var position = new BoardPosition('E', 1);
			assertEquals(1, positions.size());
			assertTrue(positions.contains(position));
		}

		@Test
		void pawns() {
			// Given
			pieceType = PAWN_W;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			assertEquals(8, positions.size());
			assertTrue(positions.containsAll(
					List.of(new BoardPosition('A', 2),
							new BoardPosition('B', 2),
							new BoardPosition('C', 2),
							new BoardPosition('D', 2),
							new BoardPosition('E', 2),
							new BoardPosition('F', 2),
							new BoardPosition('G', 2),
							new BoardPosition('H', 2))));
		}
	}

	@Nested
	class Black {

		@Test
		void rooks() {
			// Given
			pieceType = ROOK_B;

			// When
			var position = board.getPositions(pieceType);

			// Then
			var position_left = new BoardPosition('A', 8);
			var position_right = new BoardPosition('H', 8);
			assertEquals(2, position.size());
			assertTrue(position.containsAll(List.of(position_left, position_right)));
		}

		@Test
		void knights() {
			// Given
			pieceType = KNIGHT_B;

			// When
			var positions= board.getPositions(pieceType);

			// Then
			var position_left = new BoardPosition('B', 8);
			var position_right = new BoardPosition('G', 8);
			assertEquals(2, positions.size());
			assertTrue(positions.containsAll(List.of(position_left, position_right)));
		}

		@Test
		void bishops() {
			// Given
			pieceType = BISHOP_B;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			var position_left = new BoardPosition('C', 8);
			var position_right = new BoardPosition('F', 8);
			assertEquals(2, positions.size());
			assertTrue(positions.containsAll(List.of(position_left, position_right)));
		}

		@Test
		void queen() {
			// Given
			pieceType = QUEEN_B;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			var position = new BoardPosition('D', 8);
			assertEquals(1, positions.size());
			assertTrue(positions.contains(position));
		}

		@Test
		void king() {
			// Given
			pieceType = KING_B;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			var position = new BoardPosition('E', 8);
			assertEquals(1, positions.size());
			assertTrue(positions.contains(position));
		}

		@Test
		void pawns() {
			// Given
			pieceType = PAWN_B;

			// When
			var positions = board.getPositions(pieceType);

			// Then
			assertEquals(8, positions.size());
			assertTrue(positions.containsAll(
					List.of(new BoardPosition('A', 7),
							new BoardPosition('B', 7),
							new BoardPosition('C', 7),
							new BoardPosition('D', 7),
							new BoardPosition('E', 7),
							new BoardPosition('F', 7),
							new BoardPosition('G', 7),
							new BoardPosition('H', 7))));
		}
	}

	@Nested
	class FromFen {

		@BeforeEach
		void setup() {
			var fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
			board = new Board(fen);
		}
	}
}
