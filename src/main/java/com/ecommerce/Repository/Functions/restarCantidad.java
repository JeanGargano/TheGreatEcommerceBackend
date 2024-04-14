package com.ecommerce.Repository.Functions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class restarCantidad {

    public void restarCantidad(){
        int cantidad = 0;
        if (cantidad > 0) {

            actualizarCantidadEnBD(); // Actualizar la cantidad en la base de datos

            if (cantidad == 0) {
                System.out.println("¡El producto está agotado!");
            }
        } else {
            System.out.println("¡El producto está agotado!");
        }

    }

    private void actualizarCantidadEnBD() {

        int cantidad = 0; // Aca se debe cambiar luego por el JSON request que llegue pa tomar la variable correcta.
        String url = "jdbc:mysql://localhost:3306/mydb";
        String usuario = "root";
        String contraseña = "root";

        String sql = "UPDATE cantidad SET cantidad = ? WHERE idArticuloTalla = ?";

        try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cantidad);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar la cantidad en la base de datos: " + e.getMessage());
        }

    }
}
