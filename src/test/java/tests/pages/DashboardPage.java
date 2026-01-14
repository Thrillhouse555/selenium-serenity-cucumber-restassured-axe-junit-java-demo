package uk.georgeansell.tests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends PageObject {

    @FindBy(id = "welcome")
    private WebElementFacade welcomeMessage;

    @FindBy(id = "profileTextForm")
    private WebElementFacade profileTextForm;

    @FindBy(id = "linksManagement")
    private WebElementFacade linksManagementSection;

    @FindBy(id = "linksList")
    private WebElementFacade linksList;

    @FindBy(id = "logout")
    private WebElementFacade logoutLink;

    @FindBy(id = "profileText")
    private WebElementFacade profileTextArea;

    @FindBy(id = "addLinkBtn")
    private WebElementFacade addLinkButton;

    public void openPage() {
        open();
    }

    public boolean isWelcomeMessageVisible() {
        return welcomeMessage.isCurrentlyVisible();
    }

    public String getWelcomeMessageText() {
        return welcomeMessage.getText();
    }

    public boolean isProfileTextFormVisible() {
        return profileTextForm.isDisplayed();
    }

    public boolean isLinksManagementVisible() {
        return linksManagementSection.isDisplayed();
    }

    public boolean isLinksListVisible() {
        return linksList.isCurrentlyVisible();
    }

    public void clickLogout() {
        logoutLink.click();
    }

    public boolean isOnDashboard() {
        return getDriver().getCurrentUrl().contains("/dashboard.html");
    }

    public boolean hasProfileTextArea() {
        return profileTextArea.isPresent();
    }

    public boolean hasAddLinkButton() {
        return addLinkButton.isPresent();
    }
}
