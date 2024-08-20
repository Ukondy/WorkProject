package Utils;


import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class AllureTestListener implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        Allure.step("Test passed: " + context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Allure.step("Test failed: " + context.getDisplayName());
        Allure.attachment("Screenshot", "png");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        Allure.step("Test skipped: " + context.getDisplayName());
    }

}