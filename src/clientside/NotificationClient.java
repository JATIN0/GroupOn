package clientside;

/**
 *
 * @author iLLuMinatTi
 */
class NotificationClient 
{
    String content;
    boolean isNew;
    
    public NotificationClient(String content,boolean isNew)
    {
        this.content = content;
        this.isNew = isNew;
    }
    
    @Override
    public String toString()
    {
        return content;
    }
            
}
