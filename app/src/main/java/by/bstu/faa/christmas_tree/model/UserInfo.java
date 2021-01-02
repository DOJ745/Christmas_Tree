package by.bstu.faa.christmas_tree.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo implements Serializable {

    private String id;
    private String name;
    private int treeLevel;
    private int score;

    public UserInfo() {
        this.name = "player";
        this.treeLevel = 0;
        this.score = 0;
    }

    public void correctAnswer() {
        this.treeLevel += 1;
        this.score += 10;
    }

    public void wrongAnswer() {

        if(this.treeLevel - 1 < 0) { this.treeLevel = 0; }
        else this.treeLevel -= 1;

        if(this.score - 5 < 0) { this.score = 0; }
        else this.score -= 5;
    }

    @Override
    public String toString(){
        return "ID - " + this.id + "\n" +
                "Name - " + this.name + "\n" +
                "Tree level - " + this.treeLevel + "\n" +
                "Score - " + this.score;
    }
}
