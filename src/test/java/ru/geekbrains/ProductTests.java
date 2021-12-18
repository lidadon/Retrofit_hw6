package ru.geekbrains;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.dto.Product;
import ru.geekbrains.enums.CategoryType;
import ru.geekbrains.utils.DbUtils;
import ru.geekbrains.utils.PrettyLogger;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductTests extends BaseTest {
    int id = 18463;

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.witcher().character())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FURNITURE.getTitle());
    }

    @BeforeEach
    void setUpNegative() {
        wrongProduct = new Product()
                .withId((int) ((Math.random() + 1) * 10))
                .withTitle(faker.witcher().character())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FURNITURE.getTitle());
    }

    @BeforeEach
    void setUpModify() {
        productWithId = new Product()
                .withId(id)
                .withTitle(faker.witcher().character())
                .withPrice((int) ((Math.random() + 1) * 100))
                .withCategoryTitle(CategoryType.FURNITURE.getTitle());
    }

    @Test
    void getProductsPositiveTest() throws IOException {
        Response<ArrayList<Product>> response = productService.getProducts().execute();

        assertThat(response.code(), equalTo(200));
    }

    @Test
    void createProductPositiveTest() throws IOException {
        Integer countProductsBefore = DbUtils.countProducts(productsMapper);

        Response<Product> response = productService.createProduct(product).execute();
        id = response.body().getId();

        Integer countProductsAfter = DbUtils.countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore + 1));

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void createProductBadRequestTest() throws IOException {
        Integer countProductsBefore = DbUtils.countProducts(productsMapper);

        Response<Product> response = productService.createProduct(wrongProduct).execute();

        Integer countProductsAfter = DbUtils.countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore));

        assertThat(response.code(), equalTo(400));
    }

    @Test
    void modifyProductPositiveTest() throws IOException {
        String titleBefore = DbUtils.getProductsMapper().selectByPrimaryKey(Long.valueOf(id)).getTitle();

        Response<Product> response = productService.modifyProduct(productWithId).execute();

        String titleAfter = DbUtils.getProductsMapper().selectByPrimaryKey(Long.valueOf(id)).getTitle();

        assertThat(titleBefore, is(not(titleAfter)));

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void modifyProductNegativeTest() throws IOException {
        Response<Product> response = productService.modifyProduct(wrongProduct).execute();

        assertThat(response.code(), equalTo(400));
    }

    @Test
    void getProductPositiveTest() throws IOException {
        String titleBefore = DbUtils.getProductsMapper().selectByPrimaryKey(Long.valueOf(id)).getTitle();

        Response<Product> response = productService.getProduct(id).execute();

        String titleAfter = DbUtils.getProductsMapper().selectByPrimaryKey(Long.valueOf(id)).getTitle();

        PrettyLogger.DEFAULT.log(response.body().toString());

        assertThat(titleBefore, equalTo(titleAfter));

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void getProductWithoutIdTest() throws IOException {
        Response<Product> response = productService.getProduct(0).execute();

        assertThat(response.code(), equalTo(404));
    }
}
