package by.bstu.faa.christmas_tree.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QueryContainer {

    private String themeName;
    private int q_id;
    private String q_text;
    private int a_id;
    private String a_text;
    private int trueness;

    @Override
    public String toString(){
        return "Theme name - " + this.themeName + "\n" +
                "Question ID - " + this.q_id + "\n" +
                "Question text - " + this.q_text + "\n" +
                "Answer ID - " + this.a_id + "\n" +
                "Answer text - " + this.a_text + "\n" +
                "Trueness - " + this.trueness + "\n";
    }
}
