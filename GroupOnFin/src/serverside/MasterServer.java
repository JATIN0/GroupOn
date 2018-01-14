package serverside;



/**
 *
 * @author iLLuMinaTi
 */

//This file must be run inorder to start a server

//This is class which will run all the servers
class MasterServer {
    private AuthServer authServer = null;
    private DashServer dashServer = null;
    private ChatServer chatServer = null;
    public MasterServer()
    {
        //starting all the servers
        authServer = new AuthServer(2789); //server listening at port 2789
        dashServer = new DashServer(1221); //server listening at port 1221  
        chatServer = new ChatServer(3487); //server listening at port 3487
    }
    public static void main(String [] args)
    {
        MasterServer ms = new MasterServer();   
        ms.startServers();
    }
    
    public void startServers()
    {
        Thread st1 = new Thread(new MasterServerThread(authServer));
        st1.start();
        
        Thread st2 = new Thread(new MasterServerThread(dashServer));
        st2.start();
        
        Thread st3 = new Thread(new MasterServerThread(chatServer));
        st3.start();
        
    }
}
