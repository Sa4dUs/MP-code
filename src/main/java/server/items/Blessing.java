package server.items;

public class Blessing extends Ability{
    public Blessing(){super();}

    public Blessing(String name, int attack, int defense, int cost)
    {
        super(name, attack, defense, cost);
    }
    public AbilityType getType() {
        return AbilityType.Blessing;
    }
}
