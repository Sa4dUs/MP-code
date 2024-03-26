package server.characters;

import server.Characteristic;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Demon;
import server.minions.Minion;

import java.util.ArrayList;
import java.util.List;

public class Character {

    private String id;
    private String name;
    private int health;
    private int gold;
    private CharacterType breed;

    //Items
    private List<Weapon> weaponsList = new ArrayList<>();
    private List<Armor> armorList = new ArrayList<>();
    private List<Ability> abilityList = new ArrayList<>();
    private List<Ability> specialAbilityList = new ArrayList<>();

    //Minions
    private List<Minion> minionList = new ArrayList<>();

    //Characteristics
    private List<Characteristic> debilitiesList = new ArrayList<>();
    private List<Characteristic> resistancesList = new ArrayList<>();

    public int calculateMinionsKilledAfterDamage(int damage)
    {
        int damageLeft = damage;
        int minionsKilled = 0;

        for(Minion minion: minionList)
        {
            if(minion.getClass() == Demon.class)
                minionsKilled += ((Demon) minion).calculateMinionsKilledAfterDamage(damageLeft);
            else
                ++ minionsKilled;

            damageLeft -= minion.getHealth();

            if(damageLeft <= 0)
                return minionsKilled;
        }

        return minionsKilled;
    }

    public int getMinionCount()
    {
        int res = 0;

        if(minionList.isEmpty())
            return 0;

        for(Minion minion: minionList)
        {
            if(minion.getClass() == Demon.class)
                res += ((Demon) minion).getMinionCount();
            else
                ++ res;
        }

        return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void removeGold(int amount) { this.gold -= amount; }

    public CharacterType getBreed() {
        return breed;
    }

    public void setBreed(CharacterType breed) {
        this.breed = breed;
    }

    public List<Weapon> getWeaponsList() {
        return weaponsList;
    }

    public void addWeaponToList(Weapon newWeapon) {weaponsList.add(newWeapon);}

    public List<Armor> getArmorList() {
        return armorList;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }

    public List<Ability> getSpecialAbilityList() {
        return specialAbilityList;
    }

    public List<Minion> getMinionList() {
        return minionList;
    }

    public void addMinion(Minion minion){this.minionList.add(minion);}

    public List<Characteristic> getDebilitiesList() {
        return debilitiesList;
    }

    public List<Characteristic> getResistancesList() {
        return resistancesList;
    }
}
