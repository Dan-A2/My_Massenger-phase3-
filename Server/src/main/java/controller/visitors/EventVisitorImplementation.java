package controller.visitors;

import controller.ClientHandler;
import ir.sharif.ap.phase3.event.EventVisitor;
import ir.sharif.ap.phase3.event.chat.ChatVisitor;
import ir.sharif.ap.phase3.event.entrance.EntranceVisitor;
import ir.sharif.ap.phase3.event.general.GeneralVisitor;
import ir.sharif.ap.phase3.event.group.GroupVisitor;
import ir.sharif.ap.phase3.event.message.MessageVisitor;
import ir.sharif.ap.phase3.event.messaging.MessagingVisitor;
import ir.sharif.ap.phase3.event.sorting.SortingVisitor;
import ir.sharif.ap.phase3.event.tweet.TweetVisitor;
import ir.sharif.ap.phase3.event.user.UserVisitor;

public class EventVisitorImplementation implements EventVisitor {
    @Override
    public GroupVisitor getGroupVisitor() {
        return GroupHandler.getInstance();
    }

    @Override
    public UserVisitor getUserVisitor() {
        return UserHandler.getInstance();
    }

    @Override
    public ChatVisitor getChatVisitor() {
        return ChatHandler.getChatHandler();
    }

    @Override
    public GeneralVisitor getGeneralVisitor() {
        return GeneralEventsHandler.getHandler();
    }

    @Override
    public TweetVisitor getTweetVisitor() {
        return TweetHandler.getInstance();
    }

    @Override
    public SortingVisitor getSortingVisitor() {
        return SortingHandler.getInstance();
    }

    @Override
    public MessageVisitor getMessageVisitor() {
        return MessageHandler.getInstance();
    }

    @Override
    public MessagingVisitor getMessagingVisitor() {
        return MessagingHandler.getInstance();
    }

    @Override
    public EntranceVisitor getEntranceVisitor() {
        return ClientHandler.getInstance();
    }
}