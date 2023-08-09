package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCard {
    SelenideElement form = $("form");
    SelenideElement city = form.$("[data-test-id=city] input");
    SelenideElement cityClick = $(".menu");
    SelenideElement cityChoose = $(byText("Казань"));
    SelenideElement date = form.$("[data-test-id=date] input");
    SelenideElement popupDate = $(".calendar");
    SelenideElement deliveryDate = $(byText("8"));
    SelenideElement name = form.$("[data-test-id=name] input");
    SelenideElement phone = form.$("[data-test-id=phone] input");
    SelenideElement agreement = form.$("[data-test-id=agreement]");
    SelenideElement button = $$("button").find(exactText("Забронировать"));
    SelenideElement notification = $("[data-test-id=notification]");



    private String getFutureDate (int plusDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDate currentDate = LocalDate.now();
        LocalDate controlDate = currentDate.plusDays(plusDate);
        String formattedControlDate = controlDate.format(formatter);
        return formattedControlDate;
    }


    @BeforeEach
    void openHost() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTest() {
        city.setValue("Новосибирск");
        cityClick.waitUntil(exist, 5000).click();
        date.doubleClick().sendKeys(Keys.BACK_SPACE);
        String futureDay = getFutureDate(3);
        date.setValue(futureDay);
        name.setValue("Кузьмин Евгений");
        phone.setValue("+79150560000");
        agreement.click();
        button.click();
        notification.waitUntil(visible, 15000);
    }
    @Test
    void autoPutTest() {
        city.setValue("Ка");
        cityClick.waitUntil(exist, 5000);
        cityChoose.click();
        date.click();
        popupDate.waitUntil(exist, 5000);
        deliveryDate.click();
        name.setValue("Кузьмин Евгений");
        phone.setValue("+79150560000");
        agreement.click();
        button.click();
        notification.waitUntil(visible, 15000);
    }
}
