package org.workspace7.springboot;

/**
 * @author kameshs
 */
public class AppEnvironment {
    private String keyCloakUrl;
    private String redirectUri;

    public String getKeyCloakUrl() {
        return keyCloakUrl;
    }

    public void setKeyCloakUrl(String keyCloakUrl) {
        this.keyCloakUrl = keyCloakUrl;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
