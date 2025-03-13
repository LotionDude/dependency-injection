package org.example.ioc.application;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.example.ioc.library.container.annotations.Component;
import org.example.ioc.library.container.annotations.Inject;

import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class UserService {
    private final CsvService csvService;

    public String getUserCsv(User user) {
        return this.csvService.createCsv(Maps.newLinkedHashMap(Map.of(
                "name", user.getName(),
                "last_name", user.getLastName(),
                "age", user.getAge()
        )));
    }
}
