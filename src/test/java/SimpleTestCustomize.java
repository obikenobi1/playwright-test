import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Arrays;
import java.util.List;

public class SimpleTestCustomize {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;



    private static Page page;

    @BeforeAll
    public static void setUpBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
                        .setArgs(Arrays.asList("--disable-extensions"))
        );
        browserContext = browser.newContext();
        playwright.selectors().setTestIdAttribute("data-test");

    }

    @BeforeEach
    public void setUp(){
        page = browserContext.newPage();
    }

    @AfterAll
    public static void tearDown(){
        browser.close();
        playwright.close();
    }

    @Test
    void shouldShowThePageTitle(){

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));

    }

    @Test
    void shouldSearchByKeyword(){
        page.navigate("https://practicesoftwaretesting.com/");
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        int matching = page.locator(".card").count();

        Assertions.assertTrue(matching > 0);

    }

    @Test
    void checkOutOfStock(){
        page.navigate("https://practicesoftwaretesting.com/");
        //auto locator for menubar
        Locator menubar = page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu"));
        Locator categoriesBtn = menubar.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Categories"));
        //since the URL is under menubar element, not on categories element. I use menubar Locator for get by role and it works.
        Locator handToolsLink = menubar.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Hand Tools"));

        assertThat(handToolsLink).hasText("Hand Tools");
        categoriesBtn.click();
        handToolsLink.click();
        assertThat(page).hasURL("https://practicesoftwaretesting.com/category/hand-tools");

        //using wait because the list need to load first
        page.waitForSelector("a.card");

        //page.waitForCondition(() -> page.getByRole(AriaRole.ALERT).isHidden() );

        //filter usage
        List<String> allProducts= page.getByTestId("product-name").filter(new Locator.FilterOptions().setHasNotText("Drill")).allTextContents();
        List<String> outOfStockItems = page.locator("a.card")
                .filter(new Locator.FilterOptions().setHasText("Out of stock"))
                .getByTestId("product-name")
                .allTextContents()
                .stream()
                .map(String::trim)
                .toList();
        //need trim because have inconsistent space
        Assertions.assertTrue(outOfStockItems.contains("Long Nose Pliers"));


    }


}
