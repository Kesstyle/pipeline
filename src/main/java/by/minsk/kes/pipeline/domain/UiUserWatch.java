package by.minsk.kes.pipeline.domain;

public class UiUserWatch {

    private Long userId;
    private Long lastMessage;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(final Long lastMessage) {
        this.lastMessage = lastMessage;
    }
}
