package com.tomohamat.apicem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tomohamat.apicem.Model.DeviceLicense;
import com.tomohamat.apicem.Model.NetworkDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SwitchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SwitchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwitchFragment extends MyAppFragment {

    private static final String TAG = "SwitchFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    //    private TextView mSwitchFragmentView;
    private static final String ARG_PARAM2 = "param2";
    private Spinner mSpinner;
    private ArrayList<NetworkDevice> networkDevices, switches;
    private NetworkDevice networkDevice;
    private Button mGetDeviceDetails, mGetDeviceLicense, mCliRunnerButton;
    private EditText mCliEditText;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SwitchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SwitchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SwitchFragment newInstance(String param1, String param2) {
        SwitchFragment fragment = new SwitchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public String getCli() {
        return mCliEditText.getText().toString();
    }

    public String getSelectedDeviceId() {
        return switches.get(mSpinner.getSelectedItemPosition()).getId();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_switch, container, false);

        buttons = new ArrayList<>();

        mSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        mGetDeviceDetails = (Button) rootView.findViewById(R.id.deviceDetailsButton);
        mGetDeviceDetails.setOnClickListener(listener);
        buttons.add(mGetDeviceDetails);

        mGetDeviceLicense = (Button) rootView.findViewById(R.id.deviceLicenseButton);
        mGetDeviceLicense.setOnClickListener(listener);
        buttons.add(mGetDeviceLicense);

        mCliEditText = (EditText) rootView.findViewById(R.id.cliEditText);
        mCliRunnerButton = (Button) rootView.findViewById(R.id.cliRunnerButton);
        mCliRunnerButton.setOnClickListener(listener);
        buttons.add(mCliRunnerButton);

        mResultTextView = (TextView) rootView.findViewById(R.id.switchFragmentTextView);

//        disableButtons();

        return rootView;
    }

    /*
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }
    */

    public void setDeviceLicense(DeviceLicense deviceLicense) {

    }

    public void setNetworkDevice(NetworkDevice device) {
        this.networkDevice = device;
        mResultTextView.setText(device.toString());
    }

    public void setNetworkDevices(ArrayList<NetworkDevice> devices) {
        networkDevices = new ArrayList<NetworkDevice>();
        switches = new ArrayList<NetworkDevice>();
        List<String> spinnerArray = new ArrayList<String>();

        if (null != devices) {
            for (NetworkDevice device : devices) {
                networkDevices.add(device);
                if (device.isSwitch()) {
                    switches.add(device);
                    spinnerArray.add(device.getHostname());
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        if (0 < switches.size()) {
            enableButtons();
        } else {
            disableButtons();
        }
    }

}
