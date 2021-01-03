package by.bstu.faa.christmas_tree.DB.local_db;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import by.bstu.faa.christmas_tree.model.UserInfo;
import by.bstu.faa.christmas_tree.model.query.TableAnswerContainer;
import by.bstu.faa.christmas_tree.model.query.TableQuestionContainer;
import by.bstu.faa.christmas_tree.model.query.TableThemesContainer;

public class JsonOperations {

    public static void saveThemesJson(
            ArrayList<TableThemesContainer> container,
            String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), container);
        Log.d("Log_file", "File has been saved to: " + new File(fileName).getPath());
    }

    public static void saveAnswersJson(
            ArrayList<TableAnswerContainer> container,
            String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), container);
        Log.d("Log_file", "File has been saved to: " + new File(fileName).getPath());
    }

    public static void saveQuestionsJson(
            ArrayList<TableQuestionContainer> container,
            String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), container);
        Log.d("Log_file", "File has been saved to: " + new File(fileName).getPath());
    }

    public static void saveUsersJson(
            ArrayList<UserInfo> container,
            String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), container);
        Log.d("Log_file", "File has been saved to: " + new File(fileName).getPath());
    }

    public static ArrayList<TableThemesContainer> readThemesJson(File file){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TableThemesContainer> allContacts = new ArrayList<>();
        try{
            allContacts = mapper.readValue(file, new TypeReference<ArrayList<TableThemesContainer>>() { });
        }
        catch (IOException e){
            Log.e("Log_e", "Failed to read from file\n" + e.getMessage());
            return allContacts;
        }
        return allContacts;
    }
}
