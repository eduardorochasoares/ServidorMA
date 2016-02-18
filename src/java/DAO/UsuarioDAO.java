/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import User.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Principal
 */
public class UsuarioDAO {

    public boolean getUser(User user) {
        Statement s;
        Connection con = ConexaoMySQL.getConexaoMySQL();
        try {
            String query = "Select * from user where login ='" + user.getLogin() + "' and senha ='" + user.getSenha() + "'";
            s = con.createStatement();
            ResultSet rs = s.executeQuery(query);

            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
}
