package io.github.shaksternano.pinbot.command;

import io.github.shaksternano.pinbot.PinBotSettings;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Collection;
import java.util.List;

public class PinChannelSetCommand extends PinChannelSubCommand {

    private static final PinChannelSetCommand INSTANCE = new PinChannelSetCommand();
    private static final String CHANNEL_OPTION = "channel";

    private PinChannelSetCommand() {
        super("set", "Sets the channel where pins from this channel are sent to.");
    }

    @Override
    public String execute(SlashCommandInteractionEvent event) {
        Guild guild = getGuild(event);
        OptionMapping mapping = getRequiredOption(event, CHANNEL_OPTION);
        Channel sendPinFrom = event.getChannel();
        Channel sendPinTo = mapping.getAsChannel();
        if (sendPinTo instanceof IWebhookContainer) {
            PinBotSettings.setPinChannel(sendPinFrom.getIdLong(), sendPinTo.getIdLong(), guild.getIdLong());
            return "Pins from " + sendPinFrom.getAsMention() + " will now be sent to " + sendPinTo.getAsMention() + ".";
        } else {
            return sendPinTo.getAsMention() + " is not a message channel that supports webhooks!";
        }
    }

    @Override
    public Collection<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.CHANNEL, CHANNEL_OPTION, "The channel where pins from this channel are sent to.", true));
    }

    public static PinChannelSetCommand getInstance() {
        return INSTANCE;
    }
}
