package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Infoページに載せるデータ
@Component
public class TotalUsersInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        // Infoページに載せるデータを作成する。特に思いつかなかったので、
        // キーをfooもしくはbarにして、それぞれの値を適当に入れて表示させている。
        Map<String, Integer> dummyData = new HashMap<>();

        dummyData.put("foo", 100);
        dummyData.put("bar", 200);

        builder.withDetail("dummy", dummyData);
    }
}
