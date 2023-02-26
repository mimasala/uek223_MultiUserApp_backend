package com.example.demo.domain.event.asyncEvents;

import com.example.demo.core.exception.OpenAIResponseUnprocessableException;
import io.gorse.gorse4j.Gorse;
import io.gorse.gorse4j.Item;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Log4j2
public class EventCreationEventHandler implements ApplicationListener<EventCreatedMessage> {
    private final TextAnalyzer analyzer;

    private final Gorse client;

    @Autowired
    public EventCreationEventHandler(TextAnalyzer analyzer, Gorse client) throws SQLException {
        this.analyzer = analyzer;
        this.client = client;
    }

    private String convertTimeStampToTimeString(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+1"))
                .format(DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss +01:00"));
    }

    @Override
    public void onApplicationEvent(EventCreatedMessage event) {
        try {
            List<String> labels = analyzer.getLabelsForText(event.getEvent().getEventName(), 5);

            client.insertItem(new Item(
                    event.getEvent().getId().toString(),
                    false, labels, labels,
                    convertTimeStampToTimeString(event.getTimestamp()), ""
            ));
        } catch (OpenAIResponseUnprocessableException e) {
            log.error("Failed to find label for event: " + event.getEvent().getId());
            return; //TODO(hugn): Find a better behavior if the previous step fails, like send to human reviewer.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
