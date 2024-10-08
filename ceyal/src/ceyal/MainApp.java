package ceyal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainApp extends Application {
    private TableView<EventLog> tableView;
    private ObservableList<EventLog> logData;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Process Mining Software");

        logData = FXCollections.observableArrayList();
        tableView = new TableView<>(logData);

        // Setting up columns for the table
        TableColumn<EventLog, String> eventColumn = new TableColumn<>("Event");
        eventColumn.setCellValueFactory(cellData -> cellData.getValue().eventProperty());

        TableColumn<EventLog, String> timestampColumn = new TableColumn<>("Timestamp");
        timestampColumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());

        tableView.getColumns().add(eventColumn);
        tableView.getColumns().add(timestampColumn);

        Button loadButton = new Button("Load Event Log");
        loadButton.setOnAction(e -> loadEventLog(primaryStage));

        Button analyzeButton = new Button("Analyze Event Log");
        analyzeButton.setOnAction(e -> performAnalysis());

        Button visualizeButton = new Button("Visualize Process");
        visualizeButton.setOnAction(e -> visualizeProcess());

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setBottom(loadButton);
        borderPane.setTop(analyzeButton);
        borderPane.setRight(visualizeButton);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadEventLog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Event Log File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            readEventLog(file);
        }
    }

    private void readEventLog(File file) {
        logData.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    logData.add(new EventLog(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performAnalysis() {
        ProcessMiningAnalysis analysis = new ProcessMiningAnalysis();

        // Process Discovery
        Map<String, Integer> discoveredProcess = analysis.processDiscovery(logData);
        StringBuilder discoveryResults = new StringBuilder("Process Discovery Results:\n");
        discoveredProcess.forEach((event, count) -> discoveryResults.append(event).append(": ").append(count).append("\n"));

        // Conformance Checking
        boolean isConformant = analysis.conformanceCheck(logData, "Start,Process,End");
        String conformanceMessage = isConformant ? "The process log conforms to the expected model." : "The process log does not conform to the expected model.";

        // Performance Mining
        double averageDuration = analysis.calculateAverageEventDuration(logData);
        
        // Display Results
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Process Mining Analysis Results");
        alert.setHeaderText(null);
        alert.setContentText(discoveryResults.toString() + conformanceMessage + "\nAverage Event Duration: " + averageDuration);
        alert.showAndWait();
    }

    private void visualizeProcess() {
        StringBuilder visualization = new StringBuilder("Visualizing Process:\n");
        
        for (EventLog log : logData) {
            visualization.append("Event: ").append(log.eventProperty().get())
                         .append(" at ").append(log.timestampProperty().get()).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Process Visualization");
        alert.setHeaderText(null);
        alert.setContentText(visualization.toString());
        alert.showAndWait();
    }
}
