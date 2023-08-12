package server.nanum.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebhookExceptionHandler {
    private final RestTemplate restTemplate;

    private final String discordWebhookUrl = "https://discord.com/api/webhooks/1139835592797065297/XZlWtD1Qn5nbVPDO8E52hOGhkwCCd9057z0K66_ImQTLZpo2MxJ_4iQtj82gA3JaNfQM";

    public void sendExceptionWithDiscord(Exception ex) {
        int maxMessageLength = 1800;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = new HashMap<>();

            StringBuilder description = new StringBuilder();
            description.append("**").append(ex.getMessage()).append("**\n");

            StringBuilder stackTrace = new StringBuilder();
            for (StackTraceElement element : ex.getStackTrace()) {
                String elementString = element.toString() + "\n";
                if (stackTrace.length() + elementString.length() <= maxMessageLength) {
                    stackTrace.append(elementString);
                } else {
                    break;
                }
            }

            description.append("```").append(stackTrace).append("...```");
            description.append("\n\n").append(Instant.now().toString()); // Adding the timestamp

            payload.put("embeds", new Object[]{
                    new HashMap<String, Object>() {{
                        put("title", "\uD83D\uDEA8 예외 발생!");
                        put("description", description.toString());
                        put("color", 16711680);
                    }}
            });

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    discordWebhookUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Exception notification sent to Discord successfully.");
            } else {
                log.error("Failed to send exception notification to Discord. Response code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            log.error("Error sending exception notification to Discord: " + e.getMessage());
        }
    }
}
