
package serverside;

import java.net.Socket;

/**
 *
 * @author iLLuMinaTi
 */

//This is thread of AuthServer which will run individually for each user...
class AuthServerThread implements Runnable{
    private DataExchangeServer des;
    private Util utility;
    public AuthServerThread(Socket sock, Util utility)
    {      
        des=new DataExchangeServer(sock);
        this.utility = utility;
    }
    
    
    private boolean verifyLogin(){
        System.out.println("Verify Login....");
        String username = des.getData();           
        String password = des.getData();
        String[] res = utility.loginCheck(username,password);
        des.send(res);
        System.out.println(res.length);
        return res.length==3;
    }
    
    private void verifyRegister(){
        //System.out.println("inside verify register");
        String firstName=des.getData(); 
        String lastName=des.getData(); 
        String userName=des.getData(); 
        String password=des.getData(); 
        //System.out.println("PASSWORD= "+password);
        String tmp=utility.registerCheck(firstName,lastName,userName,password);
        //System.out.println("TMP= "+tmp);
        if("ok".equals(tmp)){
            String[] msg={"ok"};
            des.send(msg);
        }
        else{
            String[] msg={tmp};
            des.send(msg);
        } 
    }
    
    private void registerInDB(){
        String firstName=des.getData();
        String lastName=des.getData();
        String userName=des.getData();
        String password=des.getData();
        //System.out.println("inside authserver , registerInDB fname="+firstName+" lastName="+lastName);

        boolean isValid=utility.register(firstName,lastName,userName,password);
        //System.out.println("in registerInDB Value of isValid "+isValid);
        if(isValid){
            String[] msg={"ok"};
            des.send(msg);
        }
        else{
            String[] msg={"Could not register successfully"};
            des.send(msg);
        }
    }
    
    
    @Override
    @SuppressWarnings("ConvertToStringSwitch")
    public void run(){
        while(true)
        {   
            System.out.println("Inside run...");
            String type = des.getData();
            if("login".equals(type))
            {
                if(this.verifyLogin())
                {
                    System.out.println("Logged In..");
                    break;
                }
               
            }
            else if("registerVerify".equals(type)){
                this.verifyRegister();
            }      
            else if("register".equals(type)){
                this.registerInDB();
            }
            else if(("").equals(type))
                break;
        }
    }
}
