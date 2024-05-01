package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardRepo {

    public static int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int customerCount = 0;
        if(resultSet.next()) {
            customerCount = resultSet.getInt("customer_count");
        }
        return customerCount;
    }

    public static int getGarmentCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS garment_count FROM garment";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int garmentCount = 0;
        if(resultSet.next()) {
            garmentCount = resultSet.getInt("garment_count");
        }
        return garmentCount;
    }
}
