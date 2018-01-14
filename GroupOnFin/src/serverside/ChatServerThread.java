
package serverside;

import clientside.Message;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import static java.util.Arrays.sort;
import java.util.logging.Level;
import java.util.logging.Logger;
import static serverside.ChatServer.oosDash;
import static serverside.ChatServer.sessionArr;

/**
 *
 * @author iLLuMinaTi
 */
class ChatServerThread implements Runnable{
    private DataExchangeServer des;
    private Util utility;
    Socket clientSocket;
    
    int sUID;
    int rUID;
    String senderName;
    String receiverName;
    int num;//number of clients involved in process
    //SessionClass session;
    int id;
    int ind;//at what index in the session class array does our data stream occur
    public ChatServerThread(Socket sock,Util utility)
    {   
        this.clientSocket=sock;
        this.utility = utility;
        des=new DataExchangeServer(clientSocket);
        
    }
       
    void handleGroupChat(){
        
    }
    
    void handleSingleChat(){
        while(true){            
            Message msgObj=(Message)des.getObject();
            
            if("-1".equals(msgObj.getDateTime() )){
                
                int indexArr=Integer.parseInt(msgObj.getSUName());
                int idSession=Integer.parseInt(msgObj.getRUName());              
                System.out.println("received -1 from client and indexArr="+indexArr+" idSession="+idSession);
                sessionArr.get(indexArr).setDes(null, idSession);  
                break;
            }      
            
            String selfName=msgObj.getSUName();            
            String oppName=msgObj.getRUName();
            String msg=msgObj.getContent();
            String timeStamp=msgObj.getDateTime();
            int priority=msgObj.getPriority();
            
            System.err.println("Inside handleSingleChat() of "+selfName +" and sending message="+msg+" to "+oppName);
            
            int selfUId=utility.getUid(selfName);
            int oppUId=utility.getUid(oppName);
            
            boolean res=utility.insertMessage(oppUId,selfUId,msg,priority,"S",timeStamp);
            if(res)
                System.out.println("Message entered into databse successfully");
            else
                System.out.println("ERROR ! Message not entered into database successfully");
            
            SessionClass session=sessionArr.get(ind);
            
            if(session.isSocketNull(1-id)){
                System.err.println("opponent("+oppName+") des's of this session is null and hence sending a notification to "+oppName);
                ServerNotification notification= new ServerNotification(selfName,oppName,"message",timeStamp);                
                try {
                    oosDash.writeObject(notification);
                    oosDash.flush();
                    System.err.println("Notification successfully sent from chat server to "+oppName);
                } catch (IOException ex) {
                    System.err.println("OOPS!!! Notification is not successfully sent from chat server to "+oppName);
                    Logger.getLogger(ChatServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
            else{
                System.err.println("opponent("+oppName+") des's of this session is active and hence sending the message to "+oppName);
                DataExchangeServer des=session.desArr.get(1-id);
                des.sendObject(msgObj);
                System.err.println("Yay ! message successfully sent to "+oppName);
            }
            
        }
    }
    
    
    
    @Override
    public void run(){
        //now making a session variable       
                String type=des.getData();//type tells group chat or single chat
                int n,i;
                int id;//it will tell about the id of the sender(at what position in the array<of session class> does sender occur)
               
                
                SessionClass session;
                if("S".equals(type)){ //that means single chat
                    n=2;
                    receiverName=des.getData();
                    senderName=des.getData();
                    
                //    System.out.println("Inside chat server Receiver Name:-"+receiverName+" Sender name:-"+senderName);
                    sUID=utility.getUid(receiverName);//getting uid of receiver user
                    rUID=utility.getUid(senderName);//getting uid of sender user
                    
                    Integer[] uidArray={sUID,rUID};
                    System.out.println("receiver uid="+sUID+" sender uid="+rUID);
                    sort(uidArray);
                   // System.out.println("Hola !");
                    session=new SessionClass(uidArray,2); //session class
                   // System.out.println("des before !");
                    if(rUID<sUID){
                        session.setDes(des,0);    
                        id=0;
                        
                    }
                    else{
                        session.setDes(des,1);
                        id=1;
                    }   
                    
                 //   System.err.println("before is sender des null="+sessionArr.get(i).isSocketNull(id));
                    //System.out.println("des after !");
                    
                    
                    int flag=0; // to check whether the variable "session" is present in "sessionArr" or not. 
                    
                  //System.out.println("serverside.ChatServer.communicate() before");
                    
                        for(i=0;i<sessionArr.size();i++){
                            ArrayList<Integer>  temp1=sessionArr.get(i).uidArr;
                            ArrayList<Integer>  temp2=session.uidArr;
                            
                            int s=0,z=0;
                            for(Integer x:temp1){
                                
                                if(temp2.contains(x)==false){
                                    s=1;
                                }
                                z=1;
                            }
                            if((s==0)&&(z==1)){
                                flag=1;
                                break;
                            }
                            
                        }
                    
                    //System.out.println("serverside.ChatServer.communicate() after");
                    //here value of "i" will give the index of the "session"  variable in arrayList of sessionArr
                    if(flag==0){ // that means session is not present
                        sessionArr.add(session);
                        System.err.println("Session variable does not exist");
                    }
                    else{
                        System.err.println("Session variable already exists");
                        sessionArr.get(i).setDes(des, id);
                        System.err.println("receiverName="+receiverName+" is receiver des null="+sessionArr.get(i).isSocketNull(id));
                        System.err.println("senderName="+senderName+" is sender des null="+sessionArr.get(i).isSocketNull(id));
                        
                    }
                }
                
                
                else{ //that means group chat
                    session=null;
                    n=100;
                    id=i=100;
                }
                
                this.id=id;
                this.ind=i;
                int x=id;
                
                this.num=n;
                
                String[] temp={"<>?",Integer.toString(ind),Integer.toString(id)};
            if(n==2){
                System.err.println("id="+id+" x="+x);
                
                //sending session info to the client
                des.send(temp);
                
                //sending old messages to the client
                des.send(new String[] {"><??"});
                
                ArrayList<Message> arrMsg;
                arrMsg = utility.getOldMsgsSingle(String.valueOf(sUID),String.valueOf(rUID), senderName, receiverName);
                
                //sending all the messages
                for(i=0;i<arrMsg.size();i++)
                    des.sendObject(arrMsg.get(i));
                
                //sending end message
                des.sendObject(new Message(0,"","","<>/","",0));
                this.handleSingleChat();
            }
    }
}
