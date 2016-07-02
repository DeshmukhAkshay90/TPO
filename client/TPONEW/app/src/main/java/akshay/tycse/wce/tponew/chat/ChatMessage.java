package akshay.tycse.wce.tponew.chat;

/**
 * Created by akki on 10/6/16.
 */
public class ChatMessage
{
    public boolean left;
    public String message;
    public  String time;
    public ChatMessage(boolean left, String message,String time) {
        super();
        this.left = left;
        this.message = message;
        this.time=time;
    }
}
