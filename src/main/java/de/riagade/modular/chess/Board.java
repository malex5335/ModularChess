package de.riagade.modular.chess;

import de.riagade.modular.chess.pieces.*;
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

	public void createPieceAt(char value, char x, int y) {
		var position = new BoardPosition(x, y);
		if(getPiece(position).isPresent())
			throw new UnsupportedOperationException("a piece already exists in this place");
		var piece = createPiece(PieceType.from(value), position);
		getPieces().add(piece);
	}

	private Piece createPiece(PieceType type, BoardPosition position) {
		return switch (type) {
			case ROOK_W, ROOK_B -> new Rook(type, position);
			case KNIGHT_W, KNIGHT_B -> new Knight(type, position);
			case BISHOP_W, BISHOP_B -> new Bishop(type, position);
			case QUEEN_W, QUEEN_B -> new Queen(type, position);
			case KING_W, KING_B -> new King(type, position);
			case PAWN_W, PAWN_B -> new Pawn(type, position);
		};
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

	public boolean move(Piece piece, BoardPosition newPosition) {
		if(!piece.getPlayer().equals(getPlayer()))
			return false;
		if(!piece.canMove(newPosition, this))
			return false;
		piece.setPosition(newPosition);
		setPlayer(nextPlayer());
		return true;
	}

	private Player nextPlayer() {
		return switch(getPlayer()) {
			case WHITE -> Player.BLACK;
			case BLACK -> Player.WHITE;
		};
	}
}
