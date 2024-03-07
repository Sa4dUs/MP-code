package server;

import server.characters.*;
import server.characters.Character;

public class ChallengeResult {
    private Player attackingPlayer, attackedPlayer;
    private FightCharacter attackingCharacter, attackedCharacter;
    private int bet, turns;

    private boolean winnerAttacking = true;

    public ChallengeResult(ChallengeRequest request, PlayerCharacter otherCharacter)
    {
        attackingPlayer = request.getAttackingPlayer();
        attackedPlayer = request.getAttackedPlayer();

        attackingCharacter = CreateFightCharacterFromCharacter(request.getAttackingCharacter());
        attackedCharacter = CreateFightCharacterFromCharacter(otherCharacter);

        CalculateDuel();
    }

    private FightCharacter CreateFightCharacterFromCharacter(PlayerCharacter character)
    {
        switch (character.getBreed())
        {
            case Hunter -> {
                return new Hunter(character);
            }

            case Vampire -> {
                return new Vampire(character);
            }

            case Licantrope -> {
                return new Licantrope(character);
            }

            default -> {
                return  null;
            }
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
            defender.recieveDamage();
    }
}
