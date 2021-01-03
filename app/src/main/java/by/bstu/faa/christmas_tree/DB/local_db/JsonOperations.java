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
        ArrayList<TableThemesContainer> themesContainers = new ArrayList<>();
        try{
            themesContainers = mapper.readValue(file, new TypeReference<ArrayList<TableThemesContainer>>() { });
        }
        catch (IOException e){
            Log.e("Log_e", "Failed to read from file\n" + e.getMessage());
            return themesContainers;
        }
        return themesContainers;
    }

    public static ArrayList<TableQuestionContainer> readQuestionsJson(File file){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TableQuestionContainer> questionContainers = new ArrayList<>();
        try{
            questionContainers = mapper.readValue(file, new TypeReference<ArrayList<TableQuestionContainer>>() { });
        }
        catch (IOException e){
            Log.e("Log_e", "Failed to read from file\n" + e.getMessage());
            return questionContainers;
        }
        return questionContainers;
    }

    public static ArrayList<TableAnswerContainer> readAnswersJson(File file){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TableAnswerContainer> answerContainers = new ArrayList<>();
        try{
            answerContainers = mapper.readValue(file, new TypeReference<ArrayList<TableAnswerContainer>>() { });
        }
        catch (IOException e){
            Log.e("Log_e", "Failed to read from file\n" + e.getMessage());
            return answerContainers;
        }
        return answerContainers;
    }

    public static ArrayList<UserInfo> readUsersJson(File file){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<UserInfo> users = new ArrayList<>();
        try{
            users = mapper.readValue(file, new TypeReference<ArrayList<UserInfo>>() { });
        }
        catch (IOException e){
            Log.e("Log_e", "Failed to read from file\n" + e.getMessage());
            return users;
        }
        return users;
    }
}
