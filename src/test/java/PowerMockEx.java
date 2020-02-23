import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Utils.class})
public class PowerMockEx {

    @Test
    public void testStaticSockMethods() {
        PowerMockito.mockStatic(Utils.class);
        when(Utils.print("Hello")).thenReturn(true);
        when(Utils.print("Wrong Message")).thenReturn(false);

        assertTrue(Utils.print("Hello"));
        assertFalse(Utils.print("Wrong Message"));

        PowerMockito.verifyStatic(Utils.class, atLeast(2));
        Utils.print(anyString());
    }
}
