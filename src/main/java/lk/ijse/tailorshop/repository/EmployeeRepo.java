package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ? ,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getAddress());
        pstm.setObject(4, employee.getContactNumber());
        pstm.setObject(5, employee.getPosition());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Employee employee) throws SQLException {

        String sql = "UPDATE employee SET name = ?, address = ?, contactNumber = ?, position = ? WHERE employeeId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employee.getName());
        pstm.setObject(2, employee.getAddress());
        pstm.setObject(3, employee.getContactNumber());
        pstm.setObject(4, employee.getPosition());
        pstm.setObject(5, employee.getEmployeeId());


        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String empoyeeId) throws SQLException {
        String sql = "DELETE FROM employee WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, empoyeeId);

        return pstm.executeUpdate() > 0;
    }


    public static Employee searchById(String employeeId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employeeId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employeeId);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int contactNumber = resultSet.getInt(4);
            String position = resultSet.getString(5);

            employee = new Employee(emp_id, name, address,contactNumber,position);
        }
        return employee;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int contactNumber = resultSet.getInt(4);
            String position = resultSet.getString(5);

            Employee employee = new Employee(employeeId, name, address,contactNumber,position);
            employeeList.add(employee);
        }
        return employeeList;
    }
}
