package server.characters;

import server.Characteristic;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

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


}
