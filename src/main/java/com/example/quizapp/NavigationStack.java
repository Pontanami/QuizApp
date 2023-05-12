package com.example.quizapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing the navigation stack
 * @author Pontus
 */
public class NavigationStack extends AnchorPane {

    private final StackPane stackPane;

    private static NavigationStack instance;

    private List<String> stackClasses = new ArrayList<>();

    @FXML
    BorderPane menuPane;

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
    private void loadView(AnchorPane controller, String className){
        stackPane.getChildren().add(controller);
        stackClasses.add(className);
    }
    /**
     * Pushes a view onto the stackpane. If it already exists we change to that view instead, otherwise we add it
     * @param controller the controller of the view to be pushed
     */
    public void pushView(AnchorPane controller) {
        String className = controller.getClass().getSimpleName();
        if(stackClasses.contains(className))
            goToSpecifiedView(stackClasses.indexOf(className));
        else {
            try {
                loadView(controller, className);
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
        }
    }
    /**
     * Pops a view from the stackpane, goes back to the previous one
     */
    public void popView() {
        if (stackPane.getChildren().size() <= 1) {
            return;
        }
        int lastIndex = stackPane.getChildren().size() - 1;
        stackPane.getChildren().remove(lastIndex);
        stackClasses.remove(lastIndex);
        stackPane.getChildren().get(lastIndex - 1).setVisible(true);
    }
    /**
     * Pops to the root of the application, in other words out homeView
     */
    public void popToRoot(){
        if (stackPane.getChildren().size() <= 1) {
            return;
        }
        int homeIndex = stackClasses.indexOf("HomeController");
        removeFromStack(homeIndex);
        stackPane.getChildren().get(homeIndex).setVisible(true);
    }

    /**
     * Changes the view to a specific one depending on the index
     * @param index the index of the specific view we want to go to
     */
    private void goToSpecifiedView(int index){
        removeFromStack(index);
        stackPane.getChildren().get(index).setVisible(true);
    }

    /**
     * Removes part of the stackpane, used when we want to pop to a specific view
     * @param index the index we want to start the removal from
     */
    private void removeFromStack(int index) {
        int removeToIndex = stackPane.getChildren().size();
        stackPane.getChildren().remove(index +1, removeToIndex);
        stackClasses.subList(index +1, removeToIndex).clear();
    }


    /**
     * Removes a view from the stackpane
     * @param controller the controller of the view to be removed
     */
    public void removeView(AnchorPane controller){
        String classname = controller.getClass().getSimpleName();
        stackClasses.remove(classname);
        stackPane.getChildren().remove(controller);
    }

    /**
     * Gets the ViewStack
     * @return A {@link ObservableList} of {@link Node} representing the viewstack
     */
    public ObservableList<Node> getViewStack(){
        return stackPane.getChildren();
    }

    /**
     * returns a specific view from the stack
     * @param controller thecontroller we want to return
     * @returnThe view we wanted to get from the stack
     */
    public Node getSpecificView(Class controller){
        String classname = controller.getSimpleName();
        int index = stackClasses.indexOf(classname);
        return stackPane.getChildren().get(index);
    }

    /**
     * Updates the header menu
     * @param pane the menu we want to update to
     */
    public void setHeader(AnchorPane pane){
        menuPane.setTop(pane);
    }

    /**
     * Clears all controll classes from the stackpane
     */
    public void clearAll(){
        menuPane.getChildren().clear();
        menuPane.setCenter(stackPane);
        stackPane.getChildren().clear();
        stackClasses.clear();
    }
}
