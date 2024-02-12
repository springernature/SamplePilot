package com.sn.utils;

import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.sn.apt.listeners.ExtentTestManager;

/**
 * Common Utils Class.
 */
public class CommonUtils {

    /**
     * Logger .
     */

    /**
     * To assert true condition .
     * 
     * @param condition
     *            .
     * @param message
     *            .
     * @author nap0878
     * @Description To verify if given condition returns true
     */
    public void verifyIfTrue(final boolean condition, final String message) {
        Assert.assertTrue(condition);
        ExtentTestManager.getTest().log(Status.PASS, message);
    }

    /**
     * To assert false condition .
     * 
     * @param condition
     *            .
     * @param message
     *            .
     * @author nap0878
     * @Description To verify if given condition returns false
     */
    public void verifyIfFalse(final boolean condition, final String message) {

        Assert.assertFalse(condition);
        ExtentTestManager.getTest().log(Status.PASS, message);
    }

}
