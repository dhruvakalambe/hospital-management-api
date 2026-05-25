package hmsapi;

import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
public class HospitalController {

    Connection connect() {

        try {

            return DriverManager.getConnection(
                    "jdbc:sqlite:hms.db");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/addPatient")

    public String addPatient(

            @RequestParam String id,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String disease) {

        try {

            Connection con =
                    connect();

            Statement st =
                    con.createStatement();

            st.executeUpdate(

                    "CREATE TABLE IF NOT EXISTS patients("

                            + "id TEXT PRIMARY KEY,"
                            + "name TEXT,"
                            + "age INTEGER,"
                            + "disease TEXT)");

            PreparedStatement ps =
                    con.prepareStatement(

                            "INSERT INTO patients(id,name,age,disease)"
                                    + " VALUES(?,?,?,?)");

            ps.setString(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, disease);

            ps.executeUpdate();

            con.close();

            return "Patient Added Successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return e.getMessage();
        }
    }

    @GetMapping("/patients")

    public String patients() {

        StringBuilder data =
                new StringBuilder();

        try {

            Connection con =
                    connect();

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT * FROM patients");

            while (rs.next()) {

                data.append(
                                rs.getString("id"))
                        .append(" ")

                        .append(
                                rs.getString("name"))

                        .append(" ")

                        .append(
                                rs.getInt("age"))

                        .append(" ")

                        .append(
                                rs.getString("disease"))

                        .append("\n");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return data.toString();
    }
}
