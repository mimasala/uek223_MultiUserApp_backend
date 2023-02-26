package com.example.demo.domain.event.asyncEvents;

import com.example.demo.core.exception.OpenAIResponseUnprocessableException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventCreationEventHandler(TextAnalyzer analyzer, JdbcTemplate jdbcTemplate) throws SQLException {
        this.analyzer = analyzer;
        this.jdbcTemplate = jdbcTemplate;
    }

    private String convertTimeStampToTimeString(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+1"))
                .format(DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss +01:00"));
    }

    private void insertIntoItems(String itemId, String labels, String categories, String comment, boolean isHidden, Long timestamp) throws SQLException {
        String timeDateWithTimeZone = convertTimeStampToTimeString(timestamp);
        jdbcTemplate.execute(String.format("INSERT INTO items (item_id, categories, comment, is_hidden, labels, time_stamp) VALUES('%s', '%s', '%s', %b, '%s', '%s')", itemId, categories, comment, isHidden, labels, timeDateWithTimeZone));
    }


    @Override
    public void onApplicationEvent(EventCreatedMessage event) {
        List<String> labels = null;
        try {
            labels = analyzer.getLabelsForText(event.getEvent().getEventName(), 5);
        } catch (OpenAIResponseUnprocessableException e) {
            log.error("Failed to find label for event: " + event.getEvent().getId());
            return; //TODO(hugn): Find a better behavior if the previous step fails, like send to human reviewer.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String labelString = "[\"" + String.join("\", \"", labels) + "\"]";
        try {
            insertIntoItems(event.getEvent().getId().toString(), labelString, labelString, "No comment", false, event.getTimestamp());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
