package server.characters;

public class Hunter extends FightCharacter{
    private final int minMana;

    public Hunter(PlayerCharacter character) {
        super(character);
        this.minMana = 0;
        setMana(3);
    }

    @Override
    public void tick() {}

    @Override
    public void receiveDamage(){
        super.receiveDamage();
        this.setMana(Math.max(this.getMana() - 1, minMana));
    }

}
