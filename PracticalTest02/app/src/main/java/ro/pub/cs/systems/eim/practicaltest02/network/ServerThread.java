package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.HashSet;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;

public class ServerThread extends Thread {

    public boolean isRunning;

    private ServerSocket serverSocket;

    private EditText serverPortEditText;
    private int serverPort;
    private HashSet<String> dates;

    public ServerThread(EditText serverPortEditText, int serverPort) {
        this.serverPortEditText = serverPortEditText;
        this.serverPort = serverPort;
    }

    public void startServer() {
        isRunning = true;
        start();
        Log.v(Constants.TAG, "startServer() method was invoked");
    }

    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
        }
        Log.v(Constants.TAG, "stopServer() method was invoked");
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    CommunicationThread communicationThread = new CommunicationThread(this, socket);
                    communicationThread.start();
                }
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
        }
    }

    public HashSet getDates() {
        return dates;
    }

    public void addDate(String date) {
        if (dates == null) {
            dates = new HashSet<>();
        }
        dates.add(date);
    }

    public void removeDate(String date) {
        if (dates == null || dates.size() == 0) {
            return;
        }
        dates.remove(date);
    }

    public boolean containsDate(String date) {
        if (dates == null || dates.size() == 0) {
            return false;
        }
        if (dates.contains(date)) {
            return true;
        }
        return false;
    }

}
