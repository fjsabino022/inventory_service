package com.goeuro.inventory.bdd.steps;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.webhook.WebhookPayloads.payload;
import static org.assertj.core.api.Assertions.assertThat;

import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import io.cucumber.java8.En;
import org.springframework.http.HttpStatus;

public class SendMessageSteps implements En {

  private static final String WEBHOOK_URL =
      "https://hooks.slack.com/services/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";
  private String message;
  private String destination;
  private WebhookResponse response;

  public SendMessageSteps() {
    Given(
        "^I have my (.+)$",
        (String message) -> {
          this.message = message;
        });

    And(
        "^I have the (.+) of my message$",
        (String destination) -> {
          this.destination = destination;
        });

    When(
        "^I send the message$",
        () -> {
          final var slack = Slack.getInstance();
          response =
              slack.send(
                  WEBHOOK_URL,
                  payload(
                      p ->
                          p.text("This is a message for testing.")
                              .blocks(
                                  asBlocks(
                                      section(
                                          section ->
                                              section.text(
                                                  markdownText(
                                                      "*This is a message for testing*")))))));
        });

    Then(
        "^The message is sent correctly$",
        () -> {
          assertThat(response).isNotNull();
          assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
        });
  }
}
