package ru.geekbrains;

import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.dto.Category;
import ru.geekbrains.enums.CategoryType;
import ru.geekbrains.utils.PrettyLogger;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CategoryTest extends BaseTest {

    @Test
    void getCategoryByIdPositiveTest() throws IOException {
        Integer id = CategoryType.FURNITURE.getId();

        Response<Category> response = categoryService
                .getCategory(id)
                .execute();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.body().getTitle(), equalTo(CategoryType.FURNITURE.getTitle()));
        assertThat(response.body().getId(), equalTo(CategoryType.FURNITURE.getId()));
    }

    @Test
    void getCategoryByIdNegativeTest() throws IOException {
        Integer id = 85;

        Response<Category> response = categoryService
                .getCategory(id)
                .execute();

        assertThat(response.code(), equalTo(404));
    }
}
