package de.riagade.modular.chess;

import de.riagade.modular.chess.pieces.util.PieceType;
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

	@Nested
	class Rook {
		BoardPosition from;

		@BeforeEach
		void setup() {
			var fen = "8/8/8/8/8/8/1R6/8 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('B', 2);
		}

		@Test
		void up() {
			//Given
			var rook = board.getPiece(from).orElseThrow();
			var to = new BoardPosition(from.x(), from.y()+1);

			// When
			board.move(rook, to);

			// Then
			assertEquals(to, rook.getPosition());
		}

		@Test
		void down() {
			//Given
			var rook = board.getPiece(from).orElseThrow();
			var to = new BoardPosition(from.x(), from.y() - 1);

			// When
			board.move(rook, to);

			// Then
			assertEquals(to, rook.getPosition());
		}

		@Test
		void left() {
			//Given
			var rook = board.getPiece(from).orElseThrow();
			var to = new BoardPosition((char) (from.x() - 1), from.y());

			// When
			board.move(rook, to);

			// Then
			assertEquals(to, rook.getPosition());
		}

		@Test
		void right() {
			//Given
			var rook = board.getPiece(from).orElseThrow();
			var to = new BoardPosition((char) (from.x() + 1), from.y());

			// When
			board.move(rook, to);

			// Then
			assertEquals(to, rook.getPosition());
		}

		@Test
		void take() {
			var fen = "r7/8/8/8/8/8/8/R7 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('A', 1);
			var to = new BoardPosition('A', 8);
			var rook = board.getPiece(from).orElseThrow();

			// When
			board.move(rook, to);

			// Then
			assertEquals(to, rook.getPosition());
		}

		@Test
		void blocked() {
			//Given
			board = new Board();
			from = new BoardPosition('A', 1);
			var to = new BoardPosition('A', 2);
			var rook = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(rook, to));

			// Then
			assertEquals(from, rook.getPosition());
		}

		@Test
		void notDiagonal() {
			//Given
			var rook = board.getPiece(from).orElseThrow();
			var to = new BoardPosition((char) (from.x() + 1), from.y() + 1);

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(rook, to));

			// Then
			assertEquals(from, rook.getPosition());
		}
	}

	@Nested
	class Queen {
		BoardPosition from;

		@BeforeEach
		void setup() {
			var fen = "8/8/8/8/8/8/1Q6/8 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('B', 2);
		}

		@Test
		void up() {
			//Given
			var queen = board.getPiece(from).orElseThrow();
			var to = new BoardPosition(from.x(), from.y()+1);

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}

		@Test
		void down() {
			//Given
			var queen = board.getPiece(from).orElseThrow();
			var to = new BoardPosition(from.x(), from.y() - 1);

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}

		@Test
		void left() {
			//Given
			var queen = board.getPiece(from).orElseThrow();
			var to = new BoardPosition((char) (from.x() - 1), from.y());

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}

		@Test
		void right() {
			//Given
			var queen = board.getPiece(from).orElseThrow();
			var to = new BoardPosition((char) (from.x() + 1), from.y());

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}

		@Test
		void moveLeftUp() {
			// Given
			var to = new BoardPosition((char) (from.x() - 1), from.y() + 1);
			var queen = board.getPiece(from).orElseThrow();

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}

		@Test
		void moveLeftDown() {
			// Given
			var to = new BoardPosition((char) (from.x() - 1), from.y() - 1);
			var queen = board.getPiece(from).orElseThrow();

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}
		@Test
		void moveRightUp() {
			// Given
			var to = new BoardPosition((char) (from.x() + 1), from.y() + 1);
			var queen = board.getPiece(from).orElseThrow();

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}

		@Test
		void moveRightDown() {
			// Given
			var to = new BoardPosition((char) (from.x() + 1), from.y() - 1);
			var queen = board.getPiece(from).orElseThrow();

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}

		@Test
		void take() {
			var fen = "r7/8/8/8/8/8/8/Q7 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('A', 1);
			var to = new BoardPosition('A', 8);
			var queen = board.getPiece(from).orElseThrow();

			// When
			board.move(queen, to);

			// Then
			assertEquals(to, queen.getPosition());
		}

		@Test
		void blocked() {
			//Given
			board = new Board();
			from = new BoardPosition('D', 1);
			var to = new BoardPosition('D', 2);
			var queen = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(queen, to));

			// Then
			assertEquals(from, queen.getPosition());
		}
	}

	@Nested
	class King {
		BoardPosition from;

		@BeforeEach
		void setup() {
			var fen = "8/8/8/8/8/8/1K6/8 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('B', 2);
		}

		@Test
		void up() {
			//Given
			var king = board.getPiece(from).orElseThrow();
			var to = new BoardPosition(from.x(), from.y()+1);

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}

		@Test
		void down() {
			//Given
			var king = board.getPiece(from).orElseThrow();
			var to = new BoardPosition(from.x(), from.y() - 1);

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}

		@Test
		void left() {
			//Given
			var king = board.getPiece(from).orElseThrow();
			var to = new BoardPosition((char) (from.x() - 1), from.y());

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}

		@Test
		void right() {
			//Given
			var king = board.getPiece(from).orElseThrow();
			var to = new BoardPosition((char) (from.x() + 1), from.y());

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}

		@Test
		void moveLeftUp() {
			// Given
			var to = new BoardPosition((char) (from.x() - 1), from.y() + 1);
			var king = board.getPiece(from).orElseThrow();

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}

		@Test
		void moveLeftDown() {
			// Given
			var to = new BoardPosition((char) (from.x() - 1), from.y() - 1);
			var king = board.getPiece(from).orElseThrow();

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}
		@Test
		void moveRightUp() {
			// Given
			var to = new BoardPosition((char) (from.x() + 1), from.y() + 1);
			var king = board.getPiece(from).orElseThrow();

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}

		@Test
		void moveRightDown() {
			// Given
			var to = new BoardPosition((char) (from.x() + 1), from.y() - 1);
			var king = board.getPiece(from).orElseThrow();

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}

		@Test
		void take() {
			var fen = "8/8/8/8/8/8/r7/K7 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('A', 1);
			var to = new BoardPosition('A', 2);
			var king = board.getPiece(from).orElseThrow();

			// When
			board.move(king, to);

			// Then
			assertEquals(to, king.getPosition());
		}

		@Test
		void blocked() {
			//Given
			board = new Board();
			from = new BoardPosition('E', 1);
			var to = new BoardPosition('E', 2);
			var king = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(king, to));

			// Then
			assertEquals(from, king.getPosition());
		}

		@Test
		void notMoreThan1() {
			var fen = "8/8/8/8/8/8/8/K7 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('A', 1);
			var to = new BoardPosition('A', 3);
			var king = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(king, to));

			// Then
			assertEquals(from, king.getPosition());
		}

		@Nested
		class Castling {

			@Nested
			class Allowed {
				@BeforeEach
				void setup() {
					var fen = "8/8/8/8/8/8/8/R3K2R w KQkq - 0 1";
					board = new Board(fen);
				}

				@Test
				void long_castle() {
					// Given
					from = new BoardPosition('E', 1);
					var to = new BoardPosition('C', 1);
					var king = board.getPiece(from).orElseThrow();

					// When
					board.move(king, to);

					// Then
					assertEquals(to, king.getPosition());
					var rook1 = board.getPiece(new BoardPosition('D', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook1.getPieceType());
					var rook2 = board.getPiece(new BoardPosition('H', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook2.getPieceType());
					assertEquals(CastlingOptions.NONE, board.getCastling().getCastlingOptions().get(Player.WHITE));
				}

				@Test
				void short_castle() {
					// Given
					from = new BoardPosition('E', 1);
					var to = new BoardPosition('G', 1);
					var king = board.getPiece(from).orElseThrow();

					// When
					board.move(king, to);

					// Then
					assertEquals(to, king.getPosition());
					var rook1 = board.getPiece(new BoardPosition('F', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook1.getPieceType());
					var rook2 = board.getPiece(new BoardPosition('A', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook2.getPieceType());
					assertEquals(CastlingOptions.NONE, board.getCastling().getCastlingOptions().get(Player.WHITE));
				}
			}

			@Nested
			class NotAllowed {
				@BeforeEach
				void setup() {
					var fen = "8/8/8/8/8/8/8/R3K2R w - - 0 1";
					board = new Board(fen);
				}

				@Test
				void long_castle() {
					// Given
					from = new BoardPosition('E', 1);
					var to = new BoardPosition('C', 1);
					var king = board.getPiece(from).orElseThrow();

					// When
					assertThrows(UnsupportedOperationException.class, () -> board.move(king, to));

					// Then
					assertEquals(from, king.getPosition());
					var rook1 = board.getPiece(new BoardPosition('A', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook1.getPieceType());
					var rook2 = board.getPiece(new BoardPosition('H', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook2.getPieceType());
					assertEquals(CastlingOptions.NONE, board.getCastling().getCastlingOptions().get(Player.WHITE));
				}

				@Test
				void short_castle() {
					// Given
					from = new BoardPosition('E', 1);
					var to = new BoardPosition('G', 1);
					var king = board.getPiece(from).orElseThrow();

					// When
					assertThrows(UnsupportedOperationException.class, () -> board.move(king, to));

					// Then
					assertEquals(from, king.getPosition());
					var rook1 = board.getPiece(new BoardPosition('A', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook1.getPieceType());
					var rook2 = board.getPiece(new BoardPosition('H', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook2.getPieceType());
					assertEquals(CastlingOptions.NONE, board.getCastling().getCastlingOptions().get(Player.WHITE));
				}
			}

			@Nested
			class Blocked {
				@BeforeEach
				void setup() {
					var fen = "8/8/8/8/8/8/8/RN2K1NR w KQkq - 0 1";
					board = new Board(fen);
				}

				@Test
				void long_castle() {
					// Given
					from = new BoardPosition('E', 1);
					var to = new BoardPosition('C', 1);
					var king = board.getPiece(from).orElseThrow();

					// When
					assertThrows(UnsupportedOperationException.class, () -> board.move(king, to));

					// Then
					assertEquals(from, king.getPosition());
					var rook1 = board.getPiece(new BoardPosition('A', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook1.getPieceType());
					var rook2 = board.getPiece(new BoardPosition('H', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook2.getPieceType());
					assertEquals(CastlingOptions.BOTH, board.getCastling().getCastlingOptions().get(Player.WHITE));
				}

				@Test
				void short_castle() {
					// Given
					from = new BoardPosition('E', 1);
					var to = new BoardPosition('G', 1);
					var king = board.getPiece(from).orElseThrow();

					// When
					assertThrows(UnsupportedOperationException.class, () -> board.move(king, to));

					// Then
					assertEquals(from, king.getPosition());
					var rook1 = board.getPiece(new BoardPosition('A', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook1.getPieceType());
					var rook2 = board.getPiece(new BoardPosition('H', 1)).orElseThrow();
					assertEquals(PieceType.ROOK_W, rook2.getPieceType());
					assertEquals(CastlingOptions.BOTH, board.getCastling().getCastlingOptions().get(Player.WHITE));
				}
			}
		}
	}

	@Nested
	class Knight {
		BoardPosition from;
		@BeforeEach
		void setup() {
			var fen = "8/8/8/8/3N4/8/8/8 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('D', 4);
		}

		@Test
		void moveLeftUpUp() {
			// Given
			var to = new BoardPosition((char) (from.x() - 1), from.y() + 2);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}

		@Test
		void moveLeftUpDown() {
			// Given
			var to = new BoardPosition((char) (from.x() - 2), from.y() + 1);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}
		@Test
		void moveLeftDownUp() {
			// Given
			var to = new BoardPosition((char) (from.x() - 2), from.y() - 1);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}

		@Test
		void moveLeftDownDown() {
			// Given
			var to = new BoardPosition((char) (from.x() - 1), from.y() - 2);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}

		@Test
		void moveRightDownDown() {
			// Given
			var to = new BoardPosition((char) (from.x() + 1), from.y() - 2);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}

		@Test
		void moveRightDownUp() {
			// Given
			var to = new BoardPosition((char) (from.x() + 2), from.y() - 1);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}

		@Test
		void moveRightUpDown() {
			// Given
			var to = new BoardPosition((char) (from.x() + 2), from.y() + 1);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}

		@Test
		void moveRightUpUp() {
			// Given
			var to = new BoardPosition((char) (from.x() + 1), from.y() + 2);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}

		@Test
		void movementBlocked() {
			// Given
			var fen = "8/8/4P3/8/3N4/8/8/8 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('D', 4);
			var to = new BoardPosition((char) (from.x() + 1), from.y() + 2);
			var knight = board.getPiece(from).orElseThrow();

			// When
			assertThrows(UnsupportedOperationException.class, () -> board.move(knight, to));

			// Then
			assertEquals(from, knight.getPosition());
		}

		@Test
		void takeEnemy() {
			// Given
			var fen = "8/8/4p3/8/3N4/8/8/8 w KQkq - 0 1";
			board = new Board(fen);
			from = new BoardPosition('D', 4);
			var to = new BoardPosition((char) (from.x() + 1), from.y() + 2);
			var knight = board.getPiece(from).orElseThrow();

			// When
			board.move(knight, to);

			// Then
			assertEquals(to, knight.getPosition());
		}
	}
}
