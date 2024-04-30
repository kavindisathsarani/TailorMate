package lk.ijse.tailorshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class AddGarment {
 private Garment garment;
    private List<MaterialDetail> mdList;

}
