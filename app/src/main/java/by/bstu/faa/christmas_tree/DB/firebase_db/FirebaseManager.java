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
    private final DatabaseReference databaseReference;
    private final FirebaseUser currentUser;

    private FirebaseManager() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void callOnUserInfoById(String userInfoId, Consumer<UserInfo> userInfoConsumer) {

        databaseReference.child(currentUser.getUid()).child(userInfoId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void appendToList(UserInfo userInfo) {

        DatabaseReference add = databaseReference.child(currentUser.getUid()).push();
        userInfo.setId(add.getKey());
        add.setValue(userInfo);
    }

    public void update(UserInfo userInfo, DatabaseReference.CompletionListener completionListener) {

        DatabaseReference userInfoReference =
                databaseReference.child(currentUser.getUid())
                        .child(userInfo.getId());
        userInfoReference.setValue(userInfo, completionListener);
    }

    public void delete(String userInfoId, DatabaseReference.CompletionListener completionListener) {

        databaseReference.child(currentUser.getUid())
                .child(userInfoId)
                .removeValue(completionListener);
    }

    public static FirebaseManager getInstance() {

        if (instance == null) { instance = new FirebaseManager(); }
        return instance;
    }
}
