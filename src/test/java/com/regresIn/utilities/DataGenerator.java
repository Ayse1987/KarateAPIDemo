package com.regresIn.utilities;

import com.github.javafaker.Faker;
import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    public static void main(String[] args) {
        System.out.println(getRandomName());

    }

    static Faker faker = new Faker();
    static Random random = new Random();

    public static String getRandomName() {
        return faker.name().firstName();
    }

    public static String getRandomSurname() {
        return faker.name().lastName();
    }

    public static String getRandomUsername() {
        return faker.name().username();
    }

    public static String getPreferedName() {
        return faker.name().name();
    }

    public static String getRandomDateOfBirth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(faker.date().birthday());
    }
    // this method convert a date to string
    public static String getStringDate(Object date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    public static String getDate(int year, int month, int day, String format) {
        return LocalDate.now().plusYears(year).plusMonths(month).plusDays(day).format(DateTimeFormatter.ofPattern(format));
    }

    public static boolean isItWeekend(String date) {
        LocalDate isWeekendDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DayOfWeek day = DayOfWeek.of(isWeekendDate.get(ChronoField.DAY_OF_WEEK));
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    public static boolean isItBankHoliday(String date) {
        String bankHolidayApi = "https://www.gov.uk/bank-holidays.json";
        List<String> holidays = given()
                .when()
                .get(bankHolidayApi)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getList("england-and-wales.events.date.flatten()");
        return holidays.contains(date);
    }


    public static void getBankHolidays(int yearPeriod) throws IOException {
        String bankHolidayApi = "https://www.gov.uk/bank-holidays.json";
        String fileDestination = "src/test/java/com/finspire/data/onusPaymentManager/scheduledPaymentController/EnglandBankHolidays.csv";

        LocalDate today = LocalDate.now();
        List<String> holidays = given()
                .when()
                .get(bankHolidayApi)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getList("england-and-wales.events.date.flatten()");

        File file = new File(fileDestination);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write("bankHoliday");
        bufferedWriter.newLine();

        for (String holiday : holidays) {
            LocalDate bankHoliday = LocalDate.parse(holiday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if ((bankHoliday.isAfter(today) && bankHoliday.isBefore(today.plusDays(1).plusYears(yearPeriod)))) {
                bufferedWriter.write(holiday);
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
    }


    public static Map<String, Object> createUser() {
        String name = faker.name().firstName();
        String lastName = faker.name().lastName();
        String gender = faker.demographic().sex();
        String email = faker.name().username() + "@gmail.com";
        long phone = Long.parseLong(faker.numerify("#####"));

        Map<String, Object> newUser = new HashMap<>();
        newUser.put("name", name);
        newUser.put("gender", gender);
        newUser.put("email", email);
        newUser.put("status", "Active");

        return newUser;
    }

    //    public static String generateRandomAccountNumber() {
//        int digit = 8;
//        int m = (int) Math.pow(10, digit - 1);
//        int randomAccNumber = m + new Random().nextInt(9 * m);
//        String randomAccountNumber = String.valueOf(randomAccNumber);
//        //out.println("randomAccountNumber = " + randomAccountNumber);
//        return randomAccountNumber;
//    }
//
//    public static String generateRandomSortNumber() {
//        int digit = 6;
//        int m = (int) Math.pow(10, digit - 1);
//        int randomAccNumber = m + new Random().nextInt(9 * m);
//        String randomAccountNumber = String.valueOf(randomAccNumber);
//        //out.println("randomSortNumber = " + randomAccountNumber);
//        return randomAccountNumber;
//    }
    public static String generateRandomAccountNumber() {
//        return "" + random.nextInt(99999999); //OR
        return RandomStringUtils.randomNumeric(8);
    }

    public static String generateRandomSortNumber() {
//        return "" + random.nextInt(999999); //OR
        return RandomStringUtils.randomNumeric(6);
    }

    public static String getFakePassword() {
//        Integer password = random.nextInt(9999);
//        return "P@ss" + password;
        //OR
//        int num = random.nextInt(9999);
//        char c = (char) (random.nextInt(26) + 'a');
//        return "P@s" + num + c;
        //OR
        return RandomStringUtils.randomAlphabetic(2).toUpperCase() + "@" +
                RandomStringUtils.randomNumeric(1) +
                RandomStringUtils.randomAlphabetic(2).toLowerCase() +
                RandomStringUtils.randomNumeric(2);
    }

    public static String getFakeEmailAddress() {
        return faker.name().firstName().toLowerCase() + "." + faker.name().lastName().toLowerCase() + "@yopmail.com";
    }

    public static String extractTokenFromVerificationLink(String verificationLink) {
        String verificationToken = verificationLink.substring(verificationLink.lastIndexOf("eyJhbGciOiJIUzUxMiJ9"), verificationLink.indexOf("\"}"));
        return verificationToken;
    }

    public static String getFakeName() {
        String name = faker.name().fullName().toString();
        return name;
    }


    public static void generateFile(String path, String data) {
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file, false);//true: to add a new line; false: to overwritten on same line
            BufferedWriter bw = new BufferedWriter(fw);//create entries via buffer to reduce possible errors.
            bw.append(data);
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static String readFile(String path) {
        File file = new File(path);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            return br.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static String getCurrencyCode() {
        List<List<Object>> dbCurrency = DBUtils.getQueryResultList("SELECT code FROM currency", "current-account-manager");
        List<String> currencyList = new ArrayList<>();

        for (List<Object> o : dbCurrency) {
            currencyList.add(o.get(0).toString());
        }

        String currency = "GBP";
        while (currencyList.contains(currency)) {
            currency = faker.currency().code();
        }

        return currency;
    }


    public static String encodeFileToBase64(String base, String aFile) {
        File file = new File(aFile);
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return base + Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }

}


