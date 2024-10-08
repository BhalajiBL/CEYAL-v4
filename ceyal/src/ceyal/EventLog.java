package ceyal;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventLog {
    private final StringProperty event;
    private final StringProperty timestamp;

    public EventLog(String event, String timestamp) {
        this.event = new SimpleStringProperty(event);
        this.timestamp = new SimpleStringProperty(timestamp);
    }

    public StringProperty eventProperty() {
        return event;
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }
}
