package winAppDriver;//******************************************************************************
//
// Copyright (c) 2016 Microsoft Corporation. All rights reserved.
//
// This code is licensed under the MIT License (MIT).
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//
//******************************************************************************

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CalculatorTest {

    private static WindowsDriver CalculatorSession = null;
    private static WebElement calculatorResult = null;

    @AfterClass
    public static void TearDown() {
        calculatorResult = null;
        if (CalculatorSession != null) {
            CalculatorSession.quit();
        }
        CalculatorSession = null;
    }

    @BeforeTest
    public void setup() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "win32calc.exe");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, Platform.WIN10);
            capabilities.setCapability("deviceName", "WindowsPC");
            CalculatorSession = new WindowsDriver<WindowsElement>(new URL("http://127.0.0.1:4723/"), capabilities);
            CalculatorSession.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            calculatorResult = CalculatorSession.findElementByAccessibilityId("150");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @BeforeClass
    public void Clear() {
        CalculatorSession.findElementByAccessibilityId("81").click();
        Assert.assertEquals("0", getCalculatorResultText());
    }

    @Test
    public void Addition() {
        CalculatorSession.findElementByName("1").click();
        CalculatorSession.findElementByAccessibilityId("93").click();
        CalculatorSession.findElementByName("7").click();
        CalculatorSession.findElementByAccessibilityId("121").click();
        Assert.assertEquals("8", getCalculatorResultText());
    }

    @Test
    public void Combination() {
        CalculatorSession.findElementByName("7").click();
        CalculatorSession.findElementByAccessibilityId("92").click();
        CalculatorSession.findElementByName("9").click();
        CalculatorSession.findElementByAccessibilityId("93").click();
        CalculatorSession.findElementByName("1").click();
        CalculatorSession.findElementByAccessibilityId("121").click();
        CalculatorSession.findElementByAccessibilityId("91").click();
        CalculatorSession.findElementByName("8").click();
        CalculatorSession.findElementByAccessibilityId("121").click();
        Assert.assertEquals("8", getCalculatorResultText());
    }

    @Test
    public void Division() {
        CalculatorSession.findElementByName("8").click();
        CalculatorSession.findElementByName("8").click();
        CalculatorSession.findElementByAccessibilityId("91").click();
        CalculatorSession.findElementByName("1").click();
        CalculatorSession.findElementByName("1").click();
        CalculatorSession.findElementByAccessibilityId("121").click();
        Assert.assertEquals("8", getCalculatorResultText());
    }

    @Test
    public void Multiplication() {
        CalculatorSession.findElementByName("9").click();
        CalculatorSession.findElementByAccessibilityId("92").click();
        CalculatorSession.findElementByName("9").click();
        CalculatorSession.findElementByAccessibilityId("121").click();
        Assert.assertEquals("81", getCalculatorResultText());
    }

    @Test
    public void Subtraction() {
        CalculatorSession.findElementByName("9").click();
        CalculatorSession.findElementByAccessibilityId("94").click();
        CalculatorSession.findElementByName("1").click();
        CalculatorSession.findElementByAccessibilityId("121").click();
        Assert.assertEquals("8", getCalculatorResultText());
    }

    protected String getCalculatorResultText() {
        // trim extra text and whitespace off of the display value
        return calculatorResult.getText().replace("Display is", "").trim();
    }

}
