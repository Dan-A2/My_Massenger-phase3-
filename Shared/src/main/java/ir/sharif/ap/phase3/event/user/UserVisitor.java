package ir.sharif.ap.phase3.event.user;

import ir.sharif.ap.phase3.response.Response;

public interface UserVisitor {
    Response visitBlock(BlockEvent event);
    Response visitChangeSetting(ChangeSettingsEvent event);
    Response visitDeleteAcc(DeleteUserEvent event);
    Response visitRequest(DoRequestEvent event);
    Response visitFollowOrUnfollow(Follow_UnfollowEvent event);
    Response visitFollowOrUnfollowRequest(FollowRequestEvent event);
    Response visitMuteOrUnmute(Mute_UnmuteEvent event);
    Response visitEditProfile(ProfileEditEvent event);
    Response visitWatchPage(WatchUserPageEvent event);
    Response visitUnsaveMessage(UnsaveMessageEvent event);
    Response visitShowList(ShowAListEvent event);
    Response visitDeleteNote(DeleteNoteEvent event);
}