package uk.georgeansell.tests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

public class QRCodePage extends PageObject {

    @FindBy(css = "img[src*='qr-code.png']")
    private WebElementFacade qrCodeImage;

    public boolean isQRCodeImageDisplayed() {
        return qrCodeImage.isCurrentlyVisible();
    }

    public boolean isOnQRCodePage() {
        return getDriver().getCurrentUrl().contains("qrcode.html");
    }
}
