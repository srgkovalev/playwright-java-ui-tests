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

    String browser();

    Boolean headless();

    @Key("slow.motion")
    int slowMotion();

    int timeout();

    String userName();

    String userPass();

    String userNameSession();
}
