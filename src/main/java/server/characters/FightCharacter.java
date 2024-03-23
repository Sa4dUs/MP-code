package server.characters;

import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class FightCharacter {
    private String name;
    private int maxHealth, health, minionHealth, power, mana;
    private Weapon activeWeaponL, activeWeaponR;
    private Armor activeArmor;
    private Ability activeNormalAbility, activeSpecialAbility;

    public FightCharacter(PlayerCharacter character)
    {
        this.name = character.getName();

        this.minionHealth = getMinionHealth(character);
        this.health = character.getHealth() + this.minionHealth;
        this.maxHealth = this.health;


        this.activeWeaponL = character.getActiveWeaponL();
        this.activeWeaponR = character.getActiveWeaponR();

        this.activeArmor = character.getActiveArmor();

        this.activeNormalAbility = character.getActiveNormalAbility();
        this.activeSpecialAbility = character.getActiveSpecialAbility();
    }

    private int getMinionHealth(PlayerCharacter character){
        List<Minion> minionList = character.getMinionList();

        if(minionList == null) {
            return 0;
        }

        int mHealth = 0;

        for (Minion m: minionList) {
            mHealth += m != null ? m.getHealth() : 0;
        }

        return mHealth;
    }

    public int getReceivedDamage(){return maxHealth - health;}

    public void receiveDamage()
    {
        this.health--;
    }

    public boolean isDead()
    {
        return this.health <= 0;
    }

    public abstract void tick();

    public int calculateDamage()
    {
        int totalDamage = 0;

        totalDamage += this.activeArmor != null ? this.activeArmor.getAttack() : 0;

        if(canUseAbility(this.activeSpecialAbility, this.mana))
        {
            totalDamage += this.activeSpecialAbility.getAttack();
            useSpecialAbility();
        }
        else if (canUseAbility(this.activeNormalAbility, this.power))
        {
            totalDamage += this.activeNormalAbility.getAttack();
            useNormalAbility();
        }
        else
        {
            totalDamage += this.activeWeaponL != null ? this.activeWeaponL.getAttack() : 0;
            totalDamage += this.activeWeaponR != null ? this.activeWeaponR.getAttack() : 0;
        }

        return roll(totalDamage, 1, 6, 2);
    }

    public int calculateDefense()
    {
        int totalDefense = 0;

        totalDefense += this.activeArmor != null ? this.activeArmor.getDefense() : 0;

        if(canUseAbility(this.activeSpecialAbility, this.mana))
        {
            totalDefense += this.activeSpecialAbility.getDefense();
            useSpecialAbility();
        }
        else if (canUseAbility(this.activeNormalAbility, this.power))
        {
            totalDefense += this.activeNormalAbility.getDefense();
            useNormalAbility();
        }
        else
        {
            totalDefense += this.activeWeaponL != null ? this.activeWeaponL.getDefense() : 0;
            totalDefense += this.activeWeaponR != null ? this.activeWeaponR.getDefense() : 0;
        }

        return roll(totalDefense, 1, 6, 2);
    }

    private void useNormalAbility()
    {
        this.power -= this.activeNormalAbility.getCost();
    }

    private void useSpecialAbility()
    {
        this.mana -= this.activeSpecialAbility.getCost();
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
    { return this.health + this.minionHealth; }
}
