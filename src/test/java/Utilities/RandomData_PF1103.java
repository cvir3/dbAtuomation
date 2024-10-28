package Utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RandomData_PF1103 {

    private static final Random random = new Random();

    public static int generateRandomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    public static int generateRandomOfficeNum() {
        return generateRandomInt(8888, 9999);
    }


    public static String generateRandomOfficeDept() {
        return generateRandomString(5);
    }


    public static int generateRandomEffectiveLvl() {
        return generateRandomInt(1, 10);
    }


    public static int generateRandomIndCtlNum() {
        return generateRandomInt(8888, 9999);
    }


    public static int generateRandomCompanionCtlNum() {
        return generateRandomInt(8888, 9999);
    }


    public static String generateRandomEmployeeID() {
        return generateRandomString(20);
    }


    public static String generateRandomFirstName() {
        return generateRandomString(20);
    }


    public static String generateRandomLastName() {
        return generateRandomString(20);
    }

    public static String generateCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
