package by.bstu.faa.christmas_tree.DB.firebase_db;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

import by.bstu.faa.christmas_tree.model.UserInfo;

public class FirebaseManager {

    private static FirebaseManager instance;
    private final DatabaseReference dbRef;
    private final FirebaseUser currentUser;

    private FirebaseManager() {

        dbRef = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void callOnUserInfoById(String userInfoId, Consumer<UserInfo> userInfoConsumer) {

        dbRef.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserInfo userInfo = snapshot.getValue(UserInfo.class);
                        userInfoConsumer.accept(userInfo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
    }

    public void addToDb(UserInfo userInfo) {
        DatabaseReference addUserInfo = dbRef.child(currentUser.getUid()).push();
        userInfo.setId(addUserInfo.getKey());
        addUserInfo.setValue(userInfo);
    }

    public void update(UserInfo userInfo, DatabaseReference.CompletionListener completionListener) {

        DatabaseReference userInfoReference = dbRef.child(currentUser.getUid());
        userInfoReference.setValue(userInfo, completionListener);
    }

    public void delete(String userInfoId, DatabaseReference.CompletionListener completionListener) {

        dbRef.child(currentUser.getUid()).removeValue(completionListener);
    }

    public static FirebaseManager getInstance() {

        if (instance == null) { instance = new FirebaseManager(); }
        return instance;
    }
}
