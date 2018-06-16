package winiumDriver;//******************************************************************************
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


import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class CalculatorTest {

    protected static WiniumDriver calculatorSession;
    private static WebElement calculatorResult = null;
    private DesktopOptions options;
    private WiniumDriverService service;

    @BeforeTest
    public void setup() {
        setDesktopOptions();
        driverInitializationSteps();

        //calculatorSession.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        calculatorResult = calculatorSession.findElementById("150");

    }

    private void setDesktopOptions() {
        options = new DesktopOptions();
        options.setApplicationPath("C:\\Windows\\system32\\win32calc.exe");
    }

    private void driverInitializationSteps() {
        setDesktopOptions();
        setWiniumService();
        try {
            calculatorSession = new WiniumDriver(new URL("http://localhost:9999"), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    protected void stopWiniumService() {
        calculatorSession.close();
        service.stop();

    }

    private void setWiniumService() {
        service = new WiniumDriverService.Builder()
                .usingDriverExecutable(new File("src/main/resources/drivers/Winium.Desktop.Driver.exe"))
                .usingPort(9999)
                .withSilent(false)
                .buildDesktopService();
    }

    @BeforeMethod
    public void Clear() {
        calculatorSession.findElementById("81").click();
        Assert.assertEquals("0", getCalculatorResultText());
    }

    @Test
    public void Addition() {
        calculatorSession.findElementByName("1").click();
        calculatorSession.findElementById("93").click();
        calculatorSession.findElementByName("7").click();
        calculatorSession.findElementById("121").click();
        Assert.assertEquals("8", getCalculatorResultText());
    }

    @Test
    public void Combination() {
        calculatorSession.findElementByName("7").click();
        calculatorSession.findElementById("92").click();
        calculatorSession.findElementByName("9").click();
        calculatorSession.findElementById("93").click();
        calculatorSession.findElementByName("1").click();
        calculatorSession.findElementById("121").click();
        calculatorSession.findElementById("91").click();
        calculatorSession.findElementByName("8").click();
        calculatorSession.findElementById("121").click();
        Assert.assertEquals("8", getCalculatorResultText());
    }

    @Test
    public void Division() {
        calculatorSession.findElementByName("8").click();
        calculatorSession.findElementByName("8").click();
        calculatorSession.findElementById("91").click();
        calculatorSession.findElementByName("1").click();
        calculatorSession.findElementByName("1").click();
        calculatorSession.findElementById("121").click();
        Assert.assertEquals("8", getCalculatorResultText());
    }

    @Test
    public void Multiplication() {
        calculatorSession.findElementByName("9").click();
        calculatorSession.findElementById("92").click();
        calculatorSession.findElementByName("9").click();
        calculatorSession.findElementById("121").click();
        Assert.assertEquals("81", getCalculatorResultText());
    }

    @Test
    public void Subtraction() {
        calculatorSession.findElementByName("9").click();
        calculatorSession.findElementById("94").click();
        calculatorSession.findElementByName("1").click();
        calculatorSession.findElementById("121").click();
        Assert.assertEquals("8", getCalculatorResultText());
    }

    protected String getCalculatorResultText() {
        // trim extra text and whitespace off of the display value
        return calculatorResult.getAttribute("Name").replace("Display is", "").trim();
    }

}
