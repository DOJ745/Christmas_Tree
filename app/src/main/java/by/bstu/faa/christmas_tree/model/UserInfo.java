package by.bstu.faa.christmas_tree.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo implements Serializable {

    private String id;
    private String name;
    private int treeLevel;
    private int score;
}
