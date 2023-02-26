package com.example.demo.domain.event.asyncEvents;

import com.example.demo.core.exception.OpenAIResponseUnprocessableException;
import com.example.demo.core.security.helpers.SecretManager;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class TextAnalyzer {
    private final SecretManager secretManager;


    public TextAnalyzer(SecretManager secretManager) throws FileNotFoundException {
        this.secretManager = secretManager;
    }

    private ResponseTypes getResposeTypeForString(String response) {
        if (Pattern.compile("[0-9].*").matcher(response).find()) {
            return ResponseTypes.NUMBERED_LIST;
        } else {
            System.out.println("Response that isn't numbered list: " + response);
        }
        if (Pattern.compile("[a-zA-Z]+,").matcher(response).find()) {
            return ResponseTypes.COMMA_SEPERATED;
        } else {
            System.out.println("Response that isn't comma-seperated list: " + response);
        }
        return ResponseTypes.NUMBERED_LIST;
    }

    private List<String> getLabelsForResponse(String response) throws OpenAIResponseUnprocessableException {
        ResponseTypes responseType = getResposeTypeForString(response);

        switch (responseType) {
            case NUMBERED_LIST -> {
                return getLabelsForNumberedListResponse(response);
            }
            case COMMA_SEPERATED -> {
                return getLabelsForCommaSeperatedResponse(response);
            }
        }
        throw new OpenAIResponseUnprocessableException("Unable to process OpenAi response: " + response + "\n. Response wasn't a numbered list, nor a comma-delimited one");
    }

    private List<String> getLabelsForCommaSeperatedResponse(String response) {
        return Arrays.stream(response.split(",")).map(String::trim).toList();
    }

    private List<String> getLabelsForNumberedListResponse(String response) {
        return Arrays.stream(response.split("\n"))
                .map(label -> label.replaceFirst("\\d. ", ""))
                .filter(label -> Pattern.compile("[a-zA-Z]").matcher(label).find())
                .toList();
    }

    private String getOpenAiTextCompletion(String query) {
        String apiKey = secretManager.getSecret("projects/869490409088/secrets/OpenAI_APIkey");
        OpenAiService service = new OpenAiService(apiKey);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(query)
                .model("text-davinci-001")
                .temperature(0.0)
                .build();
        List<String> responses = service.createCompletion(completionRequest).getChoices()
                .stream()
                .map(completionChoice -> completionChoice.getText())
                .toList();

        return responses.get(0);
    }

    /**
     * Get the labels for the provided text. This method is a wrapper around https://fasttext.cc/.
     *
     * @param text           Text for which the labels should be analyzed
     * @param numberOfLabels How many labels should be predicted?
     * @return This returns a list of pairs. Each pair consists of a label and a the confidence of said label.
     */
    public List<String> getLabelsForText(String text, int numberOfLabels) throws OpenAIResponseUnprocessableException {
        String query = String.format("Create %d one-word labels for this text: \"%s\". Don't provide any description:", numberOfLabels, text);
        String response = getOpenAiTextCompletion(query);
        return getLabelsForResponse(response);
    }
}

enum ResponseTypes {
    NUMBERED_LIST,
    COMMA_SEPERATED
}
