package qa.learning;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WildBerriesTest {

    @ValueSource(strings = {"футболка женская", "футболка мужская"})
    @ParameterizedTest(name = "При поиске на wildberries по запросу {0} отображается {0}")
    void wildBerriesTest(String testData){
        Selenide.open("https://www.wildberries.ru");
        $("#searchInput").setValue(testData);
        $("#applySearchBtn").click();
        $$("#catalog").find(text(testData)).shouldBe(visible);
    }

    //@CsvFileSource(resources = "test_data/1.csv")
    @CsvSource(value = {"футболка женская, По запросу «футболка женская» найдено",
            "футболка мужская, По запросу «футболка мужская» найдено"})

    @ParameterizedTest(name = "При поиске на wildberries по запросу {0} отображается {0}")
    void wildBerriesTestSource(String searchData, String expectedResult){
        Selenide.open("https://www.wildberries.ru");
        $("#searchInput").setValue(searchData);
        $("#applySearchBtn").click();
        $$(".searching-results__title").find(text(expectedResult)).shouldBe(visible);
    }

    //для разных типов данных в тестах
    static Stream<Arguments> wildBerriesTestProviders(){
        return Stream.of(
                Arguments.of("футболка женская", List.of("По запросу «футболка женская» найдено")),
                Arguments.of("футболка мужская", List.of("По запросу «футболка мужская» найдено"))
        );
    }
    @MethodSource(value = "wildBerriesTestProviders")
    @ParameterizedTest(name = "При поиске на wildberries по запросу {0} отображается {1}")
    void wildBerriesTestComplex(String searchData, List<String> expectedResult){
        Selenide.open("https://www.wildberries.ru");
        $("#searchInput").setValue(searchData);
        $("#applySearchBtn").click();
        $$(".searching-results__title").shouldHave(CollectionCondition.texts(expectedResult));
    }

    @EnumSource(Phone.class)
    @ParameterizedTest
    void enumTest(Phone phone){
        Selenide.open("https://www.wildberries.ru");
        $("#searchInput").setValue(phone.desc);
        $("#applySearchBtn").click();
        $$("#mainContainer").find(text(phone.desc)).shouldBe(visible);

    }
}
