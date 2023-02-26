package com.example.demo.domain.event.asyncEvents;

import com.example.demo.core.exception.OpenAIResponseUnprocessableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventCreationEventHandler implements ApplicationListener<EventCreatedMessage> {
    private final TextAnalyzer analyzer;


    @Autowired
    public EventCreationEventHandler(TextAnalyzer analyzer) {
        this.analyzer = analyzer;
    }



    @Override
    public void onApplicationEvent(EventCreatedMessage event) {
        List<String> prediction = null;
        try {
            prediction = analyzer.getLabelsForText(event.getEvent().getEventName(), 5);
        } catch (OpenAIResponseUnprocessableException e) {
            return; //TODO(hugn): Find a better behavior if the previous step fails, like send to human reviewer.
        }

        System.out.println("Labels: ");
        for (String result : prediction) {
            System.out.println(result);
        }

    }
}
