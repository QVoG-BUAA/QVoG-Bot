/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.extensions;

import cn.edu.buaa.qvog.bot.config.EmailOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailClient {
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private final EmailOptions emailOptions;

    public void sendSuccess(String receiver, String... args) {
        if (args.length != 4) {
            log.error("Invalid arguments for sendSuccess");
            return;
        }
        log.info("Sending success email to {}", receiver);
        send("SuccessEmail.html", receiver, args);
    }

    public void sendFailed(String receiver, String... args) {
        if (args.length != 3) {
            log.error("Invalid arguments for sendFailed");
            return;
        }
        log.info("Sending failed email to {}", receiver);
        send("FailedEmail.html", receiver, args);
    }

    private void send(String template, String receiver, String... args) {
        String html;
        try {
            html = loadHtml(template, args);
        } catch (IOException e) {
            log.error("Failed to load email template", e);
            return;
        }
        MediaType mediaType = MediaType.parse("application/json");
        String content = String.format("""
                {
                    "from": {
                        "email": "%s",
                        "name": "QVoG.Bot"
                    },
                    "to": [
                        {
                            "email": "%s"
                        }
                    ],
                    "subject": "QVoG Scan Result",
                    "html": "%s"
                }
                """, emailOptions.getFrom(), receiver, html);
        log.info("Sending email: {}", content);
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + emailOptions.getKey())
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            log.info("Email sent, response: {}", response.body().string());
        } catch (IOException e) {
            log.error("Failed to send email", e);
        }
    }

    private String loadHtml(String template, String... args) throws IOException {
        String templatePath = Path.of(emailOptions.getTemplateDirectory(), template).toString();
        String html = Files.readString(Path.of(templatePath));

        for (int i = 0; i < args.length; i++) {
            html = html.replace("{" + i + "}", args[i]);
        }

        return html.replace("\n", "").replace("\"", "\\\"");
    }
}
