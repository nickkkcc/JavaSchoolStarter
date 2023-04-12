package com.digdes.school;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class JavaSchoolStarterTest {
    JavaSchoolStarter starter;
    List<Map<String, Object>> test;

    @Before
    public void createStarter() {
        starter = new JavaSchoolStarter();
    }

    @Test
    public void insertTest() throws Exception {
        test = new ArrayList<>() {{
            add(new HashMap<>() {{
                put("age", 45L);
                put("cost", -6.7);
                put("lastName", "Ni ck");
            }});
        }};

        Assert.assertEquals(test, starter.execute("insert values 'age' =  45, 'cost' = -6.7, 'lastName' = 'Ni ck'"));
    }

    @Test
    public void SelectTest() throws Exception {
        test = new ArrayList<>() {{
            add(new HashMap<>() {{
                put("age", 45L);
                put("cost", -6.7);
                put("lastName", "Ко ля");
            }});

            add(new HashMap<>() {{
                put("age", 678L);
                put("cost", 6.7);
                put("lastName", "Ko lya");
            }});
        }};
        starter.execute("insert values 'age' = 45, 'cost' = -6.7, 'lastName' = 'Ко ля'");
        starter.execute("insert values 'age' = 678, 'cost' = 6.7, 'lastName' = 'Ko lya'");
        List<Map<String, Object>> obj = starter.execute("select");
        Assert.assertEquals(test, obj);
    }

    //Можно поиграться с операторами в where для различных вариантов.
    @Test
    public void SelectWhereTest() throws Exception {
        test = new ArrayList<>() {{
            add(new HashMap<>() {{
                put("age", 678L);
                put("cost", 6.7);
                put("lastName", "Ko lya");
            }});
        }};
        starter.execute("insert values 'age' = 45, 'cost' = -6.7, 'lastName' = 'ko LyA'");
        starter.execute("insert values 'age' = 678, 'cost' = 6.7, 'lastName' = 'Ko lya'");
        List<Map<String, Object>> obj = starter.execute("select where 'lastname' like 'Ko lya%'");

        Assert.assertEquals(test, obj);
    }

    @Test
    public void deleteTest() throws Exception {
        test = new ArrayList<>() {{
            add(new HashMap<>() {{
                put("age", 678L);
                put("cost", 6.7);
                put("lastName", "Kolya");
            }});
        }};
        starter.execute("insert values 'age' = 678, 'cost' = 6.7, 'lastName' = 'Kolya'");
        List<Map<String, Object>> obj = starter.execute("delete");
        Assert.assertEquals(test,obj);
    }

    @Test
    public void deleteWhereTest() throws Exception {
        test = new ArrayList<>() {{
            add(new HashMap<>() {{
                put("age", 678L);
                put("cost", 6.7);
                put("lastName", "Kolya");
            }});
            add(new HashMap<>() {{
                put("age", 67L);
                put("cost", -6.7);
                put("lastName", "Tima");
            }});
        }};
        starter.execute("insert values 'age' = 678, 'cost' = 6.7, 'lastName' = 'Kolya'");
        starter.execute("insert values 'age' = 67, 'cost' = -6.7, 'lastName' = 'Tima'");
        List<Map<String, Object>> obj = starter.execute("delete where 'age' = 67");
        Assert.assertEquals(test.get(1),obj.get(0));
    }

    @Test
    public  void updateTest() throws Exception {
        test = new ArrayList<>() {{
            add(new HashMap<>() {{
                put("age", 678L);
                put("cost", 6.7);
                put("lastName", "Kolya");
            }});
            add(new HashMap<>() {{
                put("age", 67L);
                put("cost", 6.7);
                put("lastName", "Kolya");
            }});
        }};
        starter.execute("insert values 'age' = 678, 'cost' = 6.7, 'lastName' = 'Kolya'");
        List<Map<String, Object>> obj = starter.execute("update values 'age' = 67");
        Assert.assertEquals(test.get(1), obj.get(0));
    }

    @Test
    public  void updateWhereTest() throws Exception {
        test = new ArrayList<>() {{
            add(new HashMap<>() {{
                put("age", 45L);
                put("cost", -6.7);
                put("lastName", "Nick");
            }});
            add(new HashMap<>() {{
                put("age", 45L);
                put("cost", 8.7);
                put("lastName", "Nick");
            }});
            add(new HashMap<>() {{
                put("age", 46L);
                put("cost", -6.7);
                put("lastName", "Ni  ck");
            }});
        }};
        starter.execute("insert values 'age' = 45, 'cost' = -6.7, 'lastName' = 'Nick'");
        starter.execute("insert values 'age' = 45, 'cost' = 8.7, 'lastName' = 'Nick'");
        List<Map<String, Object>> obj =  starter.execute("update values 'age' = 46, 'cost' = -6.7, 'lastname'='Ni  ck'" +
                " where 'cost'=8.7");
        Assert.assertEquals(test.get(2), obj.get(0));

    }
}
