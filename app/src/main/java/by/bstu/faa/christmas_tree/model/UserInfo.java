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
        this.treeLevel = 0;
        this.score = 0;
    }

    public void addLevel() { this.treeLevel += 1; }
    public void reduceLevel() {
        if(this.treeLevel - 1 < 0) { this.treeLevel = 0; }
        else this.treeLevel -= 1;
    }

    public void addPoints() { this.score += 10; }
    public void reducePoints() {
        if(this.score - 5 < 0) { this.score = 0; }
        else this.score -= 5;
    }
}
