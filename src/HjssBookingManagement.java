import com.hjss.frontend.Menu;
public class HjssBookingManagement {
    public static void main(String[] args) {
        Menu mainMenu = new Menu();
        mainMenu.addMenuItem(1,"Add New Learner");
        mainMenu.addMenuItem(2,"View Learner");
        mainMenu.addMenuItem(0,"Exit");

        mainMenu.run();


//        Running initial test
//        Learner p1 = new Learner("Asif", "Manan", Util.Gender.Male, LocalDate.of(1985, 4, 30), 0, "+447769728661");
//        System.out.println("Learner ID: " + p1.getLearnerId().toUpperCase());
//        System.out.println("First Name: " + p1.getFirstName());
//        System.out.println("Last Name: " + p1.getLastName());
//        System.out.println("Gender: " + p1.getGender());
//        System.out.println("Age: " + Person.calculateAge(p1.getDateOfBirth()));
//        System.out.println("Grade: " + p1.getGrade());
//        System.out.println("Emergency Contact Number: " + p1.getEmergencyContactNumber());
//        System.out.println("********");
//        for(int i = 0; i < 6; i++){
//            p1.gradeLevelUp();
//            System.out.println("Grade: " + p1.getGrade());
//        }
//        p1.gradeLevelUp();
//        System.out.println("Grade: " + p1.getGrade());

//        LocalDate date = Util.convertToDate("2023-02-29");
//        if (date != null){
//            System.out.println(date);
//        }

    }
}
