package Utilities;

public class RandomData {
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    public static String generateRandomMobileNumber() {
            // Assuming Indian mobile numbers start with 7, 8, or 9 and are 10 digits long
            StringBuilder mobileNumber = new StringBuilder();
            mobileNumber.append((int)(Math.random() * 3) + 7); // Start with 7, 8, or 9
            for (int i = 0; i < 9; i++) {
                mobileNumber.append((int)(Math.random() * 10)); // Add remaining 9 digits
            }
            return mobileNumber.toString();
        }


}
