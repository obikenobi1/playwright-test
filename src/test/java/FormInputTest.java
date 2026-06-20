import com.microsoft.playwright.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;

@UsePlaywright(HeadlessChromeOptions.class)
public class FormInputTest {

    @BeforeEach
    void openContractPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }

    @DisplayName("Complete Input Form")
    @Test
    void completeForm(Page page) throws URISyntaxException {
        var firstNameField = page.getByLabel("First name");
        String name="Asdf";
        firstNameField.fill(name);
        assertThat(firstNameField).hasValue(name);

        var lastNameField = page.getByLabel("Last name");
        String lastName="Asdf2";
        lastNameField.fill(lastName);
        assertThat(lastNameField).hasValue(lastName);

        var emailNameField = page.getByLabel("Email");
        String emailName="asdf@example.com";
        emailNameField.fill(emailName);
        assertThat(emailNameField).hasValue(emailName);


        var messageField = page.getByLabel("Message");
        String message="asdf asdf asdf";
        messageField.fill(message);
        assertThat(messageField).hasValue(message);

        var subjectField = page.getByLabel("Subject");
        String subject = "warranty";
        subjectField.selectOption(subject);
//        subjectField.selectOption(new SelectOption().setIndex(2));
        assertThat(subjectField).hasValue(subject);

        var uploadField = page.getByLabel("Attachment");
        page.setInputFiles("#attachment", Paths.get(ClassLoader.getSystemResource("data/sample-text.txt").toURI()));
        String uploadedFile = uploadField.inputValue();
        Assertions.assertThat(uploadedFile).endsWith("sample-text.txt");
    }

    @DisplayName("Mandatory Fields")
    @ParameterizedTest
    @ValueSource(strings={"First name","Last name","Email","Message"})
    void mandatoryFields(String fieldname, Page page){
        Locator firstNameField = page.getByLabel("First name");
        Locator lastNameField = page.getByLabel("Last name");
        Locator emailNameField = page.getByLabel("Email");
        Locator messageField = page.getByLabel("Message");
        Locator subjectField = page.getByLabel("Subject");
        Locator uploadField = page.getByLabel("Attachment");

        page.getByLabel(fieldname).clear();

        Locator sendButton = page.getByText("Send");
        sendButton.click();

        Locator errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldname + " is required");
        assertThat(errorMessage).isVisible();
        //org.assertj.core.api.Assertions.assertThat("") assertions for general value
        //Playwright Assertions assertion for playwright locator
    }


}
