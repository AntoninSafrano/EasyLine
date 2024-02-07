import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class VoyageurDAO extends DAO<Voyageur> {
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyline", "root", "root");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public ArrayList<Voyageur> selectVoyageursSansBagage() {
        ArrayList<Voyageur> voyageursSansBagage = new ArrayList<>();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM voyageur WHERE bagage_id IS NULL";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Voyageur voyageur = new Voyageur();
                voyageur.setName(resultSet.getString("name"));
                voyageur.setAge(resultSet.getInt("age"));
                voyageur.setAddress(new AdressePostaleDAO().select(resultSet.getInt("adresse_id")));
                voyageur.setId(resultSet.getLong("id"));
                voyageursSansBagage.add(voyageur);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voyageursSansBagage;
    }
    

    public Voyageur select(long id) {
        Voyageur voyageur = new Voyageur();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM voyageur WHERE id=" + id);
            if (resultSet.next()) {
                voyageur.setName(resultSet.getString("name"));
                voyageur.setAge(resultSet.getInt("age"));
                voyageur.setAddress(new AdressePostaleDAO().select(resultSet.getInt("adresse_id")));
                voyageur.setBagage(new BagageDAO().select(resultSet.getInt("bagage_id")));
                voyageur.setId(resultSet.getLong("id"));
                return voyageur;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Voyageur> selectAll() {
        ArrayList<Voyageur> voyageurs = new ArrayList<Voyageur>();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM voyageur");
            while (resultSet.next()) {
                Voyageur voyageur = new Voyageur();
                voyageur.setName(resultSet.getString("name"));
                voyageur.setAge(resultSet.getInt("age"));
                voyageur.setAddress(new AdressePostaleDAO().select(resultSet.getInt("adresse_id")));
                voyageur.setBagage(new BagageDAO().select(resultSet.getInt("bagage_id")));
                voyageur.setId(resultSet.getLong("id"));
                voyageurs.add(voyageur);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return voyageurs;
    }

    public boolean insert(Voyageur voyageur) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO voyageur (name, age, category, adresse_id, bagage_id) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, voyageur.getName());
            preparedStatement.setInt(2, voyageur.getAge());
            preparedStatement.setString(3, voyageur.getCategory());
            preparedStatement.setLong(4, voyageur.getAddress().getId());
            preparedStatement.setLong(5, voyageur.getBagage().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public int insertAdresse(AdressePostale adressePostale) {
        int insertedAddresseId = 0;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO adresse_postale (street, city, postal_code) VALUES (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, adressePostale.getStreet());
            preparedStatement.setString(2, adressePostale.getCity());
            preparedStatement.setInt(3, adressePostale.getPostalCode());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedAddresseId = rs.getInt(1);
            }
            preparedStatement.close();

            return insertedAddresseId;
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    public boolean insertVoyageurWithAdresse(Voyageur voyageur, AdressePostale adressePostale) {
        try {
            Connection connection = getConnection();

            int adresseId = insertAdresse(adressePostale);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO voyageur (name, age, category, adresse_id) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, voyageur.getName());
            preparedStatement.setInt(2, voyageur.getAge());
            preparedStatement.setString(3, voyageur.getCategory());
            preparedStatement.setLong(4, adresseId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean update(Voyageur voyageur) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE voyageur SET name=?, age=?, category=?, adresse_id=?, bagage_id=? WHERE id=?");
            preparedStatement.setString(1, voyageur.getName());
            preparedStatement.setInt(2, voyageur.getAge());
            preparedStatement.setString(3, voyageur.getCategory());
            preparedStatement.setLong(4, voyageur.getAddress().getId());
            preparedStatement.setLong(5, voyageur.getBagage().getId());
            preparedStatement.setLong(6, voyageur.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean delete(long id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM voyageur WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public static void main(String[] args) {
    testSelectById();
}

public static void testSelectById() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Veuillez entrer l'ID du voyageur pour le test : ");

    try {
        long id = scanner.nextLong();

        VoyageurDAO voyageurDAO = new VoyageurDAO();
        Voyageur voyageur = voyageurDAO.select(id);

        if (voyageur != null) {
            System.out.println("Informations du voyageur avec l'ID " + id + " :");
            voyageur.displayInfo();
        } else {
            System.out.println("Aucun voyageur trouvé avec l'ID " + id);
        }
    } catch (Exception e) {
        System.out.println("Erreur lors de la saisie de l'ID du voyageur : " + e.getMessage());
    } finally {
        scanner.close();
    }
}
}


   