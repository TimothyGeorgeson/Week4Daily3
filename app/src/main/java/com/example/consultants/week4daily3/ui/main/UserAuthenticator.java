package com.example.consultants.week4daily3.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.consultants.week4daily3.R;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class UserAuthenticator extends AppCompatActivity {
    public static final String TAG = UserAuthenticator.class.getSimpleName() + "_TAG";
    FirebaseAuth auth;
    Callback callback;
    Activity activity;
    CompleteListener completeListener;

    GoogleSignInClient googleSignInClient;
    public final static int RC_SIGN_IN = 101;

    public UserAuthenticator(Activity activity, CompleteListener completeListener) {
        auth = FirebaseAuth.getInstance();
        callback = (Callback) activity;
        this.activity = activity;
        this.completeListener = completeListener;
    }

    public void emailSignIn(String userEmail, String password) {

        completeListener.setType(0);
        auth.signInWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(activity, completeListener);

    }

    public void emailSignUp(String userEmail, String password) {

        completeListener.setType(1);
        auth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(activity, completeListener);
    }

    public void googleSignIn(String userEmail, String password) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(activity, gso);

        Log.d(TAG, "googleSignIn: ");

        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            //updateUI(null)
                            Toast.makeText(activity, "Log In Success", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                            Toast.makeText(activity, "Log In Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void signOut() {
        auth.signOut();
    }

    interface Callback {

        void onUserValidated(FirebaseUser user);

        void onUserInvalidated();
    }
}
