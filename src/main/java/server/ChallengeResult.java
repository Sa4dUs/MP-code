package server;

import server.characters.FightCharacter;

public class ChallengeResult {
    private Player attackingPlayer, attackedPlayer;
    private FightCharacter attackingCharacter, attackedCharacter;
    private int bet, turns;

    private boolean winnerAttacking;
}
