package org.openjfx;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.connector.EventListener;
import org.openjfx.util.Updater;
import org.openjfx.view.entrance.login.Login;
import org.openjfx.view.entrance.register.Register;

import java.util.Stack;

public class SceneManager {

    private static Stage mainStage;
    private static SceneManager mySceneManager;
    private static Scene loginScene, registerScene;
    private final Stack<Scene> scenes;
    private Updater updater;
    private EventListener listener;

    private SceneManager() {
        this.scenes = new Stack<>();
    }

    public static SceneManager getSceneManager() {
        if (mySceneManager == null) {
            mySceneManager = new SceneManager();
        }
        return mySceneManager;
    }

    public void push(Scene scene){
        scenes.push(scene);
        Platform.runLater(() -> {
            mainStage.setScene(scenes.peek());
            mainStage.show();
        });
    }

    public void pop(){
        scenes.pop();
    }

    public Scene peek(){
        return scenes.peek();
    }

    public void getBack(){
        if (updater != null)
            updater.stopLoop();
        scenes.pop();
        mainStage.setScene(scenes.peek());
    }

    public void clear(){
        scenes.clear();
    }

    public void backToMain(){
        while (scenes.size() > 1) {
            scenes.pop();
        }
        updater.stopLoop();
        mainStage.setScene(scenes.peek());
    }

    public static void setMainStage(Stage mainStage) {
        SceneManager.mainStage = mainStage;
        mainStage.setResizable(false);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Scene getLoginScene() {
        return loginScene;
    }

    public static void switchToRegister() {
        if (registerScene == null) {
            Register register = new Register();
            register.generate(mySceneManager.listener);
        }
        mainStage.setScene(registerScene);
        mainStage.show();
    }

    public static void switchToLogin() {
        if (loginScene == null) {
            Login login = new Login();
            login.generate(mySceneManager.listener);
        }
        mainStage.setScene(loginScene);
        mainStage.show();
    }

    public static Scene getRegisterScene() {
        return registerScene;
    }

    public static void setLoginScene(Scene loginScene) {
        SceneManager.loginScene = loginScene;
    }

    public static void setRegisterScene(Scene registerScene) {
        SceneManager.registerScene = registerScene;
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
    }

    public Stack<Scene> getScenes() {
        return scenes;
    }
}