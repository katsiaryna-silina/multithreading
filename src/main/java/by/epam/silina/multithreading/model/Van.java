package by.epam.silina.multithreading.model;

import by.epam.silina.multithreading.model.state.VanState;
import by.epam.silina.multithreading.model.state.impl.WaitingForEntranceToBaseVanState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Van {
    private VanState state = WaitingForEntranceToBaseVanState.getInstance();
    private Integer numberInLine;
    private boolean withPerishableGoods;

    public Van(Integer numberInLine, boolean hasPerishableGoods) {
        this.numberInLine = numberInLine;
        this.withPerishableGoods = hasPerishableGoods;
    }
}
