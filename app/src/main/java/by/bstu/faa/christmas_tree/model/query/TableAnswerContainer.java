package by.bstu.faa.christmas_tree.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TableAnswerContainer {
    private int answerId;
    private int questionId;
    private String answerText;
    private int trueness;
}
