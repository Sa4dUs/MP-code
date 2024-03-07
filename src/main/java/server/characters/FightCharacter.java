package server.characters;

import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;

public class FightCharacter {
    private String name;
    private int maxHealth, health, minionHealth, power, mana;
    private Weapon activeWeaponL, activeWeaponR;
    private Armor activeArmor;
    private Ability activeNormalAbility, activeSpecialAbility;
}
