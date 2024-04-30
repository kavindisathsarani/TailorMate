package lk.ijse.tailorshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class MaterialDetail {
    private String garmentId;
    private String materialId;
    private double qty;
}
