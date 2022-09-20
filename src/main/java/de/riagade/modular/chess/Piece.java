package de.riagade.modular.chess;

import lombok.*;

@Getter
@Setter
public class Piece {
	private BoardPosition position;
	private PieceType pieceType;

	public Piece(PieceType pieceType, BoardPosition position){
		setPieceType(pieceType);
		setPosition(position);
	}
}
