package server.characters;

import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class FightCharacter {
    private String name;
    private int maxHealth, health, minionHealth, power, mana;
    private Weapon activeWeaponL, activeWeaponR;
    private Armor activeArmor;
    private Ability activeNormalAbility, activeSpecialAbility;

    public FightCharacter(PlayerCharacter character)
    {
        name = character.getName();

        health = character.getHealth();
        minionHealth = getMinionHealth(character);
        maxHealth = health + minionHealth;


        activeWeaponL = character.getActiveWeaponL();
        activeWeaponR = character.getActiveWeaponR();



        activeArmor = character.getActiveArmor();

        activeNormalAbility = character.getActiveNormalAbility();
        activeSpecialAbility = character.getActiveSpecialAbility();
    }

    private int getMinionHealth(PlayerCharacter character){
        List<Minion> minionList = character.getMinionList();

        if(minionList == null)
            return  0;

        int mHealth = 0;

        for (Minion m: minionList) {
            mHealth += m != null ? m.getHealth() : 0;
        }

        return mHealth;
    }

    public void recieveDamage()
    {
        health --;
    }

    public boolean isDead()
    {
        return  health <= 0;
    }

    public abstract void tick();

    public int calculateDamage()
    {
        int totalDamage = 0;

        totalDamage += activeArmor != null ? activeArmor.getAttack() : 0;

        if(canUseAbility(activeSpecialAbility, mana))
        {
            totalDamage += activeSpecialAbility.getAttack();
            useSpecialAbility();
        }
        else if (canUseAbility(activeNormalAbility, power))
        {
            totalDamage += activeNormalAbility.getAttack();
            useNormalAbility();
        }
        else
        {
            totalDamage += activeWeaponL != null ? activeWeaponL.getAttack() : 0;
            totalDamage += activeWeaponR != null ? activeWeaponR.getAttack() : 0;
        }

        return roll(totalDamage, 1, 6, 2);
    }

    public int calculateDefense()
    {
        int totalDefense = 0;

        totalDefense += activeArmor != null ? activeArmor.getDefense() : 0;

        if(canUseAbility(activeSpecialAbility, mana))
        {
            totalDefense += activeSpecialAbility.getDefense();
            useSpecialAbility();
        }
        else if (canUseAbility(activeNormalAbility, power))
        {
            totalDefense += activeNormalAbility.getDefense();
            useNormalAbility();
        }
        else
        {
            totalDefense += activeWeaponL != null ? activeWeaponL.getDefense() : 0;
            totalDefense += activeWeaponR != null ? activeWeaponR.getDefense() : 0;
        }

        return roll(totalDefense, 1, 6, 2);
    }

    private void useNormalAbility()
    {
        power -= activeNormalAbility.getCost();
    }

    private void useSpecialAbility()
    {
        mana -= activeSpecialAbility.getCost();
    }

    private boolean canUseAbility(Ability ability, int resource) {
        return (ability != null && ability.getCost() <= resource);
    }

    private int roll(int amount, int min, int max, int required)
    {
        int res = 0;
        for(int i = 0; i < amount; i++)
        {
            int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);

            if(randomNum <= required)
                res ++;
        }

        return res;
    }

    private int calculateHealth()
    { return health + minionHealth; }
}
