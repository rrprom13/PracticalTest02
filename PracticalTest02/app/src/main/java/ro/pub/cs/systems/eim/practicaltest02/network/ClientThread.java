package ro.pub.cs.systems.eim.practicaltest02.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class ClientThread extends Thread {

    private TextView resultTextView;
    private String command;
    private int port;
    private String date;

    public ClientThread(TextView resultTextView, String command, String portString, String date) {
        this.resultTextView = resultTextView;
        this.command = command;
        this.port = Integer.parseInt(portString);
        this.date = date;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(Constants.SERVER_HOST, this.port);
            BufferedReader br = Utilities.getReader(socket);
            PrintWriter pw = Utilities.getWriter(socket);

            pw.println(this.command);
            pw.println(this.date);

            final String result = br.readLine();
            resultTextView.post(new Runnable() {
                @Override
                public void run() {
                    resultTextView.setText(result);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    @Override
    protected Void doInBackground(String... params) {
        try {


            pw.println(params[0]);
            pw.println(params[2]);

            String result = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        resultTextView.setText("");
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        Log.i(Constants.TAG, progress[0]);
        // TODO exercise 6b
        // - append the content to serverMessageTextView
        // String serverMessageString = serverMessageTextView.getText().toString();
        //    serverMessageTextView.setText(serverMessageString + progress[0]);
    }

    @Override
    protected void onPostExecute(Void result) {}
*/
}
