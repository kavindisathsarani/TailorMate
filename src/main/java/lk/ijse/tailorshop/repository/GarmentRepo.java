package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.Garment;
import lk.ijse.tailorshop.model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GarmentRepo {
    public static String currentId() throws SQLException {
        String sql = "SELECT garmentId FROM garment ORDER BY garmentId desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Garment garment) throws SQLException {
        String sql = "INSERT INTO garment VALUES(?, ?, ?,?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, garment.getGarmentId());
        pstm.setString(2, garment.getName());
        pstm.setString(3, garment.getDescription());
        pstm.setString(4, garment.getCategory());
        pstm.setString(5, garment.getSize());
        pstm.setDouble(6,garment.getQtyOnHand());
        pstm.setDouble(7,garment.getMaterialCost());
        pstm.setDouble(8,garment.getTowage());
        pstm.setDouble(9,garment.getTotalPrice());

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT garmentId FROM garment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> codeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static Garment searchByCode(String garmentId) throws SQLException {
        String sql = "SELECT * FROM garment WHERE garmentId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, garmentId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Garment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7),
                    resultSet.getDouble(8),
                    resultSet.getDouble(9)

            );
        }
        return null;
    }


    public static boolean updateQty(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderDetail od) throws SQLException {
        String sql = "UPDATE garment SET qtyOnHand = qtyOnHand - ? WHERE garmentId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setInt(1, od.getQty());
        pstm.setString(2, od.getGarmentId());

        return pstm.executeUpdate() > 0;
    }


}
