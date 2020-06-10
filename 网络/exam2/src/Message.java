import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Objects;

/**
 * Message
 *
 * @author song
 * @version 1.0
 * @see java.io.Serializable
 */

public class Message implements Serializable {
    private String senderName;
    private String receiverName;
    private String date;
    private String text;

    /**
     * 构造函数
     */
    public Message(String senderName, String receiverName, String date, String text) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.date = date;
        this.text = text;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(senderName, message.senderName) &&
                Objects.equals(receiverName, message.receiverName) &&
                Objects.equals(date, message.date) &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderName, receiverName, date, text);
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
