package server.characters;

import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;

public class PlayerCharacter extends Character{
    private Weapon activeWeaponL, activeWeaponR;
    private Armor activeArmor;
    private Ability activeNormalAbility, activeSpecialAbility;

    public Weapon getActiveWeaponL() {
        return activeWeaponL;
    }

    public void setActiveWeaponL(Weapon activeWeaponL) {
        this.activeWeaponL = activeWeaponL;
    }

    public Weapon getActiveWeaponR() {
        return activeWeaponR;
    }

    public void setActiveWeaponR(Weapon activeWeaponR) {
        this.activeWeaponR = activeWeaponR;
    }

    public Armor getActiveArmor() {
        return activeArmor;
    }

    public void setActiveArmor(Armor activeArmor) {
        this.activeArmor = activeArmor;
    }

    public Ability getActiveNormalAbility() {
        return activeNormalAbility;
    }

    public void setActiveNormalAbility(Ability activeNormalAbility) {
        this.activeNormalAbility = activeNormalAbility;
    }

    public Ability getActiveSpecialAbility() {
        return activeSpecialAbility;
    }

    public void setActiveSpecialAbility(Ability activeSpecialAbility) {
        this.activeSpecialAbility = activeSpecialAbility;
    }
}
