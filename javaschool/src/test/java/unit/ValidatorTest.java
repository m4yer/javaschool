package unit;

import com.tsystems.controller.validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {

    @Test
    public void isValidEmail1() {
        Assert.assertTrue(validator.isValid("aspid@gmail.com", validator.EMAIL_PATTERN));
    }

    @Test
    public void isValidEmail2() {
        Assert.assertTrue(validator.isValid("Aspid@Gmail.Com", validator.EMAIL_PATTERN));
    }

    @Test
    public void isValidEmail3() {
        Assert.assertTrue(validator.isValid("AspiDDd24@GMA.Com", validator.EMAIL_PATTERN));
    }

    @Test
    public void isInvalidEmail4() {
        Assert.assertFalse(validator.isValid("!AspiDDd24@GMA.Com", validator.EMAIL_PATTERN));

    }

    @Test
    public void isInvalidEmail5() {
        Assert.assertFalse(validator.isValid("AspiDDd24@-G.ComDD", validator.EMAIL_PATTERN));

    }

    @Test
    public void isInvalidEmail6() {
        Assert.assertFalse(validator.isValid("AspiDDd24@G7.ComDD", validator.EMAIL_PATTERN));

    }

    }
