package server.characters;

public class Vampire extends FightCharacter{
    private int maxMana, age;

    public Vampire(PlayerCharacter character) {
        super(character);
        this.maxMana = 10;
        setMana(maxMana);
    }

    @Override
    public void tick() {this.setMana(Math.min(this.getMana() + 1, maxMana)); }
}
