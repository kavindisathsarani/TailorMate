package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.Customer;
import lk.ijse.tailorshop.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {


    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES(?, ?, ?, ? ,? , ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, payment.getPaymentId());
        pstm.setObject(2, payment.getTotalCost());
        pstm.setObject(3, payment.getAmount());
        pstm.setObject(4, payment.getBalance());
        pstm.setObject(5, payment.getStatus());
        pstm.setObject(6, payment.getDate());
        pstm.setObject(7, payment.getOrderId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET TotalCost = ?, amount = ?, balance = ?, status = ?, date = ? , orderId = ?  WHERE paymentId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, payment.getTotalCost());
        pstm.setObject(2, payment.getAmount());
        pstm.setObject(3, payment.getBalance());
        pstm.setObject(4, payment.getStatus());
        pstm.setObject(5, payment.getDate());
        pstm.setObject(6, payment.getOrderId());
        pstm.setObject(7, payment.getPaymentId());

        return pstm.executeUpdate() > 0;

    }

    public static Payment searchById(String orderId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE orderId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, orderId);
        ResultSet resultSet = pstm.executeQuery();

        Payment payment = null;

        if (resultSet.next()) {
            String paymentId = resultSet.getString(1);
            double TotalCost = resultSet.getDouble(2);
            double amount = resultSet.getDouble(3);
            double balance = resultSet.getDouble(4);
            String status = resultSet.getString(5);
            Date date = resultSet.getDate(6);
            String order_Id = resultSet.getString(7);

            payment = new Payment(paymentId, TotalCost, amount, balance,status,date,order_Id);
        }
        return payment;
    }

    public static boolean delete(String orderId) throws SQLException {
        String sql = "DELETE FROM payment WHERE orderId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, orderId);

        return pstm.executeUpdate() > 0;
    }

    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String paymentId = resultSet.getString(1);
            double TotalCost = resultSet.getDouble(2);
            double amount = resultSet.getDouble(3);
            double balance = resultSet.getDouble(4);
            String status = resultSet.getString(5);
            Date date = resultSet.getDate(6);
            String orderId = resultSet.getString(7);

            Payment payment = new Payment(paymentId, TotalCost, amount, balance,status,date,orderId);
            paymentList.add(payment);
        }
        return paymentList;
    }
}
