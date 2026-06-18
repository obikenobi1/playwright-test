import com.microsoft.playwright.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.SelectOption;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;

@UsePlaywright(FormInputTest.MyOptions.class)
public class FormInputTest {

    public static class MyOptions implements OptionsFactory {
        @Override
        public Options getOptions() {
            return new Options()
                    .setHeadless(false)
                    .setLaunchOptions(new BrowserType.LaunchOptions()
                            .setArgs(Arrays.asList("--disable-extensions")));
        }
    }
    @BeforeEach
    void configure(Playwright playwright) {
        playwright.selectors().setTestIdAttribute("data-test");
    }

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
        System.out.println("");






    }

}
