package com.sergiogq.myBot;

import com.symphony.bdk.core.SymphonyBdk;
import com.symphony.bdk.core.service.datafeed.RealTimeEventListener;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4UserJoinedRoom;
import com.symphony.bdk.template.api.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.symphony.bdk.core.config.BdkConfigLoader.loadFromClasspath;
import static com.symphony.bdk.core.activity.command.SlashCommand.slash;
import static java.util.Collections.singletonMap;

/**
 * Simple Bot Application.
 */
public class BotApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(BotApplication.class);

  public static void main(String[] args) throws Exception {

    // Initialize BDK entry point
    final SymphonyBdk bdk = new SymphonyBdk(loadFromClasspath("/config.yaml"));

    // Register a "slash" activity
    bdk.activities().register(slash("/gif2", false, context -> {
        ZonedDateTime utcTime = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
        String formattedTime = utcTime.format(formatter);
        System.out.println("The /gif2 was received at: " + formattedTime);

        Template template = bdk.messages().templates().newTemplateFromClasspath("/templates/gif.ftl");
        bdk.messages().send(context.getStreamId(), Message.builder().template(template).build());
    }));

    // Register a "formReply" activity that handles the Gif category form submission
    bdk.activities().register(new GifFormActivity(bdk.messages()));

    // Subscribe to 'onUserJoinedRoom' Real Time Event
    bdk.datafeed().subscribe(new RealTimeEventListener() {

      @Override
      public void onUserJoinedRoom(V4Initiator initiator, V4UserJoinedRoom event) {
        LOGGER.info("User joined room");
        final String userDisplayName = event.getAffectedUser().getDisplayName();
        Template template = bdk.messages().templates().newTemplateFromClasspath("/templates/welcome.ftl");
        bdk.messages().send(event.getStream(),
            Message.builder().template(template, singletonMap("name", userDisplayName)).build());
      }
    });

    // finally, start the datafeed read loop
    bdk.datafeed().start();
  }
}
