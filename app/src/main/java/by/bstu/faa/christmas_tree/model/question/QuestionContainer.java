package by.bstu.faa.christmas_tree.model.question;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionContainer implements Serializable {
    private String questionTheme;
    private String questionText;
    Map<String, Integer>variants = new HashMap<>();

    @Override
    public String toString() {
        return "Q theme - " + this.questionTheme + "\n" +
                "Q text - " + this.questionText + "\n" +
                "Q variants - " + this.variants.size();
    }
}
