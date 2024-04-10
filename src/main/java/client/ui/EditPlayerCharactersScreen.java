package client.ui;

import server.Resistance;
import server.Weakness;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.characters.CharacterType;
import server.items.Ability;
import server.items.Armor;
import server.items.Weapon;
import server.minions.Minion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditPlayerCharactersScreen extends EditCharacterScreen<PlayerCharacter> {
    private JPanel frame;
    private JPanel container;
    private JButton backButton;
    private JTextField nameField;
    private JTextField healthField;
    private JTextField goldField;
    private JComboBox<CharacterType> breedComboBox;
    private JPanel minionsPanel;
    private JButton minionAddButton;
    private JPanel armorsPanel;
    private JButton armorAddButton;
    private JPanel weaponsPanel;
    private JButton weaponsAddButton;
    private JPanel strengthsPanel;
    private JButton strengthsAddButton;
    private JPanel weaknessesPanel;
    private JButton weaknessesAddButton;
    private JComboBox<Ability> specialAbilityComboBox;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton createButton;
    private JPanel buttonsMinionsPanel;
    private JPanel buttonsArmorsPanel;
    private JPanel buttonsWeaponsPanel;
    private JPanel buttonsWeaknessesPanel;
    private JPanel buttonsStrengthsPanel;

    private PlayerCharacter current;
    private final List<Armor> armorList = new ArrayList<>();
    private final List<Weapon> weaponList = new ArrayList<>();
    private final List<Ability> specialAbilityList = new ArrayList<>();
    private final List<Weakness> weaknessesList = new ArrayList<>();
    private final List<Resistance> resistancesList = new ArrayList<>();
    private final List<Minion> minionList = new ArrayList<>();
    private final List<PlayerCharacter> characterList = new ArrayList<>();

    @Override
    public void start() {
        super.start(PlayerCharacter.class);
    }

    @Override
    protected JButton getCreateButton() {
        return this.createButton;
    }

    @Override
    protected void createButtonActionListener() {
        setPanelData(new PlayerCharacter());
    }

    @Override
    protected JTextField getNameField() {
        return this.nameField;
    }

    @Override
    protected JTextField getHealthField() {
        return this.healthField;
    }

    @Override
    protected JTextField getGoldField() {
        return this.goldField;
    }

    @Override
    protected JComboBox<CharacterType> getBreedComboBox() {
        return this.breedComboBox;
    }

    @Override
    protected JPanel getMinionsPanel() {
        return this.minionsPanel;
    }

    @Override
    protected JButton getMinionAddButton() {
        return this.minionAddButton;
    }

    @Override
    protected JPanel getArmorsPanel() {
        return this.armorsPanel;
    }

    @Override
    protected JButton getArmorAddButton() {
        return this.armorAddButton;
    }

    @Override
    protected JPanel getWeaponsPanel() {
        return this.weaponsPanel;
    }

    @Override
    protected JButton getWeaponsAddButton() {
        return this.weaponsAddButton;
    }

    @Override
    protected JPanel getStrengthsPanel() {
        return this.strengthsPanel;
    }

    @Override
    protected JButton getStrengthsAddButton() {
        return this.strengthsAddButton;
    }

    @Override
    protected JPanel getWeaknessesPanel() {
        return this.weaknessesPanel;
    }

    @Override
    protected JButton getWeaknessesAddButton() {
        return this.weaknessesAddButton;
    }

    @Override
    protected JComboBox<Ability> getSpecialAbilityField() {
        return this.specialAbilityComboBox;
    }

    @Override
    protected JButton getSaveButton() {
        return this.saveButton;
    }

    @Override
    protected JButton getDeleteButton() {
        return this.deleteButton;
    }

    @Override
    protected JButton getBackButton() {
        return this.backButton;
    }

    @Override
    protected Character getCurrent() {
        return this.current;
    }

    @Override
    protected void setCurrent(PlayerCharacter character) {
        this.current = character;
    }

    @Override
    protected List<Armor> getArmorList() {
        return this.armorList;
    }

    @Override
    protected List<Weapon> getWeaponList() {
        return this.weaponList;
    }

    @Override
    protected List<Ability> getSpecialAbilityList() {
        return this.specialAbilityList;
    }

    @Override
    protected List<Weakness> getWeaknessesList() {
        return this.weaknessesList;
    }
    @Override
    protected List<Resistance> getResistancesList() {
        return this.resistancesList;
    }

    @Override
    protected List<Minion> getMinionList() {
        return this.minionList;
    }

    @Override
    protected List<PlayerCharacter> getCharacterList() {
        return this.characterList;
    }

    @Override
    protected void fetchItems() {
        super.fetchItems();
        fetchItemsOfType(PlayerCharacter.class, this.getCharacterList());
    }

    @Override
    public Container getPanel() {
        return this.frame;
    }

    @Override
    protected JPanel getContainerPanel() {
        return this.container;
    }
}