package server.characters;

public class Vampire extends FightCharacter{
    private int maxMana, age;

    public Vampire(PlayerCharacter character, String playerName) {
        super(character, playerName);
        this.maxMana = 10;
        setMana(0);
    }

    @Override
    public void tick(){}
    @Override
    public void dealtDamage() {
        this.setMana(Math.min(this.getMana() + 1, maxMana));
    }
}
