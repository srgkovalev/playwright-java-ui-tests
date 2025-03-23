package com.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:config.properties"
})
public interface Configuration extends Config {

    @Key("base.url")
    String baseUrl();

    String domain();

    @Key("product.path")
    String productPath();

    @Key("cart.path")
    String cartPath();

    @DefaultValue("chromium")
    String browser();

    @DefaultValue("false")
    Boolean headless();

    @Key("slow.motion")
    @DefaultValue("100")
    int slowMotion();

    @DefaultValue("10000")
    int timeout();

    String userName();

    String userPass();

    String userNameSession();
}
