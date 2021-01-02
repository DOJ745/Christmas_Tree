package by.bstu.faa.christmas_tree.model.query;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TableAnswerContainer implements Serializable {
    private int answerId;
    private int questionId;
    private String answerText;
    private int trueness;
}
