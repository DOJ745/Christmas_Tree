package by.bstu.faa.christmas_tree.model.query;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TableQuestionContainer implements Serializable {
    private int questionId;
    private int themeId;
    private String questionText;
}
