package com.Inventory.DAO;

import com.Inventory.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public static int countBySupplierId(Connection conn) {
        String sql = "SELECT COUNT(supplierID) AS total FROM suppliers";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean insertSupplier(Supplier supplier, Connection conn) {
        String sql = "INSERT INTO suppliers (supplierName, supplierEmail, supplierPhone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getSupplierEmail());
            stmt.setString(3, supplier.getSupplierPhone());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Supplier> listAllSuppliers(Connection conn) {
        List<Supplier> listSupplier = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierID(rs.getInt("supplierID"));
                supplier.setSupplierName(rs.getString("supplierName"));
                supplier.setSupplierEmail(rs.getString("supplierEmail"));
                supplier.setSupplierPhone(rs.getString("supplierPhone"));
                listSupplier.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSupplier;
    }

    public static boolean deleteSupplier(int supplierID, Connection conn) {
        String sql = "DELETE FROM suppliers WHERE supplierID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateSupplier(Supplier supplier, Connection conn) {
        String sql = "UPDATE suppliers SET supplierName = ?, supplierEmail = ?, supplierPhone = ? WHERE supplierID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getSupplierEmail());
            stmt.setString(3, supplier.getSupplierPhone());
            stmt.setInt(4, supplier.getSupplierID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Supplier getSupplier(int supplierID, Connection conn) {
        String sql = "SELECT * FROM suppliers WHERE supplierID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Supplier supplier = new Supplier();
                    supplier.setSupplierID(rs.getInt("supplierID"));
                    supplier.setSupplierName(rs.getString("supplierName"));
                    supplier.setSupplierEmail(rs.getString("supplierEmail"));
                    supplier.setSupplierPhone(rs.getString("supplierPhone"));
                    return supplier;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public static List<Supplier> listAllSupplierNames(Connection conn) {
         List<Supplier> listSupplierNames = new ArrayList<>();
         String sql = "SELECT supplierID, supplierName FROM suppliers";
         try (PreparedStatement stmt = conn.prepareStatement(sql);
              ResultSet rs = stmt.executeQuery()) {
             while (rs.next()) {
                 Supplier supplier = new Supplier();
                 supplier.setSupplierID(rs.getInt("supplierID"));
                 supplier.setSupplierName(rs.getString("supplierName"));
                 listSupplierNames.add(supplier);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return listSupplierNames;
     }

}
