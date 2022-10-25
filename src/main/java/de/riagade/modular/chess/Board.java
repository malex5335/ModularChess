package de.riagade.modular.chess;

import de.riagade.modular.chess.pieces.util.*;
import lombok.*;

import java.util.*;
import java.util.stream.*;

import static de.riagade.modular.chess.util.FenUtil.*;

@Getter
@Setter
public class Board {
	private List<Piece> pieces = new ArrayList<>();
	private int halfMoves;
	private int moves;
	private Player player;
	private Castling castling;
	private BoardPosition enPassant;
	private List<BoardPosition> allPositions;

	public Board() {
		this(DEFAULT_FEN);
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
		runCastlingLogic(piece, newPosition);
		alterCastlingOptions(piece);
		piece.setPosition(newPosition);
		setPlayer(getPlayer().next());
		setMoves(getMoves() + 1);
	}

	private void alterCastlingOptions(Piece piece) {
		var castlingOptions = getCastling().getCastlingOptions();
		var optionBefore = castlingOptions.get(getPlayer());
		if(optionBefore.equals(CastlingOptions.NONE))
			return;
		var newOption = switch (piece.getPieceType()) {
			case KING_W, KING_B -> CastlingOptions.NONE;
			case ROOK_W, ROOK_B -> getOtherSide(piece);
			default -> optionBefore;
		};
		castlingOptions.put(getPlayer(), newOption);
	}

	private CastlingOptions getOtherSide(Piece piece) {
		var leftSide = new BoardPosition((char) (piece.getPosition().x() - 1), piece.getPosition().y());
		var otherSide = CastlingOptions.KING;
		if(getAllPositions().contains(leftSide)) {
			otherSide = CastlingOptions.QUEEN;
		}
		return otherSide;
	}

	private void runCastlingLogic(Piece piece, BoardPosition newPosition) {
		var pieceType = piece.getPieceType();
		if(!(PieceType.KING_B.equals(pieceType) ||
				PieceType.KING_W.equals(pieceType)))
			return;
		var options = getCastling().getCastlingOptions().get(getPlayer());
		if(options.equals(CastlingOptions.NONE))
			return;
		if(!directionAllowed(piece.getPosition(), newPosition, options))
			return;
		castleRookNextTo(newPosition);
	}

	private void castleRookNextTo(BoardPosition kingPosition) {
		var rookType = getPlayer().equals(Player.WHITE) ? PieceType.ROOK_W : PieceType.ROOK_B;
		var positions = getPositions(rookType);
		if(positions.isEmpty())
			return;
		var rook = getClosestOnX(positions, kingPosition);
		var rookX = rook.getPosition().x();
		var kingX = kingPosition.x();
		var kingY = kingPosition.y();
		if(rookX > kingX) {
			rookX = (char) (kingX-1);
		} else {
			rookX = (char) (kingX+1);
		}
		rook.setPosition(new BoardPosition(rookX, kingY));
	}

	public Piece getClosestOnX(List<BoardPosition> positions, BoardPosition targetPosition) {
		positions.sort(Comparator.comparingInt(a -> Math.abs(a.x() - targetPosition.x())));
		var closestPosition = positions.get(0);
		return getPiece(closestPosition).orElseThrow();
	}

	private boolean directionAllowed(BoardPosition from, BoardPosition to, CastlingOptions castlingOptions) {
		var directionX = to.x() - from.x();
		var directionY = to.y() - from.y();
		if(directionY != 0)
			return false;
		return switch (castlingOptions){
			case KING -> directionX > 0;
			case QUEEN -> directionX < 0;
			case BOTH -> directionX != 0;
			default -> false;
		};
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

	public boolean isCheckAt(BoardPosition position) {
		var nextPlayer = getPlayer().next();
		var allEnemyPieces = getPieces(nextPlayer);
		var enemyMoves = getAvailableMoves(allEnemyPieces);
		return enemyMoves.contains(position);
	}

	private Piece getKing(Player player) {
		return getPieces(player).stream()
				.filter(p -> p.getPieceType().equals(PieceType.KING_W) ||
						p.getPieceType().equals(PieceType.KING_B))
				.findFirst()
				.orElseThrow(() -> new UnsupportedOperationException("could not find a King for this player"));
	}

	public List<Piece> getPieces(Player player) {
		return getPieces().stream()
				.filter(p -> p.getPlayer().equals(player))
				.toList();
	}

	public List<BoardPosition> getAvailableMoves(List<Piece> pieces) {
		return pieces.stream()
				.map(p -> p.getPossibleMoves(this))
				.flatMap(List::stream)
				.toList();
	}

	public boolean isGameOver() {
		return isCheckMate() || isStaleMate();
	}

	public boolean isCheck() {
		var myKing = getKing(getPlayer());
		return isCheckAt(myKing.getPosition());
	}

	public boolean isCheckMate() {
		var myKing = getKing(getPlayer());
		return isCheck() && myKing.getPossibleMoves(this).isEmpty();
	}

	public boolean isStaleMate() {
		var myPieces = getPieces(getPlayer());
		var myMoves = getAvailableMoves(myPieces);
		return myMoves.isEmpty();
	}

	public Player getWinner() {
		return isCheckMate() ? getPlayer().next() : Player.NO_ONE;
	}
}
