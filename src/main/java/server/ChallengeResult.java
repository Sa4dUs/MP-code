package server;

import server.characters.*;

public class ChallengeResult {
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


    private final Player attackingPlayer, attackedPlayer;
    private final FightCharacter attackingCharacter, attackedCharacter;
    private int bet, turns;

    private boolean winnerAttacking = true;

    public ChallengeResult(ChallengeRequest request, PlayerCharacter otherCharacter)
    {
        attackingPlayer = request.getAttackingPlayer();
        attackedPlayer = request.getAttackedPlayer();

        attackingCharacter = CreateFightCharacterFromCharacter(request.getAttackingCharacter());
        attackedCharacter = CreateFightCharacterFromCharacter(otherCharacter);

        CalculateDuel();

        if(winnerAttacking)
            otherCharacter.removeGold(bet);
        else
            request.getAttackingCharacter().removeGold(bet);
    }

    private FightCharacter CreateFightCharacterFromCharacter(PlayerCharacter character)
    {
        switch (character.getBreed())
        {
            case Hunter -> { return new Hunter(character); }

            case Vampire -> { return new Vampire(character); }

            case Licantrope -> { return new Licantrope(character); }

            default -> { return  null; }
        }
    }

    private void CalculateDuel()
    {
        FightCharacter attacker = attackingCharacter, defender = attackedCharacter;

        while (!defender.isDead())
        {
            CalculateTurn(attacker, defender);

            FightCharacter aux = attacker;
            attacker = defender;
            defender = aux;

            turns ++;
        }

        if(defender == attackingCharacter)
            winnerAttacking = false;
    }

    private void CalculateTurn(FightCharacter attacker, FightCharacter defender)
    {
        int dmg = attacker.calculateDamage();
        int dfs = defender.calculateDefense();

        if(dmg >= dfs)
            defender.receiveDamage();
    }
}
