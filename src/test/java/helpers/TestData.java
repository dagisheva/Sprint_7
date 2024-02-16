package helpers;

import com.github.javafaker.Faker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestData {
    private static final Faker faker = new Faker();
    private static final String creatingError = "Этот логин уже используется. Попробуйте другой.";
    private static final String notEnoughDataError = "Недостаточно данных для создания учетной записи";
    private static final String notEnoughLoginDataError = "Недостаточно данных для входа";
    private static final String accountNotFound = "Учетная запись не найдена";
    private static final String emptyString = "";
    private static final int[] numberDaysToChoose = {1, 2, 3, 4, 5, 6, 7};
    private static final List[] colors = {List.of(Colors.BLACK), List.of(Colors.GREY),
            List.of(""), List.of(Colors.BLACK, Colors.GREY)};

    public static String generateLogin() {
        return faker.name().username();
    }

    public static String generatePassword() {
        return faker.funnyName().name();
    }

    public static String generateFirstname() {
        return faker.twinPeaks().character();
    }

    public static String returnCreatingError() {
        return creatingError;
    }

    public static String returnNotEnoughDataError() {
        return notEnoughDataError;
    }

    public static String returnNotEnoughLoginDataError() {
        return notEnoughLoginDataError;
    }

    public static String returnAccountNotFoundError() {
        return accountNotFound;
    }

    public static String returnEmptyString() {
        return emptyString;
    }

    public static int chooseDaysNumber() {
        Random random = new Random();
        int index = random.nextInt(numberDaysToChoose.length);
        return numberDaysToChoose[index];
    }

    public static String generateDate() {
        Date fakerDate = faker.date().future(1, TimeUnit.DAYS);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(fakerDate);
    }

    public static List chooseColor() {
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return colors[index];
    }
}
