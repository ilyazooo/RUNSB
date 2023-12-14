package com.example.runsb;

import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

public class DataBaseController {


    public Connection connectToDatabase() {
        String dbUrl = "jdbc:mysql://localhost:3306/rundb"; // Mettez à jour avec votre propre URL de base de données.
        String dbUser = "root";
        String dbPassword = "root";

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    // Méthode pour fermer la connexion à la base de données
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Moderateur> getModerateurs() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Moderateur> moderateurs = new ArrayList<>();

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM moderateur";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Moderateur moderateur = new Moderateur();
                    moderateur.setId(resultSet.getInt("ID_Moderateur"));
                    moderateur.setNom(resultSet.getString("nom"));
                    moderateur.setPrenom(resultSet.getString("prenom"));
                    moderateur.setEmail(resultSet.getString("email"));
                    moderateur.setMotDePasse(resultSet.getString("motDePasse"));
                    moderateurs.add(moderateur);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return moderateurs;
    }

    public Produit getProductByKeyWord(String keyWord) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM produit WHERE motCles '%keyWord%' ";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setId(resultSet.getInt("ID_Produit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setMarque(resultSet.getString("marque"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getString("prix"));
                    produit.setMotsCles(resultSet.getString("motCles"));
                    produit.setUrlPicture(resultSet.getString("urlPicture"));
                    return produit;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return null; // Si aucun produit correspondant à la recherche
    }

