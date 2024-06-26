package server;

import server.characters.FightCharacter;
import server.characters.Hunter;
import server.characters.PlayerCharacter;
import server.characters.Vampire;
import server.characters.Lycanthrope;
import server.nosql.Document;
import server.nosql.JSONable;
import server.nosql.Schemas.ChallengeResultSchema;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChallengeResult implements JSONable {
    private String id;
    private String attackerId, attackedId;
    private int bet;
    private int turns = 1;
    private int attackerMinionsLeft, attackedMinionsLeft;
    private boolean winnerAttacking = true;
    private String date;
    private List<String> history = new ArrayList<>();

    public ChallengeResult(){};

    public ChallengeResult(Player attackingPlayer, Player attackedPlayer, int bet)
    {
        this.bet = bet;
        this.attackerId = attackingPlayer.getId();
        this.attackedId = attackedPlayer.getId();
        this.history = new ArrayList<>();

        this.date = java.time.ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm"));

        PlayerCharacter attackingPlayerCharacter = attackingPlayer.getCharacter();
        PlayerCharacter attackedPlayerCharacter = attackedPlayer.getCharacter();

        FightCharacter attackingCharacter = createFightCharacterFromCharacter(attackingPlayerCharacter, attackingPlayer.getName());
        FightCharacter attackedCharacter = createFightCharacterFromCharacter(attackedPlayerCharacter, attackedPlayer.getName());

        this.attackerMinionsLeft = attackingPlayerCharacter.getMinionCount();
        this.attackedMinionsLeft = attackedPlayerCharacter.getMinionCount();


        if(attackedCharacter != null) {
            calculateDuel(attackingCharacter, attackedCharacter);
            this.history.add("Winner is: " + (winnerAttacking ? attackingPlayer.getName() : attackedPlayer.getName()));
            this.attackerMinionsLeft -= attackingPlayerCharacter.calculateMinionsKilledAfterDamage(attackingCharacter.getReceivedDamage());
            this.attackedMinionsLeft -= attackedPlayerCharacter.calculateMinionsKilledAfterDamage(attackedCharacter.getReceivedDamage());
        }

    }

    public ChallengeResult(Player attackingPlayer, Player attackedPlayer, int bet, boolean deniedFromOperator)
    {
        this.attackerId = attackingPlayer.getId();
        this.attackedId = attackedPlayer.getId();
        this.winnerAttacking = !deniedFromOperator;
        this.turns = -1;
        this.bet = bet;

        this.date = java.time.ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm"));

        //quitar oro
    }

    public FightCharacter createFightCharacterFromCharacter(PlayerCharacter character, String playerName)
    {
        return switch (character.getBreed()) {
            case Hunter -> new Hunter(character, playerName);
            case Vampire -> new Vampire(character, playerName);
            case Lycanthrope -> new Lycanthrope(character, playerName);
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

            ++this.turns;
        }
        this.winnerAttacking = attacker == attackingCharacter;
    }

    private void calculateTurn(FightCharacter attacker, FightCharacter defender)
    {
        int dmg = attacker.calculateDamage();
        int dfs = defender.calculateDefense();

        if(dmg >= dfs)
        {
            defender.receiveDamage();
            attacker.dealtDamage();
        }
        
        history.add("Turn " + this.turns + ":");
        history.add(attacker.getLastTurn());
        history.add(defender.getLastTurn());
    }

    public String getAttackerId() {
        return this.attackerId;
    }

    public String getAttackedPlayerId() {
        return this.attackedId;
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

    public int getAttackedMinionsLeft() {
        return attackedMinionsLeft;
    }

    public String getDate(){return this.date;}

    public String getId(){
        if (id == null)
            this.getDocument();
        return id;
    }

    public void setId(String id){this.id = id;}

    public List<String> getHistory() {
        return history;
    }

    @Override
    public Document getDocument() {
        Document document = new Document(new ChallengeResultSchema());
        if (this.id != null)
            document.setProperty("id", this.id);
        else
            this.id = document.getId();
        document.setProperty("bet", this.bet);
        document.setProperty("attackerId", this.attackerId);
        document.setProperty("attackedId", this.attackedId);
        document.setProperty("turns", this.turns);
        document.setProperty("attackerMinionsLeft", this.attackerMinionsLeft);
        document.setProperty("attackedMinionsLeft", this.attackedMinionsLeft);
        document.setProperty("winnerAttacking",this.winnerAttacking);
        document.setProperty("history", this.history.toArray(new String[0]));
        document.setProperty("date", this.date);
        return document;
    }
}
