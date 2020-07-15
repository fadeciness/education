package ru.education.observer;

import ru.education.observer.materials.KeyInfo;
import ru.education.observer.materials.Wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    Изучение паттерна Наблюдатель
    Оптимизация доставки изменений.
    Если родительский ключ изменился (т.е. после проверки isChanged = true),
    то все дочерние ключи получают данный флаг через механизм уведомлений.
 */
public class Main {

    public static void main(String[] args) {

        KeyInfo master = new KeyInfo();
        master.setColumnName("master");

        KeyInfo o1 = new KeyInfo();
        KeyInfo o2 = new KeyInfo();
        KeyInfo o3 = new KeyInfo();

        o1.setColumnName("o1");
        o2.setColumnName("o2");
        o3.setColumnName("o3");

        KeyInfo o4 = new KeyInfo();
        KeyInfo o5 = new KeyInfo();
        KeyInfo o6 = new KeyInfo();

        o4.setColumnName("o4");
        o5.setColumnName("o5");
        o6.setColumnName("o6");

        Wrapper wMaster = new Wrapper();
        wMaster.setKeyInfo(master);

        Wrapper wO1 = new Wrapper();
        wO1.setKeyInfo(o1);

        Wrapper wO2 = new Wrapper();
        wO2.setKeyInfo(o2);

        Wrapper wO3 = new Wrapper();
        wO3.setKeyInfo(o3);

        Wrapper wO4 = new Wrapper();
        wO4.setKeyInfo(o4);

        Wrapper wO5 = new Wrapper();
        wO5.setKeyInfo(o5);

        Wrapper wO6 = new Wrapper();
        wO6.setKeyInfo(o6);

        wMaster.subscribe(wO1);
        wMaster.subscribe(wO2);

        wO1.subscribe(wO3);
        wO1.subscribe(wO4);

        wO4.subscribe(wO5);
        wO4.subscribe(wO6);

        ArrayList<Wrapper> listMaster = new ArrayList<>();
        listMaster.add(wMaster);
        checkChilds(listMaster);

        ArrayList<Wrapper> list = new ArrayList<>();
        list.add(wMaster);
        list.add(wO1);
        list.add(wO2);
        list.add(wO3);
        list.add(wO4);
        list.add(wO5);
        list.add(wO6);

        for (Wrapper wrapper : list) {
            System.out.println(wrapper.getKeyInfo().getColumnName() + " = flag = " + wrapper.getKeyInfo().isChanged());
        }

        /*
        Dependency tree

        master  -   o1  -   o3
                        -   o4  -   o5
                                -   o6
                -   o2
         */


        KeyInfo updated = new KeyInfo();
        updated.setColumnName("o4");
        updated.setKeyValue("It's updated");
        /*
            Осуществляется поиск ключа с данным columnName
            Изменяется его keyValue.
            Поиск начинается с мастер-ключа.
         */
        wMaster.setKeyInfoByObserverPattern(updated);

        System.out.println(wMaster);


    }

    public static void checkChilds(List<Wrapper> wrapperKeyInfo) {
        for (Wrapper wrapper : wrapperKeyInfo) {
            boolean isChanged = callMyMechanism(wrapper.getKeyInfo());
            System.out.println(wrapper.getKeyInfo().getColumnName() + " = flag = " + isChanged);
            System.out.println();
            if (isChanged) {
                wrapper.updateIsChanged(true);
                wrapper.notifySubscribers();
            } else {
                wrapper.updateIsChanged(false);
                checkChilds(wrapper.getSubscribers());
            }
        }
        System.out.println();
        System.out.println();
    }

    public static boolean callMyMechanism(KeyInfo keyInfo) {
        Random r = new Random();
        boolean randomBoolean = r.nextBoolean();
        return randomBoolean;
    }

}
