package server.characters;

import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class FightCharacter {

    private String id;

    private final String name, playerName;
    private final int maxHealth;
    private int health;
    private final int minionHealth;
    private int power;
    private int mana;
    private final Weapon activeWeaponL, activeWeaponR;
    private final Armor activeArmor;
    private final Ability activeSpecialAbility;

    private StringBuilder turnResume;

    public FightCharacter(PlayerCharacter character, String playerName)
    {
        this.name = character.getName();
        this.playerName = playerName;

        this.minionHealth = getMinionHealth(character);
        this.health = character.getHealth() + this.minionHealth;
        this.maxHealth = this.health;


        this.activeWeaponL = character.getActiveWeaponL();
        this.activeWeaponR = character.getActiveWeaponR();

        this.activeArmor = character.getActiveArmor();

        this.activeSpecialAbility = character.getActiveSpecialAbility();

        this.turnResume = new StringBuilder("(").append(this.playerName).append(") ").append(this.name);
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

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana(){
        return mana;
    }
    public int getReceivedDamage(){return maxHealth - health;}

    public void receiveDamage()
    {
        this.turnResume.append(" Received damage");
        this.health--;
    }

    public boolean isDead()
    {
        return this.health <= 0;
    }

    public void tick()
    {
        ++this.power;
    }

    public int calculateDamage()
    {
        this.turnResume.append(" used ");
        int totalDamage = 0;

        if(canUseAbility(this.activeSpecialAbility, this.mana))
        {
            totalDamage += this.activeSpecialAbility.getAttack();
            useSpecialAbility();
        }
        else
        {
            if(this.activeWeaponL != null)
            {
                this.turnResume.append(activeWeaponL.toString());
                totalDamage += this.activeWeaponL.getAttack();
            }
            if(this.activeWeaponR != null)
            {
                this.turnResume.append(totalDamage == 0 ? this.activeWeaponR.toString(): " and " + this.activeWeaponR.toString());
                totalDamage += this.activeWeaponR.getAttack();
            }
        }

        totalDamage += this.activeArmor != null ? this.activeArmor.getAttack() : 0;

        this.turnResume.append(" to attack.");

        return roll(totalDamage, 1, 6, 2);
    }

    public int calculateDefense()
    {
        this.turnResume.append(" used ");
        int totalDefense = 0;

        if(canUseAbility(this.activeSpecialAbility, this.mana))
        {
            totalDefense += this.activeSpecialAbility.getDefense();
            useSpecialAbility();
        }
        else
        {
            if(this.activeWeaponL != null)
            {
                this.turnResume.append(activeWeaponL.toString());
                totalDefense += this.activeWeaponL.getAttack();
            }
            if(this.activeWeaponR != null)
            {
                this.turnResume.append(totalDefense == 0 ? this.activeWeaponR.toString(): " and " + (this.activeWeaponR.toString()));
                totalDefense += this.activeWeaponR.getAttack();
            }
        }

        totalDefense += this.activeArmor != null ? this.activeArmor.getDefense() : 0;

        this.turnResume.append(" to defend.");

        return roll(totalDefense, 1, 6, 2);
    }
    private void useSpecialAbility()
    {
        this.mana -= this.activeSpecialAbility.getCost();
        this.turnResume.append(activeSpecialAbility.toString());
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

    public String getName() {
        return this.name;
    }

    public int getHealth(){return this.health;}

    public String getLastTurn(){
        String res = this.turnResume.toString();
        this.turnResume = new StringBuilder("(").append(this.playerName).append(") ").append(this.name);
        return res;
    }

    public abstract void dealtDamage();
}
