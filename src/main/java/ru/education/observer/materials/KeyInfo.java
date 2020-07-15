package ru.education.observer.materials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyInfo {

    private String columnName;
    private boolean isMaster;
    private Class<?> parentClazz;
    private Object keyValue;
    private boolean isChanged = false;

}
