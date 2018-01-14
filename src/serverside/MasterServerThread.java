package serverside;

/**
 *
 * @author iLLuMinatTi
 */
class MasterServerThread implements Runnable
{
    ServerClass s;
    public MasterServerThread(ServerClass s)
    {
        this.s = s;
    }
    
    @Override
    public void run()
    {
        s.communicate();
    }
}
