package tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.ClickOptions.usingJavaScript;
import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.or;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CdekTests extends TestBase {

    @Test
    @DisplayName("Корректное открытие главной страницы")
    void mainPageTest() {
        open("https://www.cdek.ru/ru/");
        $$(".submenu-item__content").shouldHave(itemWithText("Рассчитать стоимость"));
    }


    @Test
    @Disabled("Нужны учетные данные")
    @DisplayName("Успешная авторизация")
    void successfulLoginTest() {
        open("https://www.cdek.ru/ru/");
        $(byName("login")).click();
        $("#loginform-login").setValue("");
        $("#loginform-password").setValue("").pressEnter();
        // todo
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    void unSuccessfulLoginTest() {
        open("https://lk.cdek.ru/user/login");

        $("#loginform-login").setValue("123123123");
        $("#loginform-password").setValue("123123123123").pressEnter();

        $(".has-error").shouldHave(
                or("Error depends on location", text("Неверный пароль или логин пользователя."), text("Wrong password or user login."))
        );
    }

    @Test
    @DisplayName("Проверка работы калькулятора")
    void calculatorTest() {
        open("https://www.cdek.ru/ru/calculate");

        $x("//label[text()=\"Откуда забираем\"]/..//input").setValue("Москва");
        $(byText("Москва, Москва, Россия")).click();
        $x("//label[text()=\"Куда доставляем\"]/..//input").setValue("Ухта");
        $(byText("Ухта, Коми, Россия")).click();
        $(byName("radio")).click(usingJavaScript());
        $(byText("Конверт")).click(usingJavaScript());
        $(byText("Рассчитать")).click(usingJavaScript());

        $x("//button[@class=\"calculator-service\"]").shouldHave(text("1 070 ₽"));
    }
}
