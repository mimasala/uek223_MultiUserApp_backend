package com.example.demo.core.security.helpers;

import com.example.demo.core.exception.UnableToOptainSecretException;
import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.ProjectName;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SecretManager {
    @Value("${gcp.projectId")
    private String projectId;

    public String getSecret(String secretName) {
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            // Build the parent name from the project.
            ProjectName projectName = ProjectName.of(projectId);

            // Access the secret version.
            AccessSecretVersionResponse response = client.accessSecretVersion(secretName);
            return response.getPayload().getData().toStringUtf8();
        } catch (IOException e) {
            throw new UnableToOptainSecretException("Failed to get secret!");
        }

    }
}
