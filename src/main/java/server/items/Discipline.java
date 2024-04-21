package server.items;

public class Discipline extends Ability{
    public Discipline(){super();}

    public Discipline(String name, int attack, int defense, int cost)
    {
        super(name, attack, defense, cost);
    }
    public AbilityType getType() {
        return AbilityType.Discipline;
    }
}
