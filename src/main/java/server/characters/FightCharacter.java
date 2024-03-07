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
        return 0;
    }

    public int CalculateDefense()
    {
        return 0;
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
