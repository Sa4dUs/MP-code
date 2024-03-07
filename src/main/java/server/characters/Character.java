package server.characters;

import server.Characteristic;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import java.util.List;

public class Character {
    private String name;
    private int health;
    private int gold;
    private CharacterType breed;

    //Items
    private List<Weapon> weaponsList;
    private List<Armor> armorList;
    private List<Ability> abilityList;
    private List<Ability> specialAbilityList;

    //Minions
    private List<Minion> minionList;

    //Characteristics
    private List<Characteristic> debilitiesList;
    private List<Characteristic> resistancesList;

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

    public CharacterType getBreed() {
        return breed;
    }

    public void setBreed(CharacterType breed) {
        this.breed = breed;
    }

    public List<Weapon> getWeaponsList() {
        return weaponsList;
    }

    public void setWeaponsList(List<Weapon> weaponsList) {
        this.weaponsList = weaponsList;
    }

    public List<Armor> getArmorList() {
        return armorList;
    }

    public void setArmorList(List<Armor> armorList) {
        this.armorList = armorList;
    }

    public List<Ability> getAbilityList() {
        return abilityList;
    }

    public void setAbilityList(List<Ability> abilityList) {
        this.abilityList = abilityList;
    }

    public List<Ability> getSpecialAbilityList() {
        return specialAbilityList;
    }

    public void setSpecialAbilityList(List<Ability> specialAbilityList) {
        this.specialAbilityList = specialAbilityList;
    }

    public List<Minion> getMinionList() {
        return minionList;
    }

    public void setMinionList(List<Minion> minionList) {
        this.minionList = minionList;
    }

    public List<Characteristic> getDebilitiesList() {
        return debilitiesList;
    }

    public void setDebilitiesList(List<Characteristic> debilitiesList) {
        this.debilitiesList = debilitiesList;
    }

    public List<Characteristic> getResistancesList() {
        return resistancesList;
    }

    public void setResistancesList(List<Characteristic> resistancesList) {
        this.resistancesList = resistancesList;
    }
}
