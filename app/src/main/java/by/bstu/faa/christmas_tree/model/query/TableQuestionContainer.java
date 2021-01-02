package by.bstu.faa.christmas_tree.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TableQuestionContainer {
    private int questionId;
    private int themeId;
    private String questionText;
}
