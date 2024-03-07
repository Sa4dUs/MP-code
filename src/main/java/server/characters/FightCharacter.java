package server.characters;

import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;

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

        maxHealth = character.getHealth();
        health = maxHealth;

        activeWeaponL = character.getActiveWeaponL();
        activeWeaponR = character.getActiveWeaponR();

        activeArmor = character.getActiveArmor();

        activeNormalAbility = character.getActiveNormalAbility();
        activeSpecialAbility = character.getActiveSpecialAbility();
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

    public int CalculateDamage()
    {
        int totalDamage = 0;

        totalDamage += activeArmor.getAttack();

        if(activeSpecialAbility.getCost() <= mana)
        {
            totalDamage += activeSpecialAbility.getAttack();
            UseSpecialAbility();
        }
        else if (activeNormalAbility.getCost() <= power)
        {
            totalDamage += activeNormalAbility.getAttack();
            UseNormalAbility();
        }
        else
        {
            totalDamage += activeWeaponL != null ? activeWeaponL.getAttack() : 0;
            totalDamage += activeWeaponR != null ? activeWeaponR.getAttack() : 0;
        }

        return Roll(totalDamage, 1, 6, 2);
    }

    public int CalculateDefense()
    {
        int totalDefense = 0;

        totalDefense += activeArmor.getDefense();

        if(activeSpecialAbility.getCost() <= mana)
        {
            totalDefense += activeSpecialAbility.getDefense();
            UseSpecialAbility();
        }
        else if (activeNormalAbility.getCost() <= power)
        {
            totalDefense += activeNormalAbility.getDefense();
            UseNormalAbility();
        }
        else
        {
            totalDefense += activeWeaponL != null ? activeWeaponL.getDefense() : 0;
            totalDefense += activeWeaponR != null ? activeWeaponR.getDefense() : 0;
        }

        return Roll(totalDefense, 1, 6, 2);
    }

    private  void UseNormalAbility()
    {
        power -= activeNormalAbility.getCost();
    }

    private  void UseSpecialAbility()
    {
        mana -= activeSpecialAbility.getCost();
    }

    private  int Roll(int amount, int min, int max, int required)
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

    private int CalculateHealth()
    {
        return 0;
    }
}
