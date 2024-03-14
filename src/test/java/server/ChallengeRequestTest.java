package server;

import org.junit.Test;
import server.characters.*;
import server.items.Weapon;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeRequestTest {

    @Test
    public void testDuelRequestAccept(){
        Player attacked = new Player();

        Weapon weaponAttacked = new Weapon();
        weaponAttacked.setAttack(3);
        weaponAttacked.setDefense(3);

        PlayerCharacter characterAttacked = new PlayerCharacter();
        characterAttacked.setHealth(20);
        characterAttacked.setActiveWeaponL(weaponAttacked);
        characterAttacked.setBreed(CharacterType.Vampire);
        attacked.setCharacter(characterAttacked);


        Player attacker = new Player();

        Weapon weaponAttacker = new Weapon();
        weaponAttacker.setAttack(3);
        weaponAttacker.setDefense(3);

        PlayerCharacter characterAttacker = new PlayerCharacter();
        characterAttacker.setHealth(20);
        characterAttacker.setActiveWeaponL(weaponAttacker);
        characterAttacker.setBreed(CharacterType.Hunter);
        attacker.setCharacter(characterAttacker);

        ChallengeRequest request = new ChallengeRequest(attacker, attacked, characterAttacker);


        //Act
        request.accept();

        //Asserts
        assertFalse(attacked.getPendingDuels().contains(request));
    }

    @Test
    public void testDuelRequestDenyFromPlayer(){
        Player attacked = new Player();

        Weapon weaponAttacked = new Weapon();
        weaponAttacked.setAttack(3);
        weaponAttacked.setDefense(3);

        PlayerCharacter characterAttacked = new PlayerCharacter();
        characterAttacked.setHealth(20);
        characterAttacked.setActiveWeaponL(weaponAttacked);
        characterAttacked.setBreed(CharacterType.Vampire);
        attacked.setCharacter(characterAttacked);


        Player attacker = new Player();

        Weapon weaponAttacker = new Weapon();
        weaponAttacker.setAttack(3);
        weaponAttacker.setDefense(3);

        PlayerCharacter characterAttacker = new PlayerCharacter();
        characterAttacker.setHealth(20);
        characterAttacker.setActiveWeaponL(weaponAttacker);
        characterAttacker.setBreed(CharacterType.Hunter);
        attacker.setCharacter(characterAttacker);

        ChallengeRequest request = new ChallengeRequest(attacker, attacked, characterAttacker);

        //Act
        request.denyFromPlayer();

        //Asserts
        assertFalse(attacked.getPendingDuels().contains(request));
    }

    @Test
    public void testDuelRequestSendToTarget(){
        Player attacked = new Player();

        Weapon weaponAttacked = new Weapon();
        weaponAttacked.setAttack(3);
        weaponAttacked.setDefense(3);

        PlayerCharacter characterAttacked = new PlayerCharacter();
        characterAttacked.setHealth(20);
        characterAttacked.setActiveWeaponL(weaponAttacked);
        characterAttacked.setBreed(CharacterType.Vampire);
        attacked.setCharacter(characterAttacked);


        Player attacker = new Player();

        Weapon weaponAttacker = new Weapon();
        weaponAttacker.setAttack(3);
        weaponAttacker.setDefense(3);

        PlayerCharacter characterAttacker = new PlayerCharacter();
        characterAttacker.setHealth(20);
        characterAttacker.setActiveWeaponL(weaponAttacker);
        characterAttacker.setBreed(CharacterType.Hunter);
        attacker.setCharacter(characterAttacker);

        ChallengeRequest request = new ChallengeRequest(attacker, attacked, characterAttacker);

        //Act
        request.sendToTarget();

        //Asserts
        assertTrue(attacked.getPendingDuels().contains(request));
    }

    @Test
    public void testDuelRequestDenyFromOperator(){
        Player attacked = new Player();

        Player attacker = new Player();

        Weapon weaponAttacker = new Weapon();
        weaponAttacker.setAttack(3);
        weaponAttacker.setDefense(3);

        PlayerCharacter characterAttacker = new PlayerCharacter();
        characterAttacker.setHealth(20);
        characterAttacker.setActiveWeaponL(weaponAttacker);
        characterAttacker.setBreed(CharacterType.Hunter);
        attacker.setCharacter(characterAttacker);

        ChallengeRequest request = new ChallengeRequest(attacker, attacked, characterAttacker);

        //Act
        request.denyFromOperator();

        //Asserts
        assertFalse(attacked.getPendingDuels().contains(request));
    }
}
