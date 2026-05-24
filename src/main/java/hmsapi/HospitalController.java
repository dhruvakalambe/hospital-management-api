package hmsapi;

import java.io.File;

import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
public class HospitalController {

    Connection connect(){

        try{

            return DriverManager.getConnection(

                    "jdbc:sqlite:hms.db");
        }

        catch(Exception e){

            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/addPatient")

    public String addPatient(

            @RequestParam String id,

            @RequestParam String name,

            @RequestParam int age,

            @RequestParam String disease){

        try{

            Connection con =
                    connect();

            PreparedStatement ps =
                    con.prepareStatement(

                            "INSERT INTO patients VALUES(?,?,?,?)");

            ps.setString(1,id);
            ps.setString(2,name);
            ps.setInt(3,age);
            ps.setString(4,disease);

            ps.executeUpdate();

            return "Patient Added Successfully";
        }

        catch(Exception e){

            e.printStackTrace();
        }

        return "Error";
    }

    @GetMapping("/patients")

    public String patients(){

        StringBuilder data =
                new StringBuilder();

        try{

            Connection con =
                    connect();

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT * FROM patients");

            while(rs.next()){

                data.append(

                                rs.getString("id"))

                        .append(" ")

                        .append(

                                rs.getString("name"))

                        .append("\n");
            }
        }

        catch(Exception e){

            e.printStackTrace();
        }

        return data.toString();
    }
}
