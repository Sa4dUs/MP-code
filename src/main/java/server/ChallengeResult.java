package server;

import server.characters.FightCharacter;
import server.characters.Hunter;
import server.characters.PlayerCharacter;
import server.characters.Vampire;
import server.characters.Lycanthrope;

public class ChallengeResult {
    private final Player attackingPlayer, attackedPlayer;
    private int bet, turns;
    private int attackerMinionsLeft, attackedMinionsLeft;
    private boolean winnerAttacking = true;

    public ChallengeResult(ChallengeRequest request, PlayerCharacter otherCharacter, int bet)
    {
        this.bet = bet;

        this.attackingPlayer = request.getAttackingPlayer();
        this.attackedPlayer = request.getAttackedPlayer();

        PlayerCharacter attackingPlayerCharacter = request.getAttackingCharacter();


        FightCharacter attackingCharacter = createFightCharacterFromCharacter(attackingPlayerCharacter);
        FightCharacter attackedCharacter = createFightCharacterFromCharacter(otherCharacter);

        attackerMinionsLeft = attackingPlayerCharacter.getMinionCount();
        attackedMinionsLeft = otherCharacter.getMinionCount();


        if(attackedCharacter != null) {
            calculateDuel(attackingCharacter, attackedCharacter);
            attackerMinionsLeft -= attackingPlayerCharacter.calculateMinionsKilledAfterDamage(attackingCharacter.getReceivedDamage());
            attackedMinionsLeft -= otherCharacter.calculateMinionsKilledAfterDamage(attackedCharacter.getReceivedDamage());
        }

        if(this.winnerAttacking) {
            otherCharacter.removeGold(this.bet);
            return;
        }

        request.getAttackingCharacter().removeGold(this.bet);
    }

    public ChallengeResult(ChallengeRequest request)
    {
        this.attackingPlayer = request.getAttackingPlayer();
        this.attackedPlayer = request.getAttackedPlayer();

        FightCharacter attackingCharacter = createFightCharacterFromCharacter(request.getAttackingCharacter());
        FightCharacter attackedCharacter = null;

        request.getAttackingCharacter().removeGold(bet);
    }

    private FightCharacter createFightCharacterFromCharacter(PlayerCharacter character)
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

    public Player getAttackingPlayer() {
        return this.attackingPlayer;
    }

    public Player getAttackedPlayer() {
        return this.attackedPlayer;
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

}
