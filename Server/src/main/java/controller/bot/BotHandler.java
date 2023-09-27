package controller.bot;

import database.Database;
import ir.sharif.ap.phase3.model.main.User;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BotHandler extends Thread {

    private final Map<User, String> botsRoute = new HashMap<>(); // maps the user related to bot with address
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        getBots();
    }

    private void getBots() {
        while (true) {
            String kooft = scanner.nextLine();
            if (kooft.startsWith("new bot ")) {
                String path = kooft.substring(8);
                File file;
                try {
                    file = new File(path);
                    Class<?> c = loadClassByFile(file);
                    checkValidity(c);
                    startBot(file);
                } catch (NoSuchMethodException e) {
                    System.out.println("bot class is invalid");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (kooft.equals("exit")) {
                System.exit(0);
            } else {
                System.out.println("wrong input");
            }
        }
    }

    private Class<?> loadClassByFile(File file) throws Exception {
        URL url = file.getParentFile().toURI().toURL();
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        String className = file.getName().substring(0, file.getName().lastIndexOf("."));
        return urlClassLoader.loadClass(className);
    }

    private void checkValidity(Class<?> potentialBotClass) throws NoSuchMethodException {
        potentialBotClass.getMethod("respond", String.class, int.class);
        potentialBotClass.getMethod("getFields");
        potentialBotClass.getMethod("setFields", String.class);
    }

    private void startBot(File file) {
        User botUser = new User("@" + file.getName().substring(0, file.getName().lastIndexOf(".")));
        Database.save(botUser);
        new BotManager(botUser, file.getAbsolutePath()).start();
    }
}