package com.bond.android.familymap;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by bondd on 11/14/2017.
 */

public class LoginFragment extends Fragment
{
    private EditText mServerHost;
    private EditText mServerPort;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private RadioGroup mGender;
    private Button mSignIn;
    private Button mRegister;

    private LoginRequest mLoginRequest;
    private RegisterRequest mRegisterRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState); //DO THIS?
        final View v = inflater.inflate(R.layout.fragment_login, container, false); //PROBLEMS WITH IT BEING FINAL? CHANGE THE RADIO BUTTON IMPLEMENTAITON TO JUST CHECK THE ID

        mLoginRequest = new LoginRequest();
        mRegisterRequest = new RegisterRequest();

        mServerHost = (EditText) v.findViewById(R.id.server_host);
        mServerHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginRequest.setServerHost(s.toString());
                mRegisterRequest.setServerHost(s.toString());
                mSignIn.setEnabled(mLoginRequest.infoComplete()); //enable the buttons if necessary
                mRegister.setEnabled(mRegisterRequest.infoComplete());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        mServerPort = (EditText) v.findViewById(R.id.server_port);
        mServerPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginRequest.setServerPort(s.toString());
                mRegisterRequest.setServerPort(s.toString());
                mSignIn.setEnabled(mLoginRequest.infoComplete()); //enable the buttons if necessary
                mRegister.setEnabled(mRegisterRequest.infoComplete());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUserName = (EditText) v.findViewById(R.id.user_name);
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginRequest.setUserName(s.toString());
                mRegisterRequest.setUserName(s.toString());
                mSignIn.setEnabled(mLoginRequest.infoComplete()); //enable the buttons if necessary
                mRegister.setEnabled(mRegisterRequest.infoComplete());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword = (EditText) v.findViewById(R.id.password);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLoginRequest.setPassword(s.toString());
                mRegisterRequest.setPassword(s.toString());
                mSignIn.setEnabled(mLoginRequest.infoComplete()); //enable the buttons if necessary
                mRegister.setEnabled(mRegisterRequest.infoComplete());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFirstName = (EditText) v.findViewById(R.id.first_name);
        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setFirstName(s.toString());
                mRegister.setEnabled(mRegisterRequest.infoComplete()); //enable the register button if necessary
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLastName = (EditText) v.findViewById(R.id.last_name);
        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setLastName(s.toString());
                mRegister.setEnabled(mRegisterRequest.infoComplete()); //enable the register button if necessary
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmail = (EditText) v.findViewById(R.id.email);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setEmail(s.toString());
                mRegister.setEnabled(mRegisterRequest.infoComplete()); //enable the register button if necessary
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGender = (RadioGroup) v.findViewById(R.id.gender);
        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) v.findViewById(checkedId);
                mRegisterRequest.setGender(rb.getText().toString());
                mRegister.setEnabled(mRegisterRequest.infoComplete()); //enable the register button if necessary
            }
        });

        mSignIn = (Button) v.findViewById(R.id.sign_in_button);
        mSignIn.setEnabled(false);

        mRegister = (Button) v.findViewById(R.id.register_button);
        mRegister.setEnabled(false);

        return v;
    }
}
