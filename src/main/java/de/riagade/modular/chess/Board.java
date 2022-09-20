package de.riagade.modular.chess;

import lombok.*;

import java.util.*;
import java.util.stream.*;

@Getter
@Setter
public class Board {
	public static final String INITIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private List<Piece> pieces;
	private int moves;

	public Board() {
		setMoves(0);
		setPieces(new ArrayList<>());
		getPieces().addAll(fromFenString(INITIAL_FEN));
	}

	public List<Piece> fromFenString(String fen) {
		var xStart = 'A';
		var yStart = 8;
		var x = xStart;
		var y = yStart;
		var pieces = new ArrayList<Piece>();
		for(var pos : fen.toCharArray()) {
			if(Character.isDigit(pos)) {
				x += pos;
			} else if(Character.isLetter(pos)) {
				pieces.add(createPiece(pos, x, y));
				x++;
			} else if(pos == '/') {
				x = xStart;
				y--;
			} else {
				break;
			}
		}
		return pieces;
	}

	private Piece createPiece(char value, char x, int y) {
		return new Piece(PieceType.from(value), new BoardPosition(x, y));
	}

	public List<BoardPosition> getPositions(PieceType pieceType) {
		if(pieces.isEmpty())
			return Collections.emptyList();
		return pieces.stream()
				.filter(p -> pieceType.equals(p.getPieceType()))
				.map(Piece::getPosition)
				.collect(Collectors.toList());
	}

	public Optional<Piece> getPiece(BoardPosition position) {
		return pieces.stream()
				.filter(p -> position.equals(p.getPosition()))
				.findFirst();
	}

	public void move(Piece piece, BoardPosition newPosition) {
		piece.setPosition(newPosition);
		setMoves(getMoves() + 1);
	}

	public Player getPlayer() {
		return getMoves() % 2 == 0 ? Player.WHITE : Player.BLACK;
	}
}
