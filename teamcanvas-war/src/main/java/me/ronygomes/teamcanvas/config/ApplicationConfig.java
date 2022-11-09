package me.ronygomes.teamcanvas.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;

/*
    By default JSF runs in backward compatible way. Adding @FacesConfig will run as 2.3 which is required to @Inject FacesContext
    Source: https://stackoverflow.com/questions/45682309/changing-faces-config-xml-from-2-2-to-2-3-causes-javax-el-propertynotfoundexcept
 */
@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
}
