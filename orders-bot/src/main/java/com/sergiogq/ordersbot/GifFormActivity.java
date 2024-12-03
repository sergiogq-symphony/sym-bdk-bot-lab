package com.sergiogq.ordersbot;

import com.symphony.bdk.core.activity.ActivityMatcher;
import com.symphony.bdk.core.activity.form.FormReplyActivity;
import com.symphony.bdk.core.activity.form.FormReplyContext;
import com.symphony.bdk.core.activity.model.ActivityInfo;
import com.symphony.bdk.core.activity.model.ActivityType;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.gen.api.model.V4Message;

public class GifFormActivity extends FormReplyActivity<FormReplyContext> {

  private final MessageService messageService;

  public GifFormActivity(MessageService messageService) {
    this.messageService = messageService;
  }

  @Override
  public ActivityMatcher<FormReplyContext> matcher() {
    return context -> "gif-category-form".equals(context.getFormId())
        && "submit".equals(context.getFormValue("action"));
  }

  @Override
  public void onActivity(FormReplyContext context) {
    final String category = context.getFormValue("category");
    final String message = "<messageML>Received category '" + category + "'</messageML>";
    this.messageService.send(context.getSourceEvent().getStream(), Message.builder().content(message).build());

    final String message2 = "<messageML>/gif2</messageML>";

    long currentTime = System.currentTimeMillis();
    V4Message sendMessage = this.messageService.send(context.getSourceEvent().getStream(), Message.builder().content(message2).build());
    System.out.println("The /gif2 with identifier " + sendMessage.getMessageId() + " was send at: " + currentTime);
  }

  @Override
  protected ActivityInfo info() {
    return new ActivityInfo().type(ActivityType.FORM)
        .name("Gif Display category form command")
        .description("\"Form handler for the Gif Category form\"");
  }
}
