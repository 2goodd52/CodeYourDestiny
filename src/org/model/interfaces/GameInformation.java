/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.model.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Dean
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GameInformation {
    
    String gameName() default "";
    String gameSummary() default "";
    
}
