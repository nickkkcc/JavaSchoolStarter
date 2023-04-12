package com.digdes.school.models.partofquery;

import com.digdes.school.models.partsofquery.Expression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ConditionPartTest {
    private List<Object> leftObj;
    private List<Object> rightObj;
    private List<Boolean> answers;
    Expression exp;
    @Before
    public void createData() throws Exception {
      leftObj = new ArrayList<>(Arrays.asList(50L,45L,null,10L,11L,5L,
              "hello", "ADhello", "ADhelloAD", "helloAD", "HeLLo", "AdHeLLo", "AdHeLLoAd", "HeLLoAd","hello",
              true, false));
        rightObj = new ArrayList<>(Arrays.asList(50L,-50L,50L, 5.6,"hello", true,
                "hello", "%hello","%hello%", "hello%", "hello", "%hello", "%hello%","hello%", "huh", true, true));
        exp = new Expression("'age'=50", true);

    }
    @Test
    public  void doEqualsTest() throws Exception {
        answers = new ArrayList<>(Arrays.asList(true, false, false, false, false,false,
                true, false,false,false,false, false, false, false, false,
                true, false));
        for (int i=0; i < leftObj.size(); ++i){
            Assert.assertEquals(exp.doEquals(leftObj.get(i), rightObj.get(i)), answers.get(i));
        }
    }
    @Test
    public  void doNotEqualsTest() throws Exception {
        answers = new ArrayList<>(Arrays.asList(false, true, true, true, true,true,
                false, true,true,true,true, true, true, true, true,
                false, true));
        for (int i=0; i < leftObj.size(); ++i){
            Assert.assertEquals(exp.doNotEquals(leftObj.get(i), rightObj.get(i)), answers.get(i));
        }
    }
    @Test
    public  void doGTEqualsTest() throws Exception {
        answers = new ArrayList<>(Arrays.asList(true, true, false, true));
        for (int i=0; i < answers.size(); ++i){
            Assert.assertEquals(exp.doGTEquals(leftObj.get(i), rightObj.get(i)), answers.get(i));
        }
    }
    @Test
    public  void doLTEqualsTest() throws Exception {
        answers = new ArrayList<>(Arrays.asList(true, false, false, false));
        for (int i=0; i < answers.size(); ++i){
            Assert.assertEquals(exp.doLTEquals(leftObj.get(i), rightObj.get(i)), answers.get(i));
        }
    }
    @Test
    public  void doGreaterTest() throws Exception {
        answers = new ArrayList<>(Arrays.asList(false, true, false, true));
        for (int i=0; i < answers.size(); ++i){
            Assert.assertEquals(exp.doGreater(leftObj.get(i), rightObj.get(i)), answers.get(i));
        }
    }
    @Test
    public  void doLessTest() throws Exception {
        answers = new ArrayList<>(Arrays.asList(false, false, false, false));
        for (int i=0; i < answers.size(); ++i){
            Assert.assertEquals(exp.doLess(leftObj.get(i), rightObj.get(i)), answers.get(i));
        }
    }
    @Test
    public  void doLikeTest() throws Exception {
        answers = new ArrayList<>(Arrays.asList(true, true, true, true, false, false,false,false,false));
        for (int i=6; i < leftObj.size() - 2; ++i){
            Assert.assertEquals(exp.doLike(leftObj.get(i), rightObj.get(i)), answers.get(i-6));
        }
    }
    @Test
    public  void doILikeTest() throws Exception {
        answers = new ArrayList<>(Arrays.asList(true,true,true,true,true,true,true,true,false));
        for (int i=6; i < leftObj.size() - 2; ++i){
            Assert.assertEquals(exp.doILike(leftObj.get(i), rightObj.get(i)), answers.get(i-6));
        }
    }


}
