package server;

import server.characters.FightCharacter;
import server.characters.Hunter;
import server.characters.PlayerCharacter;
import server.characters.Vampire;
import server.characters.Lycanthrope;

public class ChallengeResult {
    private final Player attackingPlayer, attackedPlayer;
    private final FightCharacter attackingCharacter, attackedCharacter;
    private int bet, turns;

    private boolean winnerAttacking = true;

    public ChallengeResult(ChallengeRequest request, PlayerCharacter otherCharacter)
    {
        this.attackingPlayer = request.getAttackingPlayer();
        this.attackedPlayer = request.getAttackedPlayer();

        this.attackingCharacter = createFightCharacterFromCharacter(request.getAttackingCharacter());
        this.attackedCharacter = createFightCharacterFromCharacter(otherCharacter);

        if(this.attackedCharacter != null) {
            calculateDuel();
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

        this.attackingCharacter = createFightCharacterFromCharacter(request.getAttackingCharacter());
        this.attackedCharacter = null;

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

    private void calculateDuel()
    {
        FightCharacter attacker = this.attackingCharacter;
        FightCharacter defender = this.attackedCharacter;

        while (!defender.isDead())
        {
            calculateTurn(attacker, defender);

            FightCharacter aux = attacker;
            attacker = defender;
            defender = aux;

            this.turns++;
        }

        this.winnerAttacking = attacker == this.attackingCharacter;
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

    public FightCharacter getAttackingCharacter() {
        return this.attackingCharacter;
    }

    public FightCharacter getAttackedCharacter() {
        return this.attackedCharacter;
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
