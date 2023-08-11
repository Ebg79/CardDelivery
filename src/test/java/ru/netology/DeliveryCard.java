package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class DeliveryCard {
    @BeforeEach
    void shouldSetUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");

    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldTestGoodPath() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Евгений Кузьмин");
        $("[data-test-id='phone'] input").setValue("+79150560000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));

    }

    @Test
    void shouldTestIfCityEmpty() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue(" ");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Евгений Кузьмин");
        $("[data-test-id='phone'] input").setValue("+79150560000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);

    }

    @Test
    void shouldTestIfCityNotInList() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue("Саров");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Евгений Кузьмин");
        $("[data-test-id='phone'] input").setValue("+79150560000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[@class='input__sub'][contains(text(), 'Доставка в выбранный город недоступна')]").should(appear);

    }

    @Test
    void shouldTestIfNameIsEmpty() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79150560000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);

    }

    @Test
    void shouldTestIfLatinName() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Drif");
        $("[data-test-id='phone'] input").setValue("+79150560000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear);

    }

    @Test
    void shouldTestIfNameWithSymbol() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Саша123");
        $("[data-test-id='phone'] input").setValue("+79150560000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear);

    }

    @Test
    void shouldTestIfNoDate() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Саша123");
        $("[data-test-id='phone'] input").setValue("+7915056000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[@class='input__sub'][contains(text(), 'Неверно введена дата')]").should(appear);

    }

    @Test
    void shouldTestIfDateIsNotPassed() {
        String planningDate = generateDate(1);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Сашао123");
        $("[data-test-id='phone'] input").setValue("+79150560000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[@class='input__sub'][contains(text(), 'Заказ на выбранную дату невозможен')]").should(appear);
    }

    @Test
    void shouldTestIfShortPhone() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Саша");
        $("[data-test-id='phone'] input").setValue("+7915");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear);

    }


    @Test
    void shouldTestIfAgreementNotCheck() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Александра");
        $("[data-test-id='phone'] input").setValue("+79150562322");
        $(".button__text").click();
        $x("//label[@data-test-id='agreement'][contains(@class, 'input_invalid')]").shouldHave(visible);

    }


}
