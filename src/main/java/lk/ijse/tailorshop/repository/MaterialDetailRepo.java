package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.MaterialDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MaterialDetailRepo {
    public static boolean save(List<MaterialDetail> mdList) throws SQLException {
        for (MaterialDetail md : mdList) {
            if(!save(md)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(MaterialDetail md) throws SQLException {
        String sql = "INSERT INTO materialDetail VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, md.getGarmentId());
        pstm.setString(2, md.getMaterialId());
        pstm.setDouble(3, md.getQty());

        return pstm.executeUpdate() > 0;
    }


}
