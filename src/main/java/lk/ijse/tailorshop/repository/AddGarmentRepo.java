package lk.ijse.tailorshop.repository;

import lk.ijse.tailorshop.db.DbConnection;
import lk.ijse.tailorshop.model.AddGarment;

import java.sql.Connection;
import java.sql.SQLException;

public class AddGarmentRepo {
    public static boolean addGarment(AddGarment ad) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = GarmentRepo.save(ad.getGarment());
            if (isOrderSaved) {
                boolean isOrderDetailSaved = MaterialDetailRepo.save(ad.getMdList());
                if (isOrderDetailSaved) {
                    boolean isItemQtyUpdate = MaterialRepo.updateQty(ad.getMdList());
                    if (isItemQtyUpdate) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
