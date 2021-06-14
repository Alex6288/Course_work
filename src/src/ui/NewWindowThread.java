package ui;

import javafx.scene.control.Button;

public class NewWindowThread extends Thread {

    private Button button;
    private String nameFxmlFile;

    public NewWindowThread(Button button, String nameFxmlFile) {
        this.button = button;
        this.nameFxmlFile = nameFxmlFile;
    }

    @Override
    public synchronized void start() {
        Ui.openNewWindow(button, nameFxmlFile);
    }
}
