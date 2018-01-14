package clientside;

import java.io.Serializable;

/**
 *
 * @author iLLuMinatTi
 */
public class Message implements Serializable
{
    private int msgId;
    private String sUName;
    private String rUName;
    private String dateTime;
    private String content;
    private int priority;
    
    public Message(int msgId,String sUName, String rUName,String dateTime,String content,int priority)
    {
        this.msgId = msgId;
        this.sUName = sUName;
        this.rUName = rUName;
        this.dateTime = dateTime;
        this.content = content;
        this.priority = priority;
    }
    
    public int getMsgID()
    {
        return msgId;
    }
    
    public String getSUName()
    {
        return sUName;
    }
    
    public String getRUName()
    {
        return rUName;
    }
    
    public String getDateTime()
    {
        return dateTime;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public int getPriority()
    {
        return priority;
    }
}
