
package serverside;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author iLLuMinaTi
 */
class SessionClass {
    
    ArrayList<Integer> uidArr; // arraylist of the uid of all the clients involved in a session.
    ArrayList<DataExchangeServer> desArr;  // arraylist of the dataexchange server objects of all clients involved in the session.
    
    SessionClass(Integer[] uidArray,int n){//"n" denotes number of clients involved in a session     
       // System.out.println("serverside.SessionClass constructor");
        
        uidArr=new ArrayList<Integer>(n); 
        desArr=new ArrayList<DataExchangeServer>(n);
        
        for (int i = 0; i < n; i++){
            uidArr.add(uidArray[i]);
            desArr.add(null);
        }
        
        
        //System.out.println("Constructor finished");
    }
    
    void setDes(DataExchangeServer des,int ind){
        // System.out.println("inside setDes start !");
        System.out.println("inside setDes ind="+ind);
        DataExchangeServer set = desArr.set(ind, des);
        // System.out.println("inside setDes end !");
    }
    
    boolean isSocketNull(int indUser){
        System.out.println("Inside isSocketNull induser="+indUser);
        DataExchangeServer temp=desArr.get(indUser);
        if(temp==null)
            return true;
        return false;
    }   
}
