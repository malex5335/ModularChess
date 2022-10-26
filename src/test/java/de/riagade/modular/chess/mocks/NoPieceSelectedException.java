package de.riagade.modular.chess.mocks;

public class NoPieceSelectedException extends UnsupportedOperationException {
	public NoPieceSelectedException(String message) {
		super(message);
	}
}
