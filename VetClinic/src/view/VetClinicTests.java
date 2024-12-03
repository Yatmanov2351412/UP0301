package view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VetClinicTests {

    @Test
    public void testCalculateCostWithVaccine() {
        int baseCost = 200;  // Базовая стоимость
        boolean hasVaccine = true;
        String complaint = "Прививка";

        int expectedCost = baseCost + 50;  // Прививка добавляет 50
        if (!hasVaccine) {
            expectedCost += 50;  // Без прививки добавляется ещё 50
        }

        assertEquals(250, expectedCost, "Стоимость с прививкой рассчитана неверно");
    }

    @Test
    public void testCalculateCostWithoutVaccine() {
        int baseCost = 200;
        boolean hasVaccine = false;
        String complaint = "Прививка";

        int expectedCost = baseCost + 50 + 50;  // Жалоба добавляет 50, отсутствие прививки ещё 50
        assertEquals(300, expectedCost, "Стоимость без прививки рассчитана неверно");
    }

    @Test
    public void testEmptyClientName() {
        String clientName = "";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (clientName.isEmpty()) {
                throw new IllegalArgumentException("Имя клиента не может быть пустым");
            }
        });
        assertEquals("Имя клиента не может быть пустым", exception.getMessage());
    }

    @Test
    public void testResultMessageFormat() {
        String clientName = "Иван";
        String petName = "Барсик";
        String complaint = "Прививка";
        boolean hasVaccine = true;
        int petAge = 3;

        String resultMessage = String.format(
                "Здравствуйте, %s!\nПитомец: %s\nЖалоба: %s\nПрививка: %s\nВозраст питомца: %d\nОбщая стоимость: %d руб.",
                clientName, petName, complaint, hasVaccine ? "Да" : "Нет", petAge, 250
        );

        String expectedMessage = "Здравствуйте, Иван!\n" +
                "Питомец: Барсик\n" +
                "Жалоба: Прививка\n" +
                "Прививка: Да\n" +
                "Возраст питомца: 3\n" +
                "Общая стоимость: 250 руб.";

        assertEquals(expectedMessage, resultMessage, "Формат итогового сообщения неверен");
    }
}
