package server.characters;

public class Hunter extends FightCharacter{
    private final int maxMana;

    public Hunter(PlayerCharacter character) {
        super(character);
        this.maxMana = 3;
        setMana(maxMana);
    }

    @Override
    public void tick() {this.setMana(Math.min(this.getMana() + 1, maxMana)); }

}
