package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.Measurement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeasurementRepo {
    public static boolean save(Measurement measurement) throws SQLException {
        String sql = "INSERT INTO measurement VALUES(?, ?, ?, ? ,? , ?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, measurement.getMeasurementId());
        pstm.setObject(2, measurement.getNeckSize());
        pstm.setObject(3, measurement.getArmhole());
        pstm.setObject(4, measurement.getSleeveLength());
        pstm.setObject(5, measurement.getWrist());
        pstm.setObject(6, measurement.getChest());
        pstm.setObject(7, measurement.getTorsoLength());
        pstm.setObject(8, measurement.getWaist());
        pstm.setObject(9, measurement.getHip());
        pstm.setObject(10, measurement.getCrotchLength());
        pstm.setObject(11, measurement.getShoulderLength());
        pstm.setObject(12, measurement.getThighCircumference());
        pstm.setObject(13, measurement.getWaistToHem());
        pstm.setObject(14, measurement.getEmployeeId());
        pstm.setObject(15, measurement.getCustomerId());

        return pstm.executeUpdate() > 0;
    }

    public static Measurement searchById(String customerId) throws SQLException {
        String sql = "SELECT * FROM measurement WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, customerId);
        ResultSet resultSet = pstm.executeQuery();

        Measurement measurement = null;

        if (resultSet.next()) {
            String measurementId = resultSet.getString(1);
            double neckSize= resultSet.getDouble(2);
            double armhole= resultSet.getDouble(3);
            double sleeveLength= resultSet.getDouble(4);
            double wrist= resultSet.getDouble(5);
            double chest= resultSet.getDouble(6);
            double torsoLength= resultSet.getDouble(7);
            double waist= resultSet.getDouble(8);
            double hip= resultSet.getDouble(9);
            double crotchLength= resultSet.getDouble(10);
            double shoulderLength= resultSet.getDouble(11);
            double thighCircumference= resultSet.getDouble(12);
            double waistToHem= resultSet.getDouble(13);
            String employeeId = resultSet.getString(14);
            String cus_id = resultSet.getString(15);

            measurement = new Measurement(measurementId,neckSize,armhole,sleeveLength,wrist,chest,torsoLength,waist,hip,crotchLength,shoulderLength,thighCircumference,waistToHem,employeeId,cus_id);
        }
        return measurement;
    }

    public static boolean update(Measurement measurement) throws SQLException {
        String sql = "UPDATE measurement SET measurementId = ?, neckSize = ?, armhole = ?, sleeveLength = ?, wrist = ?,chest=?,torsoLength=?,waist=?,hip=?,crotchLength=?,shoulderLength=?,thighCircumference=?,waistToHem=?,employeeId=? WHERE customerId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, measurement.getMeasurementId());
        pstm.setObject(2, measurement.getNeckSize());
        pstm.setObject(3, measurement.getArmhole());
        pstm.setObject(4, measurement.getSleeveLength());
        pstm.setObject(5, measurement.getWrist());
        pstm.setObject(6, measurement.getChest());
        pstm.setObject(7, measurement.getTorsoLength());
        pstm.setObject(8, measurement.getWaist());
        pstm.setObject(9, measurement.getHip());
        pstm.setObject(10, measurement.getCrotchLength());
        pstm.setObject(11, measurement.getShoulderLength());
        pstm.setObject(12, measurement.getThighCircumference());
        pstm.setObject(13, measurement.getWaistToHem());
        pstm.setObject(14, measurement.getEmployeeId());
        pstm.setObject(15, measurement.getCustomerId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String customerId) throws SQLException {
        String sql = "DELETE FROM measurement WHERE customerId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, customerId);

        return pstm.executeUpdate() > 0;
    }

    public static List<Measurement> getAll() throws SQLException {
        String sql = "SELECT * FROM measurement";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Measurement> measurementList = new ArrayList<>();
        while (resultSet.next()) {
            String measurementId = resultSet.getString(1);
            double neckSize= resultSet.getDouble(2);
            double armhole= resultSet.getDouble(3);
            double sleeveLength= resultSet.getDouble(4);
            double wrist= resultSet.getDouble(5);
            double chest= resultSet.getDouble(6);
            double torsoLength= resultSet.getDouble(7);
            double waist= resultSet.getDouble(8);
            double hip= resultSet.getDouble(9);
            double crotchLength= resultSet.getDouble(10);
            double shoulderLength= resultSet.getDouble(11);
            double thighCircumference= resultSet.getDouble(12);
            double waistToHem= resultSet.getDouble(13);
            String employeeId = resultSet.getString(14);
            String customerId = resultSet.getString(15);


            Measurement measurement = new Measurement(measurementId, neckSize, armhole, sleeveLength,wrist,chest,torsoLength,waist,hip,crotchLength,shoulderLength,thighCircumference,waistToHem,employeeId,customerId);
            measurementList.add(measurement);
        }
        return measurementList;
    }
}
