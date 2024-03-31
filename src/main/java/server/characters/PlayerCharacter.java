package server.characters;

import server.Player;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.nosql.Document;
import server.nosql.Schemas.PlayerCharacterSchema;

import javax.print.Doc;

public class PlayerCharacter extends Character{
    private Weapon activeWeaponL, activeWeaponR;
    private Armor activeArmor;
    private Ability activeNormalAbility, activeSpecialAbility;

    public PlayerCharacter(){}

    public PlayerCharacter(Character character) {
        super(character);
    }

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

    public Document getDocument()
    {
        Document document = new Document(new PlayerCharacterSchema());
        document.updateFromDocument(super.getDocument());
<<<<<<< Updated upstream
        document.setProperty("activeWeaponL", "");
        document.setProperty("activeWeaponR", "");
        document.setProperty("activeArmor", "");
        document.setProperty("activeNormalAbility", "");
        document.setProperty("activeSpecialAbility", "");
=======
        document.setProperty("activeWeaponL", this.activeWeaponL != null ? this.activeWeaponL.getDocument(): "");
        document.setProperty("activeWeaponR", this.activeWeaponR != null ? this.activeWeaponR.getDocument(): "");
        document.setProperty("activeArmor", this.activeArmor != null ? this.activeArmor.getDocument(): "");
        document.setProperty("activeNormalAbility", this.activeNormalAbility != null ? this.activeNormalAbility.getDocument(): "");
        document.setProperty("activeNormalAbility", this.activeSpecialAbility != null ? this.activeSpecialAbility.getDocument(): "");
>>>>>>> Stashed changes

        return document;
    }
}
