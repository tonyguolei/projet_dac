/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerts;

/**
 * All the available alerts handled by bootstrap
 * Used buy Alert.java
 * @author bemug
 */

public enum AlertType {
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    DANGER("danger");
    
    private String name = "";

    AlertType(String name){
      this.name = name;
    }

    @Override
    public String toString(){
      return name;
    }
}