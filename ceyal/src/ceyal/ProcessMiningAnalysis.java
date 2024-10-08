package ceyal;

import javafx.collections.ObservableList;
import java.util.HashMap;
import java.util.Map;

public class ProcessMiningAnalysis {

    // Method for Process Discovery
    public Map<String, Integer> processDiscovery(ObservableList<EventLog> logs) {
        Map<String, Integer> eventCounts = new HashMap<>();
        for (EventLog log : logs) {
            String event = log.eventProperty().get();
            eventCounts.put(event, eventCounts.getOrDefault(event, 0) + 1);
        }
        return eventCounts;
    }

    // Method for Conformance Checking
    public boolean conformanceCheck(ObservableList<EventLog> logs, String expectedProcessModel) {
        for (String expectedEvent : expectedProcessModel.split(",")) {
            boolean found = logs.stream().anyMatch(log -> log.eventProperty().get().equals(expectedEvent.trim()));
            if (!found) {
                return false;
            }
        }
        return true;
    }

    // Method for Performance Mining
    public double calculateAverageEventDuration(ObservableList<EventLog> logs) {
        // Implement logic to calculate average duration based on logs
        return logs.size() > 0 ? 1.0 : 0.0; // Placeholder
    }
}
