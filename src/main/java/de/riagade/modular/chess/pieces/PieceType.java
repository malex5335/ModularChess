package de.riagade.modular.chess.pieces;

import de.riagade.modular.chess.*;
import lombok.*;

@Getter
public enum PieceType {
	ROOK_W('R'), KNIGHT_W('N'), BISHOP_W('B'), QUEEN_W('Q'), KING_W('K'), PAWN_W('P'),
	ROOK_B('r'), KNIGHT_B('n'), BISHOP_B('b'), QUEEN_B('q'), KING_B('k'), PAWN_B('p');

	private final char value;

	PieceType(char value) {
		this.value = value;
	}

	public static PieceType from(char value) {
		for(var piece : values()) {
			if(piece.value == value) {
				return piece;
			}
		}
		throw new UnsupportedOperationException("no piece found by this value");
	}

	public Piece createPiece(BoardPosition position) {
		var type = this;
		return switch (type) {
			case ROOK_W, ROOK_B -> new Rook(type, position);
			case KNIGHT_W, KNIGHT_B -> new Knight(type, position);
			case BISHOP_W, BISHOP_B -> new Bishop(type, position);
			case QUEEN_W, QUEEN_B -> new Queen(type, position);
			case KING_W, KING_B -> new King(type, position);
			case PAWN_W, PAWN_B -> new Pawn(type, position);
		};
	}
}
