package ir.sharif.ap.phase3.model.help;

public class WatchUserPageFiller {

    private final UserCopy showFrom; // 1
    private final UserCopy showTo; // 2
    private final boolean containFollowing12;
    private final boolean containFollowing21;
    private final boolean containRequester;
    private final boolean containBlackList12;
    private final boolean containBlackList21;
    private final boolean containMuted12;

    public WatchUserPageFiller(UserCopy showFrom, UserCopy showTo, boolean containFollowing12, boolean containFollowing21, boolean containRequester, boolean containBlackList12, boolean containBlackList21, boolean containMuted12) {
        this.showFrom = showFrom;
        this.showTo = showTo;
        this.containFollowing12 = containFollowing12;
        this.containFollowing21 = containFollowing21;
        this.containRequester = containRequester;
        this.containBlackList12 = containBlackList12;
        this.containBlackList21 = containBlackList21;
        this.containMuted12 = containMuted12;
    }

    public UserCopy getShowFrom() {
        return showFrom;
    }

    public UserCopy getShowTo() {
        return showTo;
    }

    public boolean isContainFollowing12() {
        return containFollowing12;
    }

    public boolean isContainFollowing21() {
        return containFollowing21;
    }

    public boolean isContainRequester() {
        return containRequester;
    }

    public boolean isContainBlackList12() {
        return containBlackList12;
    }

    public boolean isContainBlackList21() {
        return containBlackList21;
    }

    public boolean isContainMuted12() {
        return containMuted12;
    }
}