package de.riagade.modular.chess;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Castling {
    Map<Player, CastlingOptions> castlingOptions;

    public Castling() {
        setCastlingOptions(new HashMap<>());
        getCastlingOptions().put(Player.WHITE, CastlingOptions.BOTH);
        getCastlingOptions().put(Player.BLACK, CastlingOptions.BOTH);
    }
}
