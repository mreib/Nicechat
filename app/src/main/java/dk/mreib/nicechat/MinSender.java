package dk.mreib.nicechat;

import android.os.AsyncTask;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatMessageListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.android.AndroidSmackInitializer;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * Created by mreib on 25-10-14.
 */
public class MinSender extends AsyncTask {


    XMPPTCPConnection connection=null;
    Chat chat=null;
    Exception exception;
    String user="";
    String password="";
    String friend="";
    String mymessage="";


    private XMPPTCPConnection getConnection() throws IOException, XMPPException, SmackException {
        if (connection == null) {

/*
            ConnectionConfiguration config =
                   new ConnectionConfiguration(GCM_SERVER, GCM_PORT);
            config.setSecurityMode(SecurityMode.enabled);
            config.setReconnectionAllowed(true);
            config.setRosterLoadedAtLogin(false);
            config.setSendPresence(false);
            config.setSocketFactory(SocketFactory.getDefault());

*/


// Create the configuration for this new connection_

            //AndroidSmackInitializer androidSmackInitializer = new AndroidSmackInitializer();
            //androidSmackInitializer.initialize();

            ConnectionConfiguration config = new ConnectionConfiguration("mire.dk",5222);
            //ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
            //config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);

            connection = new XMPPTCPConnection(config);
            //AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            //XMPPTCPConnection connection = new XMPPTCPConnection(config);
            //connection.setPacketReplyTimeout(1000);

//talk.google.com
//gmail.com


            connection.connect();
            System.out.println("After connect");

            connection.login(getUser(), getPassword());
            System.out.println("After login");
            //connection.login("michael.reib@gmail.com", "3996UGH1");
        }
        //connection.disconnect();
        return connection;
    }

    public Chat getChat() throws XMPPException, IOException, SmackException {
        if (chat == null) {
            // Start a new conversation with John Doe and send him a message.
            chat = ChatManager.getInstanceFor(getConnection()).createChat(getFriend(), new ChatMessageListener() {
            //chat = ChatManager.getInstanceFor(getConnection()).createChat("test1@mire.dk", new ChatMessageListener() {
                public void processMessage(Chat chat, Message message) {
                    // Print out any messages we get back to standard out.
                    System.out.println("Received message: " + message);
                }


            });
        }
        try {
            chat.sendMessage(getMymessage());
        }
        catch (XMPPException e) {
            System.out.println("Error Delivering block");
        }
        return chat;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getMymessage() {
        return mymessage;
    }

    public void setMymessage(String mymessage) {
        this.mymessage = mymessage;
    }

/*
    protected void onPreExecute(Void void1) {
        // do nothing;
    }
*/


    @Override
    protected Object doInBackground(Object[] params) {
        try {
            for (Object param : params) {
                System.out.println("param = " + param);
            }

            setUser((String) params[0]);
            setPassword((String) params[1]);
            setFriend((String) params[2]);
            setMymessage((String) params[3]);
            getChat();
            connection.disconnect();
        } catch (XMPPException e) {
            this.exception = e;
        } catch (IOException e) {
            this.exception = e;
        } catch (SmackException e) {
            this.exception = e;
        }
        return null;
    }

    protected void onPostExecute(Void void1) {
        // do nothing;
    }

/*    protected void onProgressUpdate(Void void1) {
        // do nothing;
    }
*/

    public Exception getException() {
        return exception;
    }

    public MinSender() {
    }
}
