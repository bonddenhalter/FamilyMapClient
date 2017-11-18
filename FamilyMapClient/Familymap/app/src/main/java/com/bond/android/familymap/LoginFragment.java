package com.bond.android.familymap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bond.android.familymap.model.FamilyInfo;
import com.bond.android.familymap.model.UserInfo;
import com.bond.android.familymap.requests.LoginRequest;
import com.bond.android.familymap.requests.RegisterRequest;
import com.bond.android.familymap.results.EventsResult;
import com.bond.android.familymap.results.LoginResult;
import com.bond.android.familymap.results.PeopleResult;

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
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInRequestTask reqTask = new SignInRequestTask();
                reqTask.execute(mLoginRequest);
            }
        });

        mRegister = (Button) v.findViewById(R.id.register_button);
        mRegister.setEnabled(false);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Registering. Please wait...",
                        Toast.LENGTH_LONG).show();
                RegisterRequestTask reqTask = new RegisterRequestTask();
                reqTask.execute(mRegisterRequest);
            }
        } );


        return v;
    }

    private static final String LTAG = "LogInReq";
    private static final String RTAG = "RegReq";
    private class SignInRequestTask extends AsyncTask<LoginRequest,Void,LoginResult> {
        @Override
        protected LoginResult doInBackground(LoginRequest... params) {
            Proxy proxy = Proxy.getInstance();
            LoginResult loginResult = proxy.login(mLoginRequest);
   //         return proxy.login(mLoginRequest);

            UserInfo userInfo = UserInfo.getInstance();
            if (loginResult.getAuthToken() != null)
                userInfo.setAuthToken(loginResult.getAuthToken());

            if (loginResult.getMessage() != null) //there was a failure
            {
                return loginResult;
            }
            else
            {
                getFamilyInfo(); //load family info
            }

            FamilyInfo familyInfo = FamilyInfo.getInstance();
            if (familyInfo.isEventsLoadSuccessful() && familyInfo.isPeopleLoadSuccessful())
            {
                return loginResult;
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(LoginResult loginResult) {
            //super.onPostExecute(aVoid);
            if (loginResult == null)
            {
                Log.e("Login Error", "failed to load family");
                Toast.makeText(getActivity(), "Login failed to load family",
                        Toast.LENGTH_SHORT).show();
                return;

            }
            else {

                //stash results
                UserInfo userInfo = UserInfo.getInstance();
                if (loginResult.getPersonID() != null)
                    userInfo.setPersonID(loginResult.getPersonID());
                if (loginResult.getUserName() != null)
                    userInfo.setUserName(loginResult.getUserName());

                if (loginResult.getMessage() != null) //there was a failure
                {
                    Log.e("Login Request failed", loginResult.getMessage());
                    Toast.makeText(getActivity(), "Log in failed",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.i("Login request", "successful");
                    String userID = loginResult.getPersonID();
                    FamilyInfo familyInfo = FamilyInfo.getInstance();
                    userInfo.setFirstName(familyInfo.getFirstName(userID));
                    userInfo.setLastName(familyInfo.getLastName(userID));
                    
                    Toast.makeText(getActivity(), (userInfo.getFirstName() + ", " + userInfo.getLastName()),
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private class RegisterRequestTask extends AsyncTask<RegisterRequest, Void, LoginResult>{  //todo: implement like login
        @Override
        protected LoginResult doInBackground(RegisterRequest... params) { //loginResult is identical to RegisterResult
            Proxy proxy = Proxy.getInstance();
            LoginResult registerResult = proxy.register(mRegisterRequest);

            UserInfo userInfo = UserInfo.getInstance();
            if (registerResult.getAuthToken() != null)
                userInfo.setAuthToken(registerResult.getAuthToken());

            if (registerResult.getMessage() != null) //there was a failure
            {
                return registerResult;
            }
            else
            {
                getFamilyInfo(); //load family info
            }

            FamilyInfo familyInfo = FamilyInfo.getInstance();
            if (familyInfo.isEventsLoadSuccessful() && familyInfo.isPeopleLoadSuccessful())
            {
                return registerResult;
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(LoginResult registerResult) {
            //super.onPostExecute(aVoid);
            if (registerResult == null) //family failed
            {
                Log.e("Register Request", "Failed to load family");
                Toast.makeText(getActivity(), "Register failed to load family",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            else //family successful or not performed
            {

                //stash results
                UserInfo userInfo = UserInfo.getInstance();
                if (registerResult.getPersonID() != null)
                    userInfo.setPersonID(registerResult.getPersonID());
                if (registerResult.getUserName() != null)
                    userInfo.setUserName(registerResult.getUserName());

                if (registerResult.getMessage() != null) //there was a register failure
                {
                    Log.e("Register Request failed", registerResult.getMessage());
                    Toast.makeText(getActivity(), "Register failed",
                            Toast.LENGTH_SHORT).show();
                }
                else //total success
                {
                    Log.i("Register request", "successful");
                    Toast.makeText(getActivity(), (userInfo.getFirstName() + ", " + userInfo.getLastName()),
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    void getFamilyInfo()
    {
//        EventsRequestTask eventsRequestTask = new EventsRequestTask();
//        eventsRequestTask.execute();
//        PeopleRequestTask peopleRequestTask = new PeopleRequestTask();
//        peopleRequestTask.execute();
        Proxy proxy = Proxy.getInstance();
        EventsResult eventsResult = proxy.events();
        FamilyInfo familyInfo = FamilyInfo.getInstance();
        if (eventsResult == null)
        {
            Log.e("events resp", "null data and msg");
            familyInfo.setEventsLoadSuccessful(false);
        }
        else if (eventsResult.getData() != null)
        {
            familyInfo.setEvents(eventsResult.getData());
            Log.i("Events request", "successful");
            familyInfo.setEventsLoadSuccessful(true);
        }
        else if (eventsResult.getMessage() != null) //there was a failure in the server
        {
            Log.e("events error", eventsResult.getMessage());
            familyInfo.setEventsLoadSuccessful(false);
        }
        else
        {
            Log.e("events resp", "null data and msg");
            familyInfo.setEventsLoadSuccessful(false);
        }

        PeopleResult peopleResult = proxy.people();
        if (peopleResult == null)
        {
            Log.e("people resp", "null data and msg");
            familyInfo.setPeopleLoadSuccessful(false);
        }
        else if (peopleResult.getData() != null)
        {
            familyInfo.setPeople(peopleResult.getData());
//                Toast.makeText(getActivity(), "People succeeded!", //TODO:TEMP!
//                        Toast.LENGTH_SHORT).show();
            Log.i("People request", "successful");
            familyInfo.setPeopleLoadSuccessful(true);
        }
        else if (peopleResult.getMessage() != null) //there was a failure in the server
        {
            Log.e("people error", peopleResult.getMessage());
            familyInfo.setPeopleLoadSuccessful(false);
        }
        else
        {
            Log.e("people resp", "null data and msg");
            familyInfo.setPeopleLoadSuccessful(false);
        }


    }

    private class EventsRequestTask extends AsyncTask<Void, Void, EventsResult> {
        @Override
        protected EventsResult doInBackground(Void... params) {
            Proxy proxy = Proxy.getInstance();
            EventsResult eventsResult = proxy.events();
            return eventsResult;
        }

        @Override
        protected void onPostExecute(EventsResult eventsResult) {
            //super.onPostExecute(eventsResult);
            //stash results
            FamilyInfo familyInfo = FamilyInfo.getInstance();
            if (eventsResult == null)
            {
                Log.e("events resp", "null data and msg");
                familyInfo.setEventsLoadSuccessful(false);
            }
            else if (eventsResult.getData() != null)
            {
                familyInfo.setEvents(eventsResult.getData());
                Log.i("Events request", "successful");
                familyInfo.setEventsLoadSuccessful(true);
            }
            else if (eventsResult.getMessage() != null) //there was a failure in the server
            {
                Log.e("events error", eventsResult.getMessage());
                familyInfo.setEventsLoadSuccessful(false);
            }
            else
            {
                Log.e("events resp", "null data and msg");
                familyInfo.setEventsLoadSuccessful(false);
            }
        }
    }

    private class PeopleRequestTask extends AsyncTask<Void, Void, PeopleResult> {

        @Override
        protected PeopleResult doInBackground(Void... params) {
            Proxy proxy = Proxy.getInstance();
            return proxy.people();
        }

        @Override
        protected void onPostExecute(PeopleResult peopleResult) {
            //super.onPostExecute(peopleResult);
            //stash data
            FamilyInfo familyInfo = FamilyInfo.getInstance();
            if (peopleResult == null)
            {
                Log.e("people resp", "null data and msg");
                familyInfo.setPeopleLoadSuccessful(false);
            }
            else if (peopleResult.getData() != null)
            {
                familyInfo.setPeople(peopleResult.getData());
//                Toast.makeText(getActivity(), "People succeeded!", //TODO:TEMP!
//                        Toast.LENGTH_SHORT).show();
                Log.i("People request", "successful");
                familyInfo.setPeopleLoadSuccessful(true);
            }
            else if (peopleResult.getMessage() != null) //there was a failure in the server
            {
                Log.e("people error", peopleResult.getMessage());
                familyInfo.setPeopleLoadSuccessful(false);
            }
            else
            {
                Log.e("people resp", "null data and msg");
                familyInfo.setPeopleLoadSuccessful(false);
            }

        }
    }

}
