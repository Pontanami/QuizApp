package com.example.quizapp;

import com.example.quizapp.firebase.FirebaseConnection;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.mainview.MenuController;
import javafx.application.Application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is responsible for managing the navigation stack
 * @author Pontus
 */
public class NavigationStack extends AnchorPane {

    private final StackPane stackPane;

    private static NavigationStack instance;

    @FXML
    BorderPane menuPane;

    public void start(Stage primaryStage){
        /*


        BorderPane root = new BorderPane();
        HeaderController headerController = new HeaderController();
        root.setTop(headerController);
        stackPane = new StackPane();
        root.setCenter(stackPane);
        primaryStage.setScene(new Scene(root, 1920, 1080));

        loadView(new HelloController());

        primaryStage.show();
        */

    }

    public static NavigationStack getInstance(){
        if (instance == null)
            instance = new NavigationStack();

        return instance;
    }

    private NavigationStack(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        stackPane = new StackPane();
        menuPane.setCenter(stackPane);
    }
    /**
     * Loads a view into the stackpane
     * @param controller the controller of the view to be loaded
     */
    public void loadView(AnchorPane controller){
        stackPane.getChildren().add(controller);
    }
    /**
     * Pushes a view onto the stackpane
     * @param controller the controller of the view to be pushed
     */
    public void pushView(AnchorPane controller) {
        if (stackPane.getChildren().contains(controller)) {
            return;
        }
        try {
            loadView(controller);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int lastIndex = stackPane.getChildren().size() - 1;
        Node pushedNode = stackPane.getChildren().get(lastIndex);
        pushedNode.managedProperty().bind(pushedNode.visibleProperty());
        pushedNode.toFront();

        for (int i = 0; i < stackPane.getChildren().size() - 1; i++) {
            stackPane.getChildren().get(i).setVisible(false);
        }
        System.out.println(stackPane.getChildren());
    }
    /**
     * Pops a view from the stackpane
     */
    public void popView() {
        if (stackPane.getChildren().size() <= 1) {
            return;
        }
        int lastIndex = stackPane.getChildren().size() - 1;
        stackPane.getChildren().remove(lastIndex);
        stackPane.getChildren().get(lastIndex - 1).setVisible(true);
        System.out.println(stackPane.getChildren());
    }
    /**
     * Pops to the root view
     */
    public void popToRoot(){
        if (stackPane.getChildren().size() <= 1) {
            return;
        }
        int lastIndex = stackPane.getChildren().size() - 1;
        stackPane.getChildren().remove(1, lastIndex);
        stackPane.getChildren().get(0).setVisible(true);
    }
    /**
     * Pops to a specified view
     * @param controller the controller of the view to be popped to
     */
    public void goBackToSpecifiedView(AnchorPane controller){
        int index = stackPane.getChildren().indexOf(controller);
        if (index == -1) {
            return;
        }
        int lastIndex = stackPane.getChildren().size() - 1;
        stackPane.getChildren().remove(index + 1, lastIndex);
        stackPane.getChildren().get(index).setVisible(true);
    }
    /**
     * Removes a view from the stackpane
     * @param controller the controller of the view to be removed
     */
    public void removeView(AnchorPane controller){
        stackPane.getChildren().remove(controller);
    }

    /**
     * Gets the ViewStack
     * @return A {@link ObservableList} of {@link Node} representing the viewstack
     */
    public ObservableList<Node> getViewStack(){
        return stackPane.getChildren();
    }

    public void setHeader(AnchorPane pane){
        menuPane.setTop(pane);
    }

    public void clearAll(){
        menuPane.getChildren().clear();
        menuPane.setCenter(stackPane);
    }
}
