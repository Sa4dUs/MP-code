package server.characters;

import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;

public class PlayerCharacter extends Character{
    private Weapon activeWeaponL, activeWeaponR;
    private Armor activeArmor;
    private Ability activeNormalAbility, activeSpecialAbility;
}
