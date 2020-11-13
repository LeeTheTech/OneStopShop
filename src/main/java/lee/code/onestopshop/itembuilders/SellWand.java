package lee.code.onestopshop.itembuilders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class SellWand {

    private final String name;
    private final List<String> lore;

}
