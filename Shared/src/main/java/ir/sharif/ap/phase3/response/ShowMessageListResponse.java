package ir.sharif.ap.phase3.response;

import ir.sharif.ap.phase3.model.help.MassageFiller;

import java.util.List;

public class ShowMessageListResponse extends Response{

    private final List<MassageFiller> fillers;
    private final boolean isNotes;

    public ShowMessageListResponse(List<MassageFiller> fillers, boolean isNotes) {
        this.fillers = fillers;
        this.isNotes = isNotes;
    }

    public List<MassageFiller> getFillers() {
        return fillers;
    }

    public boolean isNotes() {
        return isNotes;
    }

    @Override
    public void visit(ResponseVisitor visitor) {
        visitor.visitShowMessageList(this);
    }
}