package com.digdes.school.models.partofquery;

import com.digdes.school.models.partsofquery.Expression;
import org.junit.Test;

public class ExpressionTest {

    @Test(expected = Exception.class)
    public void testValuesPartExpressionInvalidRightOperand1() throws Exception {
        new Expression("'age'='hi'", true);
    }

    @Test(expected = Exception.class)
    public void testValuesPartExpressionInvalidRightOperand2() throws Exception {

        new Expression("'age'=5.656", true);

    }

    @Test(expected = Exception.class)
    public void testValuesPartExpressionInvalidRightOperand3() throws Exception {
        new Expression("'age'=5,656", true);

    }

    @Test()
    public void testValuesPartExpressionSpaces() throws Exception {
        new Expression("'age' =      5000", true);
    }

    @Test()
    public void testValuesPartExpressionId() throws Exception {
        new Expression("'Id'=5000", true);
    }


    @Test()
    public void testValuesPartExpressionAge() throws Exception {
        new Expression("'age'=5000", true);
    }

    @Test()
    public void testValuesPartExpressionLastName() throws Exception {
        new Expression("'lastName'='5000'", true);
    }

    @Test()
    public void testValuesPartExpressionCost() throws Exception {
        new Expression("'cost'=5000.90", true);
    }

    @Test()
    public void testValuesPartExpressionActive() throws Exception {
        new Expression("'active'=true", true);
    }

    @Test()
    public void testValuesPartExpressionNegativeNumber() throws Exception {
        new Expression("'age'=-5000", true);
    }

    @Test()
    public void testValuesPartExpressionNegativeNumbersDouble() throws Exception {
        new Expression("'cost'=-5.0", true);
    }

    @Test()
    public void testValuesPartExpressionNumbersCases() throws Exception {
        new Expression("'AgE'=5000", true);
    }

    @Test()
    public void testValuesPartExpressionNull() throws Exception {
        new Expression("'AgE'=null", true);
    }


    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandNull() throws Exception {
        new Expression("'age'=null", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandNull1() throws Exception {
        new Expression("'age'>null", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandNull2() throws Exception {
        new Expression("'age' like null", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandString() throws Exception {
        new Expression("'lastname' > 'true'", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandString1() throws Exception {
        new Expression("'lastname' < 'true'", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandString2() throws Exception {
        new Expression("'lastname' >= 'true'", false);

    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandString3() throws Exception {
        new Expression("'lastname' <= 'true'", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandBool() throws Exception {
        new Expression("'Active' <= 'true'", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandBool1() throws Exception {
        new Expression("'Active' >= 'true'", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandBool2() throws Exception {
        new Expression("'Active' < 'true'", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandBool3() throws Exception {
        new Expression("'Active' > 'true'", false);
    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandLike() throws Exception {
        new Expression("'Active' like true", false);


    }

    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidRightOperandILike() throws Exception {
        new Expression("'Active' ILIKE TRUE", false);
    }


    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidGrammar() throws Exception {
        new Expression("'Active' sdfsd ILIKE TRUE", false);
    }
    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidGrammar1() throws Exception {
        new Expression("'Active' ILIKE, TRUE", false);
    }
    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidGrammar2() throws Exception {
        new Expression("'Active'ILIKE TRUE", false);
    }
    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidGrammar3() throws Exception {
        new Expression("'Active'     ILIKETRUE", false);
    }
    @Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidGrammar4() throws Exception {
        new Expression("'Active' ILIKE TRUE     ", false);
    }@Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidGrammar5() throws Exception {
        new Expression("'Active'  TRUE", false);
    }@Test(expected = Exception.class)
    public void testConditionPartExpressionInvalidGrammar6() throws Exception {
        new Expression("'Active' ILIKE", false);
    }
}
