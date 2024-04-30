package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {
    public static boolean save(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(OrderDetail od) throws SQLException {
        String sql = "INSERT INTO orderDetail VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getGarmentId());
        pstm.setInt(3, od.getQty());

        return pstm.executeUpdate() > 0;
    }
}
