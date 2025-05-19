package Services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import utils.SceneLocator;

public class SceneManager {
    private static SceneManager instance;

    private Scene scene;
    private String currentPath;
    private LanguageManager languageManager;

    private static final String DEFAULT_START_PAGE = SceneLocator.ADMIN_HOME_PAGE;

    private SceneManager(){
        this.languageManager = LanguageManager.getInstance();
        scene = initializeScene();
    }

    public static SceneManager getInstance(){
        if (instance == null){
            instance = new SceneManager();
        }
        return instance;
    }

    private Scene initializeScene(){
        try{
            Parent root = loadFXML(getCurrentPath());
            return new Scene(root);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static void load(String path) throws Exception{
        getInstance().setSceneRoot(path);
    }

    private static void load(String path, Pane pane) throws Exception{
        getInstance().setPaneContent(path,pane);
    }


    private void setPaneContent(String path, Pane pane) throws Exception{
        pane.getChildren().setAll(loadFXML(path));
    }

    private void setSceneRoot(String path) throws Exception{
        Parent root = loadFXML(path);
        this.currentPath = path;
        scene.setRoot(root);
    }

    private Parent loadFXML(String path) throws Exception{
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource(path)
        );
        loader.setResources(this.languageManager.getResourceBundle());
        return loader.load();
    }

    private FXMLLoader loadFXMLLoader(String path) throws Exception { //used to pass selected data to another controller (edit scholarships)
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
        loader.setResources(this.languageManager.getResourceBundle());
        loader.load();
        return loader;
    }

    public static void reload() throws Exception{
        load(instance.getCurrentPath());
    }

    public String getCurrentPath(){
        return currentPath == null ? DEFAULT_START_PAGE : currentPath;
    }

    public Scene getScene(){
        return scene;
    }

    public void loadScene(String path){
        try{
            setSceneRoot(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
