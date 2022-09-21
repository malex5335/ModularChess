package de.riagade.modular.chess.pieces;

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
}
