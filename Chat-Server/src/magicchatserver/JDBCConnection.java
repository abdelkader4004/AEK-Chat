package magicchatserver;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author el-magic
 */
import java.sql.*;
import javax.swing.*;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2010</p>
 * <p>Société :nekaa & benzohra </p>
 * @author non attribuable
 * @version 1.0
 */
public class JDBCConnection {

    Connection connection = null;
    final static String BD = "chatserveur";

    public JDBCConnection() {
    }

    /**
     * methodes connect permet  de connecter a la base de donnee
     */
    public void connect() {
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "imposible de charger le pilote JDBD", "Attention", JOptionPane.INFORMATION_MESSAGE);
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+Main.dbHost+"/" + BD, "root", "");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "imposible de connecter au SGBD", "Attention", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * cette methode permet identifier
     * elle admet en entree le deux String un pour mot de pass et le deusieme  login
     * en sortie elles return une boolean true si client est identifier false sinon
     */
    public boolean authenticate(String e, String f) throws SQLException {
        Statement statement = null; // objet d'émission des requêtes
        ResultSet resultSet = null; // table résultat d'une requêtes
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from client");
        boolean sortie = true;
        boolean ok = false;
        try {
            while (resultSet.next() & sortie == true) { // tant qu'il y a une ligne à exploiter
                if ((resultSet.getString("pseodo").equals(e)) & (resultSet.getString("motpass").equals(f))) {
                    System.out.println("ok");
                    sortie = false;
                    ok = true;
                }

            }// ligne suivante

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ok;
    }

    public void disconnectDB() {    // fermeture de la base
        try {
            connection.close();
            System.out.println("Base " + " fermée");
        } catch (Exception e) {
        }
    }

    public boolean Insert(String log, String mot) {
        Statement S = null; // objet d'émission des requêtes
        ResultSet RS = null; // table résultat d'une requêtes
        int exist = -1;
        boolean bool = false;
        /**
         * chercher s'il exit un logoin
         */
        exist = testPseudo(log);
        System.out.println("cette client exist deja = " + exist);
        if (exist != -1) {
            return false;
        } else {
            char v = ',';
            char x = '(';
            char y = ')';
            char t = 't';
            String login = String.valueOf("'") + String.valueOf(log) + String.valueOf("'");
            String motpass = String.valueOf("'") + String.valueOf(mot) + String.valueOf("'");
            String req = "insert into client " + String.valueOf(x) + String.valueOf("`") +
                    String.valueOf("pseodo") + String.valueOf("`") + String.valueOf(v) +
                    String.valueOf("`") + String.valueOf("motpass") + String.valueOf("`") +
                    String.valueOf(y) + String.valueOf(" values ");

            String requett = req + String.valueOf(x) + String.valueOf(login) +
                    String.valueOf(v) + String.valueOf(motpass) + String.valueOf(y);
            System.out.print("requtte d'insertion == " + requett);
            try {
                S = connection.createStatement();
                S.execute(requett);
            //RS = S.executeQuery("select * from client");
            } catch (SQLException ex) {
                System.out.print("erreur de requette de creation" + ex);
            }

            bool = true;
        }

        return bool;
    }

    /**
     * methode test un login qui exist déja
     */
    public int testPseudo(String pseodo) {
        int i = -1;
        boolean existe = false;// on suppose que le pseodo n'exist pas
        String rep = "";
        Statement S = null; // objet d'émission des requêtes
        ResultSet RS = null; // table résultat d'une requêtes
        String requette = "select id_client  from client where pseodo= " + "'" + pseodo + "'";
        System.out.println("requette de recherche =" + requette);
        try {
            S = connection.createStatement();
            RS = S.executeQuery(requette);
            RS.next();
            i = Integer.parseInt(RS.getString("id_client"));
        } catch (SQLException ex) {
            System.out.println("erreur get login" + ex);
        }

        return i;
    }

    public String getNbLignes() {
        String nb = "inconnu ";
        Statement S = null; // objet d'émission des requêtes
        ResultSet RS = null; // table résultat d'une requêtes
        try {
            S = connection.createStatement();
            RS = S.executeQuery("select count(*) as nombre from client");
            RS.next();
            nb = RS.getString("nombre");
            RS.close();
        } catch (SQLException ex) {
        }

        return nb;
    }

    public String getUID(String login) {
        String c = String.valueOf("'");
        String id = "0";
        Statement S = null; // objet d'émission des requêtes
        ResultSet RS = null; // table résultat d'une requêtes
        String l = String.valueOf(login);

        String reqette = "select id_client from client where ";
        String es = reqette + "pseodo=" + c + l + c;
        //System.out.println("requette de id=" + es);
        try {
            S = connection.createStatement();
            RS = S.executeQuery(es);
            RS.next();
            id = RS.getString("id_client");
            RS.close();
            return id;
        } catch (SQLException ex) {
            System.out.println("Exeption sql dans getUID");
            return null;
        }


    }

    public String getlist(long id) {
        String a = "";
        Statement S = null; // objet d'émission des requêtes
        ResultSet RS = null; // table résultat d'une requêtes
        String requtte = "SELECT client.id_client,client.pseodo FROM list_contact, client ";
        String condition = "WHERE (client.id_client = list_contact.cl_connecte) AND(list_contact.id_client = " + id + ")";
        //System.out.println("list=" + requtte + condition);
        try {
            S = connection.createStatement();
            RS = S.executeQuery(requtte + condition);
            String id1 = "";
            String log = "";
            while (RS.next()) {
                id1 = RS.getString("id_client");
                log = RS.getString("pseodo");
                a = a + id1 + "," + log + ":";
            }
            RS.close();
        } catch (SQLException ex) {
            System.out.println("reqette errone");
        }

        if (a.length() != 0) {
            a = a.substring(0, a.length() - 1);
        }
        return a;
    }

    public void addContact(long id1, long id2) {
        String requete;
        Statement S = null; // objet d'émission des requêtes        
        try {
            S = connection.createStatement();
            requete = "INSERT INTO chatserveur.list_contact (id_client ,cl_connecte) VALUES ( '" + id1 + "', '" + id2 + "');";
            System.out.println("requete contact= " + requete);
            S.executeUpdate(requete);

            requete = "INSERT INTO `chatserveur`.`list_contact` (`id_client` ,`cl_connecte`) VALUES ( '" + id2 + "', '" + id1 + "')";
            S.executeUpdate(requete);
        } catch (SQLException ex) {
            System.out.println("erreur requete contact" + ex);
        }
    }

    public String chercher(String str) {
        String res0, res = "";
        Statement S = null; // objet d'émission des requêtes
        ResultSet RS = null; // table résultat d'une requêtes
        try {
            S = connection.createStatement();
            RS = S.executeQuery("select * from client");

        } catch (SQLException ex) {
        }
        try {
            while (RS.next()) {
                res0 = RS.getString("pseodo");
                if (res0.startsWith(str)) {
                    res = res + "," + res0;
                }


            }
            RS.close();
        } catch (SQLException ex1) {
        }


        System.out.println("le resultas de recherche=" + res);
        return res;

    }

    void contactDelete(long toDeleteUID, long uid) {
        String requete;
        Statement S = null; // objet d'émission des requêtes
        try {
            S = connection.createStatement();
            requete = "DELETE FROM `list_contact` WHERE `list_contact`.`id_client` =" + uid + " AND `list_contact`.`cl_connecte` =" + toDeleteUID;
            System.out.println("requete contact= " + requete);
            S.executeUpdate(requete);
            S = connection.createStatement();
            requete = "DELETE FROM `list_contact` WHERE `list_contact`.`id_client` =" + toDeleteUID + " AND `list_contact`.`cl_connecte` =" + uid;
            System.out.println("requete contact= " + requete);
            S.executeUpdate(requete);


        } catch (SQLException ex) {
            System.out.println("erreur requete del contact" + ex);
        }

    }
}
