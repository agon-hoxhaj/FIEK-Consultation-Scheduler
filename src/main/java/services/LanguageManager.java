package services;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private static final String BASE_NAME="languages.messages";
    private static LanguageManager instance;
    private Locale locale;
    private ResourceBundle resourceBundle;

    private LanguageManager(){
        this.locale=new Locale("en");
        this.resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
    }
    public static LanguageManager getInstance(){
        if(instance == null){
            instance=new LanguageManager();
        }
        return instance;
    }
    public void setLocale(Locale locale){
        this.locale= locale;
        refreshResourceBundle();
    }
    public Locale getLocale(){
        return locale;
    }
    public ResourceBundle getResourceBundle(){
        return resourceBundle;
    }
    public void refreshResourceBundle(){
        this.resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
    }
}
