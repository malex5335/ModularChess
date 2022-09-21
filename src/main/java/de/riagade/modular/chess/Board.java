package de.riagade.modular.chess;

import lombok.*;

import java.util.*;
import java.util.stream.*;

import static de.riagade.modular.chess.FenHelper.*;

@Getter
@Setter
public class Board {
	public static final String INITIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private List<Piece> pieces;
	private int halfMoves;
	private int moves;
	private Player player;
	private Castling castling;
	private EnPassant enPassant;

	public Board() {
		setMoves(0);
		setPieces(new ArrayList<>());
		loadFenSettings(this, INITIAL_FEN);
	}

	public Board(String fen) {
		setMoves(0);
		setPieces(new ArrayList<>());
		loadFenSettings(this, fen);
	}

	public Piece createPieceAt(char value, char x, int y) {
		var position = new BoardPosition(x, y);
		if(getPiece(position).isEmpty()) {
			var piece = new Piece(PieceType.from(value), position);
			getPieces().add(piece);
			return piece;
		}
		throw new UnsupportedOperationException("a piece already exists in this place");
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
		nextPlayer();
	}

	private void nextPlayer() {
		switch(getPlayer()) {
			case WHITE -> setPlayer(Player.BLACK);
			case BLACK -> setPlayer(Player.WHITE);
		}
	}
}
