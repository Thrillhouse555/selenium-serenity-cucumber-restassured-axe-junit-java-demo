package uk.georgeansell.tests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

public class HomePage extends PageObject {

    @FindBy(css = ".profile h1")
    private WebElementFacade profileName;

    @FindBy(css = ".profile img[src*='profile.jpeg']")
    private WebElementFacade profilePicture;

    @FindBy(css = ".contact-icons a[href*='contact.html']")
    private WebElementFacade contactIcon;

    @FindBy(css = ".contact-icons a[href*='linkedin']")
    private WebElementFacade linkedInLink;

    @FindBy(css = ".contact-icons a[href*='github']")
    private WebElementFacade gitHubLink;

    @FindBy(id = "profile-blurb")
    private WebElementFacade profileBlurb;

    @FindBy(css = "img[src*='ISTQB.png']")
    private WebElementFacade istqbBadge;

    public void openPage() {
        getDriver().get(System.getProperty("webdriver.base.url", "https://georgeansell.co.uk"));
    }

    public String getProfileName() {
        return profileName.getText();
    }

    public boolean isProfilePictureDisplayed() {
        return profilePicture.isCurrentlyVisible();
    }

    public void clickContactIcon() {
        contactIcon.click();
    }

    public void clickProfilePicture() {
        profilePicture.click();
    }

    public boolean isLinkedInLinkDisplayed() {
        return linkedInLink.isCurrentlyVisible();
    }

    public boolean isGitHubLinkDisplayed() {
        return gitHubLink.isCurrentlyVisible();
    }

    public boolean isProfileBlurbDisplayed() {
        return profileBlurb.isCurrentlyVisible();
    }

    public String getProfileBlurb() {
        return profileBlurb.getText();
    }
}
