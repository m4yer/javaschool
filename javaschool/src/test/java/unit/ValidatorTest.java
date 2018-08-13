package unit;

import com.tsystems.controller.validation.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {

    @Test
    public void isValidEmail1() {
        Assert.assertTrue(Validator.isValid("aspid@gmail.com", Validator.EMAIL_PATTERN));
    }

    @Test
    public void isValidEmail2() {
        Assert.assertTrue(Validator.isValid("Aspid@Gmail.Com", Validator.EMAIL_PATTERN));
    }

    @Test
    public void isValidEmail3() {
        Assert.assertTrue(Validator.isValid("AspiDDd24@GMA.Com", Validator.EMAIL_PATTERN));
    }

    @Test
    public void isInvalidEmail4() {
        Assert.assertFalse(Validator.isValid("!AspiDDd24@GMA.Com", Validator.EMAIL_PATTERN));

    }

    @Test
    public void isInvalidEmail5() {
        Assert.assertFalse(Validator.isValid("AspiDDd24@-G.ComDD", Validator.EMAIL_PATTERN));

    }

    @Test
    public void isInvalidEmail6() {
        Assert.assertFalse(Validator.isValid("AspiDDd24@G7.ComDD", Validator.EMAIL_PATTERN));

    }

    }
