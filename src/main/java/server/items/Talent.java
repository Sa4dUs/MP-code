package server.items;

public class Talent extends Ability{
    public Talent(){super();}

    public Talent(String name, int attack, int defense, int cost)
    {
        super(name, attack, defense, cost);
    }
    public AbilityType getType() {
        return AbilityType.Talent;
    }
}
