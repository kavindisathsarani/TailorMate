package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> customersList = new ArrayList<>();
        while (resultSet.next()) {
            String customerId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String gender = resultSet.getString(3);
            String address = resultSet.getString(4);
            int contactNumber = resultSet.getInt(5);
            String email = resultSet.getString(6);

            Customer customer = new Customer(customerId, name, gender, address,contactNumber,email);
            customersList.add(customer);
        }
        return customersList;
    }

    public static boolean save(Customer customer) throws SQLException {
        // In here you can now save your customer
        String sql = "INSERT INTO customer VALUES(?, ?, ?, ? ,? , ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, customer.getCustomerId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getGender());
        pstm.setObject(4, customer.getAddress());
        pstm.setObject(5, customer.getContactNumber());
        pstm.setObject(6, customer.getEmail());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name = ?, gender = ?, address = ?, contactNumber = ?, email = ? WHERE customerId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getGender());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getContactNumber());
        pstm.setObject(5, customer.getEmail());
        pstm.setObject(6, customer.getCustomerId());

        return pstm.executeUpdate() > 0;
    }


    public static Customer searchById(String customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, customerId);
        ResultSet resultSet = pstm.executeQuery();

        Customer customer = null;

        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String gender = resultSet.getString(3);
            String address = resultSet.getString(4);
            int contactNumber = resultSet.getInt(5);
            String email = resultSet.getString(6);

             customer = new Customer(cus_id, name, gender, address,contactNumber,email);
        }
        return customer;
    }

    public static boolean delete(String customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, customerId);

        return pstm.executeUpdate() > 0;
    }
}