    public boolean checkAdminCredentials(String email, String motDePasse) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM administrateur WHERE email = ? AND motDePasse = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, motDePasse);
                resultSet = preparedStatement.executeQuery();

                // Si la requête renvoie des résultats, alors les informations d'identification sont correctes
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        // Si la requête ne renvoie pas de résultats ou s'il y a une erreur, les informations d'identification sont incorrectes
        return false;
    }

    public boolean checkModeratorCredentials(String email, String motDePasse) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM moderateur WHERE email = ? AND motDePasse = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, motDePasse);
                resultSet = preparedStatement.executeQuery();

                // Si la requête renvoie des résultats, alors les informations d'identification sont correctes
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        // Si la requête ne renvoie pas de résultats ou s'il y a une erreur, les informations d'identification sont incorrectes
        return false;
    }

    public boolean insertProduit(String nom, double prix, String marque, String description, String urlPicture, String motsCles) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "INSERT INTO produit(nom, marque, description, prix, urlPicture, motsCles) VALUES (?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, marque);
                preparedStatement.setString(3, description);
                preparedStatement.setDouble(4, prix);
                preparedStatement.setString(5, urlPicture);
                preparedStatement.setString(6, motsCles);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return false;
    }

    public boolean deleteProduit(int productId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "DELETE FROM produit WHERE ID_Produit=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, productId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return false;
    }

    public boolean updateProduit(Produit produit) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "UPDATE produit SET nom=?, marque=?, description=?, prix=?, urlPicture=?, motsCles=? WHERE ID_Produit=?";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, produit.getNom());
                preparedStatement.setString(2, produit.getMarque());
                preparedStatement.setString(3, produit.getDescription());
                preparedStatement.setString(4, produit.getPrix());
                preparedStatement.setString(5, produit.getUrlPicture());
                preparedStatement.setString(6, produit.getMotsCles());
                preparedStatement.setInt(7, produit.getId());

                // Exécuter la mise à jour
                preparedStatement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }
        return false;
    }

    public Moderateur getModeratorById(int moderatorId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM moderateur WHERE ID_Moderateur=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, moderatorId);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Moderateur moderateur = new Moderateur();
                    moderateur.setId(resultSet.getInt("ID_Moderateur"));
                    moderateur.setAjouterProduit(resultSet.getBoolean("ajouterProduit"));
                    moderateur.setSupprimerProduit(resultSet.getBoolean("supprimerProduit"));
                    moderateur.setModifierProduit(resultSet.getBoolean("modifierProduit"));
                    return moderateur;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return null; // Si aucun modérateur correspondant à l'ID n'est trouvé.
    }

    public Moderateur getModeratorByEmail(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM moderateur WHERE email=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Moderateur moderateur = new Moderateur();
                    moderateur.setId(resultSet.getInt("ID_Moderateur"));
                    moderateur.setAjouterProduit(resultSet.getBoolean("ajouterProduit"));
                    moderateur.setSupprimerProduit(resultSet.getBoolean("supprimerProduit"));
                    moderateur.setModifierProduit(resultSet.getBoolean("modifierProduit"));
                    return moderateur;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return null; // Si aucun modérateur correspondant à l'email n'est trouvé.
    }

    public boolean insertModerator(String nom, String prenom, String email, String motDePasse,
                                   boolean ajouterProduit, boolean supprimerProduit, boolean modifierProduit) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String motDePasseHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
                String query = "INSERT INTO moderateur(nom, prenom, email, motDePasse, ajouterProduit, supprimerProduit, modifierProduit) VALUES (?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, motDePasseHash);
                preparedStatement.setBoolean(5, ajouterProduit);
                preparedStatement.setBoolean(6, supprimerProduit);
                preparedStatement.setBoolean(7, modifierProduit);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return false;
    }


    public boolean deleteModerator(int moderatorId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "DELETE FROM moderateur WHERE ID_Moderateur=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, moderatorId);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return false;
    }
    public boolean updateModerator(Moderateur moderateur) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "UPDATE moderateur SET ajouterProduit=?, supprimerProduit=?, modifierProduit=? WHERE ID_Moderateur=?";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setBoolean(1, moderateur.isAjouterProduit());
                preparedStatement.setBoolean(2, moderateur.isSupprimerProduit());
                preparedStatement.setBoolean(3, moderateur.isModifierProduit());
                preparedStatement.setInt(4, moderateur.getId());

                // Exécuter la mise à jour
                preparedStatement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }
        return false;
    }


    public List<Produit> getCatalogue() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Produit> catalogue = new ArrayList<>();

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM produit";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setId(resultSet.getInt("ID_Produit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setMarque(resultSet.getString("marque"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getString("prix"));
                    produit.setUrlPicture(resultSet.getString("urlPicture"));
                    catalogue.add(produit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return catalogue;
    }
    public Produit getProductById(int productId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM produit WHERE ID_Produit=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, productId);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setId(resultSet.getInt("ID_Produit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setMarque(resultSet.getString("marque"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getString("prix"));
                    produit.setUrlPicture(resultSet.getString("urlPicture"));
                    produit.setMotsCles(resultSet.getString("motsCles"));
                    return produit;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return null; // Si aucun produit correspondant à l'ID n'est trouvé.
    }
    public List<Produit> getProductBySearch(String search) {

        List<Produit> produits = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM produit WHERE nom LIKE ? OR motsCles LIKE ? OR marque LIKE ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "%" + search + "%");
                preparedStatement.setString(2, "%" + search + "%");
                preparedStatement.setString(3, "%" + search + "%");
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setId(resultSet.getInt("ID_Produit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setMarque(resultSet.getString("marque"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setMotsCles(resultSet.getString("motsCles"));
                    produit.setPrix(resultSet.getString("prix"));
                    produit.setUrlPicture(resultSet.getString("urlPicture"));

                    produits.add(produit);
                }

                return produits;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return null; // Si aucun produit ne correspond à la recherche
    }
    public List<String> getAllDistinctMarques() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> marques = new ArrayList<>();

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT DISTINCT marque FROM produit";
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    marques.add(resultSet.getString("marque"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            closeConnection(connection);
        }

        return marques;
    }

    public List<Produit> getFilteredProducts(String marque, String couleur, String genre) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Produit> produits = new ArrayList<>();

        try {
            connection = connectToDatabase();
            if (connection != null) {
                // Construire la requête SQL en fonction des filtres sélectionnés
                StringBuilder queryBuilder = new StringBuilder("SELECT * FROM produit WHERE 1=1");

                if (marque != null && !marque.isEmpty()) {
                    queryBuilder.append(" AND marque = ?");
                }

                if (couleur != null && !couleur.isEmpty()) {
                    queryBuilder.append(" AND motsCles LIKE ?");
                }

                if (genre != null && !genre.isEmpty()) {
                    queryBuilder.append(" AND (motsCles LIKE ? OR nom LIKE ?)");
                }

                preparedStatement = connection.prepareStatement(queryBuilder.toString());

                // Paramètres de la requête en fonction des filtres sélectionnés
                int parameterIndex = 1;
                if (marque != null && !marque.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, marque);
                }

                if (couleur != null && !couleur.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, "%"+couleur+"%");
                }

                if (genre != null && !genre.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, "%"+genre+"%");
                    preparedStatement.setString(parameterIndex++, "%"+genre+"%");
                }

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setId(resultSet.getInt("ID_Produit"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setMarque(resultSet.getString("marque"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getString("prix"));
                    produit.setUrlPicture(resultSet.getString("urlPicture"));
                    produits.add(produit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return produits;
    }

    public boolean checkLogin(String email, String motDePasse) throws SQLException {
        String sql = "SELECT motDePasse FROM client WHERE email = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String motDePasseHash = resultSet.getString("motDePasse");

                    return BCrypt.checkpw(motDePasse, motDePasseHash);
                }
            }
        }
        return false;
    }

    public int getUserIdFromDatabase(String email) throws SQLException {
        String sql = "SELECT ID_Client FROM client WHERE email = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("ID_Client");
                }
            }
        }
        return -1; // Retourne -1 si l'utilisateur n'a pas été trouvé dans la base de données
    }

    public boolean register(String prenom, String nom, String email, String motDePasse) {
        // Assurez-vous d'adapter les détails de votre base de données
        String selectSQL = "SELECT COUNT(*) FROM client WHERE email = ?";
        String insertSQL = "INSERT INTO client (nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectToDatabase();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {


            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) == 0) {
                String motDePasseHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
                insertStatement.setString(1, nom);
                insertStatement.setString(2, prenom);
                insertStatement.setString(3, email);
                insertStatement.setString(4, motDePasseHash);


                int rowsAffected = insertStatement.executeUpdate();


                return rowsAffected == 1;
            } else {

                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public List<VarianteProduit> getVariantesByProductId(int productId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<VarianteProduit> variantes = new ArrayList<>();

        try {
            connection = connectToDatabase();
            if (connection != null) {
                String query = "SELECT * FROM variantes_produit WHERE id_produit = ? ORDER BY pointure ASC;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, productId);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    VarianteProduit variante = new VarianteProduit(
                            resultSet.getInt("ID_Variante"),
                            resultSet.getInt("ID_produit"),
                            resultSet.getInt("pointure"),
                            resultSet.getInt("stock")
                    );
                    variantes.add(variante);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(connection);
        }

        return variantes;
    }

    public String getImageUrlFromProductId(int productId) {
        String imageUrl = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = connectToDatabase();
            String query = "SELECT urlPicture FROM produit WHERE ID_Produit = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, productId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                imageUrl = rs.getString("urlPicture");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return imageUrl;
    }

    public int getProductIdFromVarianteId(int idVariante)  {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int idProduit = 0;

        try {
            conn =  connectToDatabase();// Obtenez la connexion à la base de données ici

            // Écrivez votre requête SQL pour récupérer l'ID du produit en fonction de l'ID de la variante
            String sql = "SELECT ID_Produit FROM variantes_produit WHERE ID_Variante = ?";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idVariante);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idProduit = resultSet.getInt("ID_Produit");
                return idProduit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez les exceptions ici
        }

        // Retournez -1 ou une valeur par défaut si l'ID du produit n'est pas trouvé
        return -1;
    }

    public int getPointureFromVarianteId(int idVariante)  {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int pointure = 0;

        try {
            conn =  connectToDatabase();


            String sql = "SELECT pointure FROM variantes_produit WHERE ID_Variante = ?";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idVariante);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pointure = resultSet.getInt("pointure");
                return pointure;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez les exceptions ici
        }

        // Retournez -1 ou une valeur par défaut si l'ID du produit n'est pas trouvé
        return -1;
    }

    public Client getClientById(int clientId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            connection = connectToDatabase();

            String query = "SELECT * FROM client WHERE ID_Client = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, clientId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                client = new Client();
                client.setId(resultSet.getInt("ID_Client"));
                client.setNom(resultSet.getString("nom"));
                client.setPrenom(resultSet.getString("prenom"));
                client.setEmail(resultSet.getString("email"));
                client.setSoldeFidelite(resultSet.getInt("soldeFidelite"));

            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des erreurs
        } finally {

        }

        return client;
    }


    public int creerCommande(int idClient) {
        String query = "INSERT INTO commande (ID_Client, date_commande, statut) VALUES (?, ?, ?)";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        String statut = "en attente";

        try (Connection connection = connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, idClient);
            preparedStatement.setString(2, formattedDate);
            preparedStatement.setString(3, statut);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Récupère la première colonne des clés générées, normalement l'ID_Commande
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // En cas d'échec
    }

    public void creerDetailCommande(int idCommande, int idProduit, int idVariante, int quantite) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectToDatabase();

            String sql = "INSERT INTO details_commande (ID_Commande, ID_Produit, ID_Variante, quantite) VALUES (?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, idCommande);
            statement.setInt(2, idProduit);
            statement.setInt(3, idVariante);
            statement.setInt(4, quantite);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // À personnaliser pour la gestion des exceptions
        } finally {
            // Fermer la connexion et les ressources
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Commande> getCommandesByClientId(int idClient) {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande WHERE ID_Client = ?";

        try (Connection connection = connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idClient);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Récupérer les informations de chaque commande depuis le résultat de la requête
                    int idCommande = resultSet.getInt("ID_Commande");
                    String dateCommande = resultSet.getString("date_commande");
                    String statut = resultSet.getString("statut");

                    // Créer une instance de la classe Commande pour chaque résultat
                    Commande commande = new Commande(idCommande, idClient, dateCommande, statut);

                    // Ajouter la commande à la liste
                    commandes.add(commande);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }



    public List<DetailsCommande> getDetailsCommandeByCommandeId(int idCommande) {
        List<DetailsCommande> detailsCommandes = new ArrayList<>();
        String query = "SELECT * FROM details_commande WHERE ID_Commande = ?";

        try (Connection connection = connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idCommande);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idDetail = resultSet.getInt("ID_Detail");
                    int idProduit = resultSet.getInt("ID_Produit");
                    int idVariante = resultSet.getInt("ID_Variante");
                    int quantite = resultSet.getInt("quantite");

                    DetailsCommande detailsCommande = new DetailsCommande(idDetail, idCommande, idProduit, idVariante, quantite);
                    detailsCommandes.add(detailsCommande);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detailsCommandes;
    }


    public void addFidelitePoints(int clientId, int fidelitePoints) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = connectToDatabase();
            String selectQuery = "SELECT soldeFidelite FROM client WHERE ID_Client = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, clientId);
            resultSet = preparedStatement.executeQuery();

            int currentFidelitePoints = 0;

            if (resultSet.next()) {
                currentFidelitePoints = resultSet.getInt("soldeFidelite");
            }


            int newFidelitePoints = currentFidelitePoints + fidelitePoints;


            String updateQuery = "UPDATE client SET soldeFidelite = ? WHERE ID_Client = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, newFidelitePoints);
            preparedStatement.setInt(2, clientId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception (par exemple, journalisation, affichage d'un message d'erreur, etc.)
        } finally {
            // Étape 5 : Fermer les ressources (ResultSet, PreparedStatement, Connection)
            closeResources(resultSet, preparedStatement, connection);
        }
    }


    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public boolean getVerifsolde(int userId) {
        Random rand = new Random();
        int number = rand.nextInt(4);
        return number != 0;
    }

}
