package server.items;

public abstract class Stats {

    private String id;
    private String name;
    private int attack;
    private final int maxAttack = 3;
    private int defense;
    private final int maxDefense = 3;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = (attack > maxAttack) ? maxAttack : Math.max(attack, 1);;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int defense) {
        this.defense = (defense > maxDefense) ? maxDefense : Math.max(defense, 1);;
    }

}
