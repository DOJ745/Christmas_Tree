package by.bstu.faa.christmas_tree.model.query;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TableThemesContainer implements Serializable {
    private int themeId;
    private String themeName;
}
