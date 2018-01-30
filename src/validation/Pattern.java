package validation;

public class Pattern {
    public static String stringPattern = "^[A-Za-zĹĽĹşÄ‡Ĺ„ĂłĹ‚Ä™Ä…Ĺ›Ĺ»ĹąÄ†Ä„ĹšÄ?Ĺ?Ă“Ĺ?\\s]*$";
    public static String streetPattern = "^[A-Za-zĹĽĹşÄ‡Ĺ„ĂłĹ‚Ä™Ä…Ĺ›Ĺ»ĹąÄ†Ä„ĹšÄ?Ĺ?Ă“Ĺ?0-9\\s]*$";
    public static String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static String postalCodePattern = "^[0-9]{2}-[0-9]{3}$";
    public static String phoneNumberPattern = "^[1-9]{1}[0-9]{2}-[0-9]{3}-[0-9]{3}$";
    public static String phoneNumberHomePattern = "^[1-9]{2}-[1-9]{1}[0-9]{2}-[0-9]{2}-[0-9]{2}$";
    public static String registrationNumberPattern = "^[A-Z\\s0-9]{7,8}$";
    public static String VINPattern = "^[A-Z\\s0-9]{10,17}$";
}
