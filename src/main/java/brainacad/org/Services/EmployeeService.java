package brainacad.org.Services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EmployeeService
{

    public boolean isValidHireDate(String hireDate)
    {
        try {
            // Перетворення рядка у LocalDate
            LocalDate date = LocalDate.parse(hireDate);

            // Перевірка, чи дата не пізніше поточної
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            // Якщо формат дати невірний
            System.err.println("Невірний формат дати. Використовуйте формат yyyy-MM-dd.");
            return false;
        }
    }

    public LocalDate SetDateFromString(String hireDate)
    {
        try {
            // Перетворення рядка у LocalDate
            return LocalDate.parse(hireDate);
        } catch (DateTimeParseException e) {
            // Якщо формат дати неправильний, виводимо помилку
            System.err.println("Невірний формат дати. Використовуйте формат yyyy-MM-dd.");
            return null;  // Повертаємо null, якщо дата не була правильно перетворена
        }
    }
}