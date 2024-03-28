package server;

import server.characters.FightCharacter;
import server.characters.Hunter;
import server.characters.PlayerCharacter;
import server.characters.Vampire;
import server.characters.Lycanthrope;

public class ChallengeResult {
    private String id;
    private final String attackingPlayerId, attackedPlayerId;
    private int bet, turns;
    private int attackerMinionsLeft, attackedMinionsLeft;
    private boolean winnerAttacking = true;

    public ChallengeResult(Player attackingPlayer, Player attackedPlayer, int bet)
    {
        this.bet = bet;
        this.attackingPlayerId = attackingPlayer.getId();
        this.attackedPlayerId = attackedPlayer.getId();

        PlayerCharacter attackingPlayerCharacter = attackingPlayer.getCharacter();
        PlayerCharacter attackedPlayerCharacter = attackedPlayer.getCharacter();

        FightCharacter attackingCharacter = createFightCharacterFromCharacter(attackingPlayerCharacter);
        FightCharacter attackedCharacter = createFightCharacterFromCharacter(attackedPlayerCharacter);

        attackerMinionsLeft = attackingPlayerCharacter.getMinionCount();
        attackedMinionsLeft = attackedPlayerCharacter.getMinionCount();


        if(attackedCharacter != null) {
            calculateDuel(attackingCharacter, attackedCharacter);
            attackerMinionsLeft -= attackingPlayerCharacter.calculateMinionsKilledAfterDamage(attackingCharacter.getReceivedDamage());
            attackedMinionsLeft -= attackedPlayerCharacter.calculateMinionsKilledAfterDamage(attackedCharacter.getReceivedDamage());
        }

        //Quitar oro
    }

    public ChallengeResult(Player attackingPlayer, Player attackedPlayer, int bet, boolean deniedFromOperator)
    {
        this.attackingPlayerId = attackingPlayer.getId();
        this.attackedPlayerId = attackedPlayer.getId();
        this.winnerAttacking = !deniedFromOperator;
        this.turns = -1;
        this.bet = bet;

        //quitar oro
    }

    public FightCharacter createFightCharacterFromCharacter(PlayerCharacter character)
    {
        return switch (character.getBreed()) {
            case Hunter -> new Hunter(character);
            case Vampire -> new Vampire(character);
            case Lycanthrope -> new Lycanthrope(character);
            default -> null;
        };
    }

    private void calculateDuel(FightCharacter attackingCharacter, FightCharacter attackedCharacter)
    {
        FightCharacter attacker = attackingCharacter;
        FightCharacter defender = attackedCharacter;

        while (!defender.isDead())
        {
            calculateTurn(attacker, defender);

            FightCharacter aux = attacker;
            attacker = defender;
            defender = aux;

            this.turns++;
        }

        this.winnerAttacking = attacker == attackingCharacter;
    }

    private void calculateTurn(FightCharacter attacker, FightCharacter defender)
    {
        int dmg = attacker.calculateDamage();
        int dfs = defender.calculateDefense();

        if(dmg >= dfs)
            defender.receiveDamage();
    }

    public String getAttackingPlayerId() {
        return this.attackingPlayerId;
    }

    public String getAttackedPlayerId() {
        return this.attackedPlayerId;
    }

    public int getBet() {
        return this.bet;
    }

    public int getTurns() {
        return this.turns;
    }

    public boolean isWinnerAttacking() {
        return this.winnerAttacking;
    }

    public int getAttackerMinionsLeft() {
        return attackerMinionsLeft;
    }

    public void setAttackerMinionsLeft(int attackerMinionsLeft) {
        this.attackerMinionsLeft = attackerMinionsLeft;
    }

    public int getAttackedMinionsLeft() {
        return attackedMinionsLeft;
    }

    public void setAttackedMinionsLeft(int attackedMinionsLeft) {
        this.attackedMinionsLeft = attackedMinionsLeft;
    }

    public String getId(){return id;}

    public void setId(String id){this.id = id;}
}
