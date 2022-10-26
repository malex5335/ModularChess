package de.riagade.modular.chess.mocks;

import de.riagade.modular.chess.*;
import lombok.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
@Setter
public class GameMock implements BaseGame {
	Board board;
	int moveNumber;
	List<Movement> moves;
	public record Movement(BoardPosition from, BoardPosition to){}
	long millisDuration;

	public GameMock() {
		setBoard(new Board());
		setMoveNumber(0);
		setMoves(new LinkedList<>());
		setMillisDuration(0);
	}

	@Override
	public Player runGame() {
		var start = Instant.now();
		var winner = BaseGame.super.runGame();
		var end = Instant.now();
		setMillisDuration(ChronoUnit.MILLIS.between(start, end));
		return winner;
	}

	@Override
	public BoardPosition getFrom() {
		return getCurrentMovement().from();
	}

	@Override
	public BoardPosition getTo() {
		return getCurrentMovement().to();
	}

	@Override
	public void updateBoard() {
		setMoveNumber(getMoveNumber() + 1);
	}

	@Override
	public void noPieceSelectedError() {
		var from = getCurrentMovement().from();
		var message = "There was no piece found on %s; please use another field";
		throw new NoPieceSelectedException(String.format(message, toString(from)));
	}

	@Override
	public void wrongPlayerError() {
		var from = getCurrentMovement().from();
		var message = "The piece found on %s does not belong to you; please use another field";
		throw new WrongPlayerException(String.format(message, toString(from)));
	}

	@Override
	public void moveNotPossibleError() {
		var from = getCurrentMovement().from();
		var to = getCurrentMovement().to();
		var message = "The piece found on %s is not allowed to move to %s; please use another piece or field";
		throw new MoveNotPossibleException(String.format(message, toString(from), toString(to)));
	}

	private Movement getCurrentMovement() {
		return getMoves().get(getMoveNumber()-1);
	}

	private String toString(BoardPosition position) {
		return String.valueOf(position.x()) + position.y();
	}
}
