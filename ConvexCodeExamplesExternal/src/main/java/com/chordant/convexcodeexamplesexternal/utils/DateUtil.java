/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chordant.convexcodeexamplesexternal.utils;

import java.util.Date;

/**
 *
 * @author groov
 */
public class DateUtil {
    
    public static String getTimestamp() {
        Date date = new Date();
        return date.toString();
        
    }
    
}
