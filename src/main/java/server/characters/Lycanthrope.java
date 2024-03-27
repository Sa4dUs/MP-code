package server.characters;

public class Lycanthrope extends FightCharacter{
    private int maxMana;

    public Lycanthrope(PlayerCharacter character) {
        super(character);
        this.maxMana = 3;
        setMana(0);
    }

    @Override
    public void tick() {}

    @Override
    public void receiveDamage(){
        super.receiveDamage();
        this.setMana(Math.min(this.getMana() + 1, maxMana));
    }
}
