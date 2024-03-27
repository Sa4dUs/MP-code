package server.characters;

public class Lycanthrope extends FightCharacter{
    private int maxMana;

    public Lycanthrope(PlayerCharacter character) {
        super(character);
        this.maxMana = 3;
        setMana(maxMana);
    }

    @Override
    public void tick() {this.setMana(Math.min(this.getMana() + 1, maxMana)); }
}
