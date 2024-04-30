package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.Material;
import lk.ijse.tailorshop.model.MaterialDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialRepo {
    public static boolean save(Material material) throws SQLException {
        String sql = "INSERT INTO material VALUES(?, ?, ?, ? ,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, material.getMaterialId());
        pstm.setObject(2, material.getDescription());
        pstm.setObject(3, material.getQty());
        pstm.setObject(4, material.getUnitPrice());
        pstm.setObject(5, material.getCustomerId());

        return pstm.executeUpdate() > 0;
    }

    public static Material searchById(String materialId) throws SQLException {
        String sql = "SELECT * FROM material WHERE materialId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, materialId);
        ResultSet resultSet = pstm.executeQuery();

        Material material = null;

        if (resultSet.next()) {
            String material_id = resultSet.getString(1);
            String description = resultSet.getString(2);
            double qty = resultSet.getDouble(3);
            double unitPrice = resultSet.getDouble(4);
            String customerId = resultSet.getString(5);

            material = new Material(material_id, description, qty, unitPrice,customerId);
        }
        return material;
    }

    public static boolean update(Material material) throws SQLException {
        String sql = "UPDATE material SET description = ?, qty = ?, unitPrice = ?, customerId = ? WHERE materialId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, material.getDescription());
        pstm.setObject(2, material.getQty());
        pstm.setObject(3, material.getUnitPrice());
        pstm.setObject(4, material.getCustomerId());
        pstm.setObject(5, material.getMaterialId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String materialId) throws SQLException {
        String sql = "DELETE FROM material WHERE materialId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, materialId);

        return pstm.executeUpdate() > 0;
    }

    public static List<Material> getAll() throws SQLException {
        String sql = "SELECT * FROM material";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Material> materialList = new ArrayList<>();
        while (resultSet.next()) {
            String materialId = resultSet.getString(1);
            String description = resultSet.getString(2);
            double qty = Double.parseDouble(resultSet.getString(3));
            double unitPrice = Double.parseDouble(resultSet.getString(4));
            String customerId = resultSet.getString(5);

            Material material = new Material(materialId, description, qty, unitPrice,customerId);
            materialList.add(material);
        }
        return materialList;
    }


    public static List<String> getIds() throws SQLException {
        String sql = "SELECT materialId FROM material";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> codeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static Material searchByCode(String materialId) throws SQLException {
        String sql = "SELECT * FROM material WHERE materialId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, materialId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Material(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static boolean updateQty(List<MaterialDetail> mdList) throws SQLException {
        for (MaterialDetail md : mdList) {
            if(!updateQty(md)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(MaterialDetail md) throws SQLException {
        String sql = "UPDATE material SET qty = qty - ? WHERE materialId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setDouble(1, md.getQty());
        pstm.setString(2, md.getMaterialId());

        return pstm.executeUpdate() > 0;
    }
}
