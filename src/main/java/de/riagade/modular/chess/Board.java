package de.riagade.modular.chess;

import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

import java.util.*;
import java.util.stream.*;

import static de.riagade.modular.chess.util.FenUtil.*;

@Getter
@Setter
public class Board {
	public static final String INITIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private List<Piece> pieces = new ArrayList<>();
	private int halfMoves;
	private int moves;
	private Player player;
	private Castling castling;
	private BoardPosition enPassant;
	private List<BoardPosition> allPositions;

	public Board() {
		this(INITIAL_FEN);
	}

	public Board(String fen) {
		loadFenSettings(this, fen);
		generatePositions();
	}

	public void createPieceAt(PieceType pieceType, char x, int y) {
		var position = new BoardPosition(x, y);
		if(getPiece(position).isPresent())
			throw new UnsupportedOperationException("a piece already exists in this place");
		var piece = pieceType.turnIntoPieceAt(position);
		getPieces().add(piece);
	}

	public List<BoardPosition> getPositions(PieceType pieceType) {
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
		if(!piece.getPlayer().equals(getPlayer()))
			throw new UnsupportedOperationException("only the player controlling the piece is allowed to move it");
		if(!piece.getPossibleMoves(this).contains(newPosition))
			throw new UnsupportedOperationException("piece cannot be moved to this position");
		runTakeLogic(newPosition);
		piece.setPosition(newPosition);
		setPlayer(getPlayer().next());
	}

	private void runTakeLogic(BoardPosition newPosition) {
		var potentialPiece = getPiece(newPosition);
		potentialPiece.ifPresent(value -> getPieces().remove(value));
	}

	private void generatePositions() {
		setAllPositions(new ArrayList<>());
		for(var x = 'A'; x <= 'H'; x++) {
			for(var y = 1; y <= 8; y++) {
				var position = new BoardPosition(x,y);
				getAllPositions().add(position);
			}
		}
	}

}
