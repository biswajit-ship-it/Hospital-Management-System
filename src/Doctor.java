import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner scanner;
    public Doctor(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addDoctor(){
        System.out.println("Enter doctor name:");
        String name=scanner.next();
        System.out.println("Enter doctor age:");
        String specialization =scanner.next();
        try{
            String sql="insert into doctors(name,specialization )values(?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,specialization);
            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows>0)
                System.out.println("Doctor added successfully");
            else
                System.out.println("Failed to add Doctor");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void viewDoctor(){
        String query="select * from doctors";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("doctors:");
            System.out.println("+----+------+--------------+");
            System.out.println("|id  |name  |specialization ");
            System.out.println("+----+------+-----+--------+");
            while ((resultSet.next())){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String gender=resultSet.getString("specialization");
                System.out.println("|%-4s|%-4s|%-10s|");
                System.out.println("+----+------+-----+--------+");
            }

            System.out.println();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkDoctor(int id)  {
        String query="select * from patients where id =?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
