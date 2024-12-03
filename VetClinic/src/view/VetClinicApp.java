package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VetClinicApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VetClinicApp().createAndShowGUI());
    }

    public void createAndShowGUI() {
        // Создание окна
        JFrame frame = new JFrame("Ветклиника");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Размер главного окна
        frame.setLayout(new GridBagLayout()); // Используем GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Поля ввода
        JTextField clientNameField = new JTextField();
        JTextField petNameField = new JTextField();
        JTextField petAgeField = new JTextField();

        // Выпадающий список видов питомцев
        String[] petTypes = {"Кошка", "Собака", "Попугай"};
        JComboBox<String> petTypeComboBox = new JComboBox<>(petTypes);

        // Выпадающий список жалоб
        String[] complaints = {"Вакцинация", "Осмотр", "Стрижка"};
        JComboBox<String> complaintComboBox = new JComboBox<>(complaints);

        // Чекбокс для прививки
        JCheckBox vaccinationCheckBox = new JCheckBox("Прививка есть");

        // Кнопка для вывода результата
        JButton resultButton = new JButton("Вывести результат");

        // Текстовое поле для вывода результата
        JTextArea resultArea = new JTextArea(5, 50); // Увеличиваем количество столбцов и строк
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Добавление элементов на форму
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Имя клиента:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(clientNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Имя питомца:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(petNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Вид питомца:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(petTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Жалоба:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(complaintComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(new JLabel("Прививка:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(vaccinationCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(new JLabel("Возраст питомца (лет):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        frame.add(petAgeField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        frame.add(resultButton, gbc);

        // Поле вывода результата
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH; // Поле растягивается по ширине и высоте
        frame.add(scrollPane, gbc);

        // Обработчик кнопки "Вывести результат"
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Сбор данных
                String clientName = clientNameField.getText().trim();
                String petName = petNameField.getText().trim();
                String petType = (String) petTypeComboBox.getSelectedItem();
                String complaint = (String) complaintComboBox.getSelectedItem();
                boolean hasVaccination = vaccinationCheckBox.isSelected();
                String petAgeStr = petAgeField.getText().trim();

                // Проверка данных
                if (clientName.isEmpty() || petName.isEmpty() || petAgeStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Все поля должны быть заполнены!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int petAge;
                try {
                    petAge = Integer.parseInt(petAgeStr);
                    if (petAge < 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Возраст питомца должен быть положительным числом!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Расчет стоимости
                double[] prices = {500, 1000, 700}; // Цены на услуги
                double basePrice = prices[complaintComboBox.getSelectedIndex()];
                double totalPrice = basePrice;
                if (!hasVaccination) {
                    totalPrice += 200; // Доплата за отсутствие прививки
                }

                // Текущее время визита
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                String visitTime = now.format(formatter);

                // Рекомендации
                String[] recommendations = {
                        "Рекомендуем повторить вакцинацию через год.",
                        "Необходимо обратиться к ветеринару через 3 месяца.",
                        "Ухаживайте за шерстью питомца, чтобы предотвратить колтуны."
                };
                String recommendation = recommendations[complaintComboBox.getSelectedIndex()];

                // Вывод результата
                String result = "Здравствуйте, " + clientName + "!\n" +
                        "Данные о вашем питомце:\n" +
                        "- Имя: " + petName + "\n" +
                        "- Вид: " + petType + "\n" +
                        "- Возраст: " + petAge + " лет\n" +
                        "- Жалоба: " + complaint + "\n" +
                        "- Прививка: " + (hasVaccination ? "Да" : "Нет") + "\n" +
                        "Дата и время визита: " + visitTime + "\n" +
                        "Стоимость визита: " + totalPrice + " руб.\n\n" +
                        "Рекомендация: " + recommendation;
                resultArea.setText(result);
            }
        });
        
        // Лиза, я тебя <3
        // Отображение окна
        frame.setVisible(true);
    }
}
