package org.openjfx.connector;

import ir.sharif.ap.phase3.event.EvenToken;
import ir.sharif.ap.phase3.event.Event;
import ir.sharif.ap.phase3.event.general.TokenRequest;
import ir.sharif.ap.phase3.response.Response;
import ir.sharif.ap.phase3.util.Loop;
import ir.sharif.ap.phase3.util.Status;
import javafx.stage.Stage;
import org.openjfx.SceneManager;
import org.openjfx.util.Updater;
import org.openjfx.view.GraphicalAgent;

import java.util.LinkedList;
import java.util.List;

public class MainController {

    private final EventSender sender;
    private final List<Event> events;
    private final Loop loop;
    private final GraphicalAgent graphicalAgent;
    private final ClientResponseVisitor responseVisitor;
    private Status status;
    private Integer token;

    public MainController(EventSender sender, Stage mainStage) {
        this.sender = sender;
        events = new LinkedList<>();
        loop = new Loop(2, this::sendEvents);
        Updater updater = new Updater(this::addEvent);
        SceneManager.getSceneManager().setUpdater(updater);
        graphicalAgent = new GraphicalAgent(this::addEvent ,mainStage);
        responseVisitor = new ClientResponseVisitor(graphicalAgent, updater, this);
    }

    public void start() {
        addEvent(new TokenRequest());
        loop.start();
        graphicalAgent.goToLogin();
    }

    public void addEvent(Event event) {
        synchronized (events) {
            events.add(event);
        }
    }

    public void sendEvents() {
        List<Event> tmp;
        synchronized (events) {
            tmp = new LinkedList<>(events);
            events.clear();
        }
        for (Event event : tmp) {
            try {
                Response response = sender.send(new EvenToken(event, token));
                response.visit(responseVisitor);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void setToken(Integer token) {
        this.token = token;
    }
}