package ru.education.observer.materials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wrapper {

    private KeyInfo keyInfo;
    private List<Wrapper> subscribers = new ArrayList<>();

    public void subscribe(Wrapper s) {
        subscribers.add(s);
    }

    public void unSubscribe(Wrapper s) {
        subscribers.remove(s);
    }

    public void notifySubscribers() {
        for (Wrapper subscriber : subscribers) {
            subscriber.methodCallByNotify();
        }
    }

    public void updateIsChanged(boolean isChanged) {
        keyInfo.setChanged(isChanged);
    }

    public void methodCallByNotify() {
        this.updateIsChanged(true);
        this.notifySubscribers();
    }

    public boolean setKeyInfoByObserverPattern(KeyInfo keyInfo) {
        if (this.keyInfo.getColumnName().equals(keyInfo.getColumnName())) {
            this.keyInfo = keyInfo;
            return true;
        } else {
            boolean isSet = false;
            for (Wrapper subscriber : subscribers) {
                isSet = subscriber.setKeyInfoByObserverPattern(keyInfo);
            }
            return isSet;
        }
    }

    public boolean isDistinct() {
        if (this.keyInfo.isChanged()) {
            return true;
        }
        boolean flag = false;
        for (Wrapper subscriber : subscribers) {
            boolean result = subscriber.isDistinct();
            if (result) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
