package tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.ClickOptions.usingJavaScript;
import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class CdekTests extends TestBase {

    @Test
    @DisplayName("Valid open main page")
    void mainPageTest() {
        step("Open main page", () -> open(""));
        step("Check open main page", () -> {
            $$(".submenu-item__content").shouldHave(itemWithText("Рассчитать стоимость"));
        });
    }

    @Test
    @Disabled("Need credentials for authorization")
    @DisplayName("Successful authorization")
    void successLoginTest() {
        step("Open Login page", () -> open("https://lk.cdek.ru/user/login"));
        step("Authorization user", () -> {
            $(byName("login")).click();
            $("#loginform-login").setValue("");
            $("#loginform-password").setValue("").pressEnter();
        });
        // todo Need credentials for authorization
    }

    @Test
    @DisplayName("Unsuccessful authorization")
    void unSuccessfulLoginTest() {
        step("Open Login page", () -> open("https://lk.cdek.ru/user/login"));
        step("Auth with invalid login and password", () -> {
            $("#loginform-login").setValue("123123123");
            $("#loginform-password").setValue("123123123123").pressEnter();
        });
        step("Check error is display", () -> {
            $(".has-error").shouldHave(or(
                    "Error depends on location",
                    text("Неверный пароль или логин пользователя."),
                    text("Wrong password or user login.")
            ));
        });
    }

    @Test
    @DisplayName("Check the operation of the calculator")
    void calculatorTest() {
        step("Open Calculate page", () -> open("calculate"));
        step("Fill required fields valid data and click calculate button", () -> {
            $(byText("Откуда забираем")).parent().$("input").setValue("Москва");
            $(byText("Москва, Москва, Россия")).click();
            $(byText("Куда доставляем")).parent().$("input").setValue("Ухта");
            $(byText("Ухта, Коми, Россия")).click();
            $(byName("radio")).click(usingJavaScript());
            $(byText("Конверт")).click(usingJavaScript());
            $(byText("Рассчитать")).click(usingJavaScript());
        });
        step("Check cost is correct", () -> {
            $x("//button[@class=\"calculator-service\"]").shouldHave(text("1 070 ₽"));
        });
    }

    @Test
    @DisplayName("Check the operation of the parcel")
    void calculatorParcelTest() {
        step("Open Parcel page", () -> open("box"));
        step("Fill required fields valid data and click calculate button", () -> {
            $$("[placeholder=\"Город назначения\"]").find(visible).setValue("Ухта");
            $(byText("Ухта, Коми, Россия")).click();
            $$("[placeholder=\"Размер посылки\"]").find(visible).click();
            $(byText("Размер S")).click();
            $$(".form-calculate__footer > button").last().click();
        });
        step("Check cost is correct", () -> {
            $$(".result-calculate__cost").last().shouldHave(text("350 ₽"));
        });
    }
}
