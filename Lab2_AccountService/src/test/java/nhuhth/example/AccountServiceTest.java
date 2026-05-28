package nhuhth.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService service;

    @BeforeEach
    void setUp() {
        // Arrange chung
        service = new AccountService();
    }

    // =========================
    // Test isValidEmail
    // =========================

    @ParameterizedTest(name = "Valid email: {0}")
    @ValueSource(strings = {
            "john@example.com",
            "alice.b@mail.co.uk",
            "carol_99@domain.io"
    })
    @DisplayName("isValidEmail trả về true với email hợp lệ")
    void isValidEmail_ValidEmails_ReturnsTrue(String email) {
        // Act
        boolean actual = service.isValidEmail(email);

        // Assert
        assertTrue(actual);
    }

    @ParameterizedTest(name = "Invalid email: {0}")
    @ValueSource(strings = {
            "bobmail.com",
            "missing@dot",
            "@nodomain.com",
            "abc@",
            "abc.com",
            "abc@domain"
    })
    @DisplayName("isValidEmail trả về false với email sai định dạng")
    void isValidEmail_InvalidEmails_ReturnsFalse(String email) {
        // Act
        boolean actual = service.isValidEmail(email);

        // Assert
        assertFalse(actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @DisplayName("isValidEmail trả về false với email null/rỗng/khoảng trắng")
    void isValidEmail_NullBlankOrEmpty_ReturnsFalse(String email) {
        // Act
        boolean actual = service.isValidEmail(email);

        // Assert
        assertFalse(actual);
    }

    // =========================
    // Test registerAccount bằng CSV
    // =========================

    @ParameterizedTest(name = "Row {index}: username={0}, password={1}, email={2}, expected={3}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    @DisplayName("registerAccount kiểm thử với dữ liệu từ file CSV")
    void registerAccount_FromCsv_ReturnsExpectedResult(
            String username,
            String password,
            String email,
            boolean expected
    ) {
        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertEquals(expected, actual);
    }

    // =========================
    // Edge cases
    // =========================

    @Test
    @DisplayName("registerAccount trả về false khi password đúng 6 ký tự")
    void registerAccount_PasswordExactly6_ReturnsFalse() {
        // Arrange
        String username = "bob";
        String password = "abcdef";
        String email = "bob@mail.com";

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertFalse(actual);
    }

    @Test
    @DisplayName("registerAccount trả về true khi password có 7 ký tự")
    void registerAccount_PasswordExactly7_ReturnsTrue() {
        // Arrange
        String username = "bob";
        String password = "abcdefg";
        String email = "bob@mail.com";

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertTrue(actual);
    }

    @Test
    @DisplayName("registerAccount trả về false khi tất cả tham số là null")
    void registerAccount_AllNull_ReturnsFalse() {
        // Act
        boolean actual = service.registerAccount(null, null, null);

        // Assert
        assertFalse(actual);
    }

    @Test
    @DisplayName("registerAccount trả về false khi username chỉ có khoảng trắng")
    void registerAccount_BlankUsername_ReturnsFalse() {
        // Arrange
        String username = "   ";
        String password = "password123";
        String email = "user@mail.com";

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertFalse(actual);
    }

    @Test
    @DisplayName("registerAccount trả về false khi password null")
    void registerAccount_NullPassword_ReturnsFalse() {
        // Arrange
        String username = "john";
        String password = null;
        String email = "john@mail.com";

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertFalse(actual);
    }

    @Test
    @DisplayName("registerAccount trả về false khi email null")
    void registerAccount_NullEmail_ReturnsFalse() {
        // Arrange
        String username = "john";
        String password = "password123";
        String email = null;

        // Act
        boolean actual = service.registerAccount(username, password, email);

        // Assert
        assertFalse(actual);
    }
}