package net.steppschuh.slackmessagebuilder;

import net.steppschuh.slackmessagebuilder.message.MessageAttachment;
import net.steppschuh.slackmessagebuilder.message.MessageAttachmentBuilder;
import net.steppschuh.slackmessagebuilder.message.MessageAttachmentField;
import net.steppschuh.slackmessagebuilder.message.MessageBuilder;
import net.steppschuh.slackmessagebuilder.message.MessageLink;
import net.steppschuh.slackmessagebuilder.request.Webhook;

import org.junit.Test;

public class MessageBuilderUnitTest {

    @Test
    public void builder_exampleMessage() throws Exception {
        // create a webhook
        String hookUrl = "https://hooks.slack.com/services/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";
        Webhook webhook = new Webhook(hookUrl);

        // create some content
        MessageLink gitHubLink = new MessageLink("https://github.com/Steppschuh/SlackMessageBuilder", "GitHub repo");

        // create a message
        MessageBuilder messageBuilder = new MessageBuilder()
                .setChannel("#random")
                .setUsername("Slack Message Builder")
                .setIconEmoji(":+1:")
                .setText("I'm the message text with a link to a " + gitHubLink);

        // add some attachments
        int attachmentCount = 3;
        for (int index = 1; index <= attachmentCount; index++) {
            messageBuilder.addAttachment(generateMessageAttachment(index));
        }

        // send message
        webhook.postMessageSynchronous(messageBuilder.build());
    }

    public static MessageAttachment generateMessageAttachment(int index) {
        MessageAttachmentBuilder attachmentBuilder = new MessageAttachmentBuilder()
                .setTitle("Attachment #" + index)
                .setText("This is the main text in message attachment " + index + ", and can contain standard message markup.")
                .setColor(getHexCode(index))
                .addField(new MessageAttachmentField("User", System.getProperty("user.name")))
                .addField(new MessageAttachmentField("Index", String.valueOf(index)))
                .setFooter("Created by " + MessageBuilderUnitTest.class.getSimpleName());

        return attachmentBuilder.build();
    }

    public static String getHexCode(int index) {
        String[] colors = {"#00BCD4", "#2196F3", "#673AB7", "#E91E63", "#795548"};
        return colors[Math.abs(index % (colors.length - 1))];
    }

}