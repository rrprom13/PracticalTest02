package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class CommunicationThread extends Thread {

    private Socket socket;
    private ServerThread serverThread;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Log.i(Constants.TAG, "Comm Thread run start");

            BufferedReader br = Utilities.getReader(socket);
            PrintWriter pw = Utilities.getWriter(socket);

            String command = br.readLine();
            String date = br.readLine();

            Log.i(Constants.TAG, command);
            Log.i(Constants.TAG, date);

            if (command.equals(Constants.SET_COMMAND)) {
                serverThread.addDate(date);
            } else if (command.equals(Constants.RESET_COMMAND)) {
                serverThread.removeDate(date);
            } else {
                if (!serverThread.containsDate(date)) {
                    pw.println("none");
                }

                Socket netSocket = new Socket(Constants.NETWORK_HOST, Constants.NETWORK_PORT);
                BufferedReader bufferedReader = Utilities.getReader(netSocket);
                bufferedReader.readLine();
                String dayTimeProtocol = bufferedReader.readLine();
                Log.d(Constants.TAG, "The server returned: " + dayTimeProtocol);

                DateFormat format = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
                Date clientDate = format.parse(date);
                System.out.println(clientDate);

                String[] atoms = dayTimeProtocol.split(" ");
                dayTimeProtocol = atoms[2];
                Date netDate = format.parse(dayTimeProtocol);
                System.out.println(netDate);

                if (netDate.after(clientDate)) {
                    pw.println("active");
                } else {
                    pw.println("inactive");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
