package ro.pub.cs.systems.eim.practicaltest02.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ro.pub.cs.systems.eim.practicaltest02.R;
import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.network.ClientThread;

public class ClientFragment extends Fragment {

    private ClientThread clientThread;

    private EditText portEditText;
    private EditText dateEditText;
    private Button setButton;
    private Button resetButton;
    private Button pollButton;
    private TextView resultTextView;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String port = portEditText.getText().toString();
            String date = dateEditText.getText().toString();

            if (view.getId() == R.id.client_set_button) {
                clientThread = new ClientThread(resultTextView, Constants.SET_COMMAND, port, date);
            } else if (view.getId() == R.id.client_reset_button) {
                clientThread = new ClientThread(resultTextView, Constants.RESET_COMMAND, port, date);
            } else {
                clientThread = new ClientThread(resultTextView, Constants.POLL_COMMAND, port, date);
            }
            clientThread.start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        return inflater.inflate(R.layout.client_frame_layout, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        portEditText = (EditText) getActivity().findViewById(R.id.client_port_edit_text);
        dateEditText = (EditText) getActivity().findViewById(R.id.client_date_edit_text);
        setButton = (Button) getActivity().findViewById(R.id.client_set_button);
        resetButton = (Button) getActivity().findViewById(R.id.client_reset_button);
        pollButton = (Button) getActivity().findViewById(R.id.client_poll_button);
        resultTextView = (TextView) getActivity().findViewById(R.id.client_result_text_view);

        setButton.setOnClickListener(buttonClickListener);
        resetButton.setOnClickListener(buttonClickListener);
        pollButton.setOnClickListener(buttonClickListener);
    }

}
