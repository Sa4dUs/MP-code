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
    private Ability activeSpecialAbility;

    public PlayerCharacter(){super();}

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
        document.setProperty("activeWeaponL", this.activeWeaponL != null ? this.activeWeaponL.getDocument().getId(): "");
        document.setProperty("activeWeaponR", this.activeWeaponR != null ? this.activeWeaponR.getDocument().getId(): "");
        document.setProperty("activeArmor", this.activeArmor != null ? this.activeArmor.getDocument().getId(): "");
        document.setProperty("activeSpecialAbility", this.activeSpecialAbility != null ? this.activeSpecialAbility.getDocument().getId(): "");

        return document;
    }
}
