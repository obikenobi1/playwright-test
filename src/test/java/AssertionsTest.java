import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@UsePlaywright(HeadlessChromeOptions.class)
public class AssertionsTest {
    @BeforeEach
    void openContractPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }

    @DisplayName("Checking the value of a field")
    @Test
    void fieldValues(Page page){
        var firstNameField = page.getByLabel("First name");
        firstNameField.fill("asdf");

        assertThat(firstNameField).hasValue("asdf");

        assertThat(firstNameField).not().isVisible();
        assertThat(firstNameField).isVisible();
        assertThat(firstNameField).isEditable();
    }

}
