package server;

import server.characters.*;

public class ChallengeResult {
    private final Player attackingPlayer, attackedPlayer;
    private final FightCharacter attackingCharacter, attackedCharacter;
    private int bet, turns;

    private boolean winnerAttacking = true;

    public ChallengeResult(ChallengeRequest request, PlayerCharacter otherCharacter)
    {
        attackingPlayer = request.getAttackingPlayer();
        attackedPlayer = request.getAttackedPlayer();

        attackingCharacter = createFightCharacterFromCharacter(request.getAttackingCharacter());
        attackedCharacter = createFightCharacterFromCharacter(otherCharacter);

        if(attackedCharacter != null)
            calculateDuel();

        if(winnerAttacking)
            otherCharacter.removeGold(bet);
        else
            request.getAttackingCharacter().removeGold(bet);
    }

    public ChallengeResult(ChallengeRequest request)
    {
        attackingPlayer = request.getAttackingPlayer();
        attackedPlayer = request.getAttackedPlayer();

        attackingCharacter = createFightCharacterFromCharacter(request.getAttackingCharacter());
        attackedCharacter = null;

        request.getAttackingCharacter().removeGold(bet);
    }

    private FightCharacter createFightCharacterFromCharacter(PlayerCharacter character)
    {
        return switch (character.getBreed()) {
            case Hunter -> new Hunter(character);
            case Vampire -> new Vampire(character);
            case Licantrope -> new Licantrope(character);
            default -> null;
        };
    }

    private void calculateDuel()
    {
        FightCharacter attacker = attackingCharacter, defender = attackedCharacter;

        while (!defender.isDead())
        {
            calculateTurn(attacker, defender);

            FightCharacter aux = attacker;
            attacker = defender;
            defender = aux;

            turns ++;
        }

        winnerAttacking = attacker == attackingCharacter;
    }

    private void calculateTurn(FightCharacter attacker, FightCharacter defender)
    {
        int dmg = attacker.calculateDamage();
        int dfs = defender.calculateDefense();

        if(dmg >= dfs)
            defender.receiveDamage();
    }

    public Player getAttackingPlayer() {
        return attackingPlayer;
    }

    public Player getAttackedPlayer() {
        return attackedPlayer;
    }

    public FightCharacter getAttackingCharacter() {
        return attackingCharacter;
    }

    public FightCharacter getAttackedCharacter() {
        return attackedCharacter;
    }

    public int getBet() {
        return bet;
    }

    public int getTurns() {
        return turns;
    }

    public boolean isWinnerAttacking() {
        return winnerAttacking;
    }

}
