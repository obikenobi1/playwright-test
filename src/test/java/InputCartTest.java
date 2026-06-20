import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@UsePlaywright(HeadlessChromeOptions.class)

public class InputCartTest {
    @BeforeEach
    void openContractPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @DisplayName("Complete Input Form")
    @Test
    void inputCart(Page page){
        page.getByText("Bolt Cutters").click();
        page.getByText("Add to cart").click();

        page.waitForCondition(() -> page.getByTestId("cart-quantity").textContent().equals("1"));

    }
}
