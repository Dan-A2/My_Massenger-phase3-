package ir.sharif.ap.phase3.event;

import ir.sharif.ap.phase3.event.chat.ChatVisitor;
import ir.sharif.ap.phase3.event.entrance.EntranceVisitor;
import ir.sharif.ap.phase3.event.general.GeneralVisitor;
import ir.sharif.ap.phase3.event.group.GroupVisitor;
import ir.sharif.ap.phase3.event.message.MessageVisitor;
import ir.sharif.ap.phase3.event.messaging.MessagingVisitor;
import ir.sharif.ap.phase3.event.sorting.SortingVisitor;
import ir.sharif.ap.phase3.event.tweet.TweetVisitor;
import ir.sharif.ap.phase3.event.user.UserVisitor;

public interface EventVisitor {
    GroupVisitor getGroupVisitor();
    UserVisitor getUserVisitor();
    ChatVisitor getChatVisitor();
    GeneralVisitor getGeneralVisitor();
    TweetVisitor getTweetVisitor();
    SortingVisitor getSortingVisitor();
    MessageVisitor getMessageVisitor();
    MessagingVisitor getMessagingVisitor();
    EntranceVisitor getEntranceVisitor();
}