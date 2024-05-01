package server.nosql;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.*;
import server.characters.Character;
import server.characters.PlayerCharacter;
import server.items.*;
import server.minions.Demon;
import server.minions.Ghoul;
import server.minions.Human;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @Test
    public void cleanUp()
    {
        Database.deleteMany(Weapon.class.getName(), new Query());
        Database.deleteMany(Armor.class.getName(), new Query());
        Database.deleteMany(Ability.class.getName(), new Query());
        Database.deleteMany(Talent.class.getName(), new Query());
        Database.deleteMany(Blessing.class.getName(), new Query());
        Database.deleteMany(Discipline.class.getName(), new Query());
        Database.deleteMany(Resistance.class.getName(), new Query());
        Database.deleteMany(Weakness.class.getName(), new Query());
        Database.deleteMany(Human.class.getName(), new Query());
        Database.deleteMany(Demon.class.getName(), new Query());
        Database.deleteMany(Ghoul.class.getName(), new Query());
        Database.deleteMany(ChallengeResult.class.getName(), new Query());
        Database.deleteMany(ChallengeRequest.class.getName(), new Query());
        Database.deleteMany(Collection.CHALLENGE_OPERATORS, new Query());
        Database.deleteMany(Player.class.getName(), new Query());
        Database.deleteMany(Operator.class.getName(), new Query());
        Database.deleteMany(Character.class.getName(), new Query());
        Database.deleteMany(PlayerCharacter.class.getName(), new Query());
    }

    @Test
    public void saveAndLoadEmptyObjects() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<?>> classes = getClassesInPackage("server");

        for (Class<?> clazz: classes)
        {
            JSONable obj = (JSONable) clazz.getDeclaredConstructor().newInstance();
            Document doc = obj.getDocument();
            doc.saveToDatabase(clazz);
            Query query = new Query();
            query.addFilter("id", doc.getId());

            assertEquals(obj.getClass(), Document.getDocument(doc.getId(), clazz).deJSONDocument(clazz).getClass());
            Database.deleteOne(clazz.getName(), query);
        }

    }

    public List<Class<?>> getClassesInPackage(String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements())
        {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            if (directory.exists() && directory.isDirectory())
            {
                File[] files = directory.listFiles();
                if (files != null)
                {
                    for (File file : files)
                    {
                        String fileName = file.getName();
                        if (fileName.endsWith(".class"))
                        {
                            String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
                            Class<?> clazz = Class.forName(className);

                            if(JSONable.class.isAssignableFrom(clazz) && clazz != JSONable.class && !Modifier.isAbstract(clazz.getModifiers()))
                                classes.add(clazz);
                        } else
                        {
                            classes.addAll(getClassesInPackage(packageName + "." + fileName));
                        }
                    }
                }
            }
        }
        return classes;
    }
}