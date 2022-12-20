/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;


public class OurString {
  String s;
  /**
   *
   */
  public OurString(String s){
    this.s=s;
  }
  public boolean equals(Object o){
    if(s.compareTo(o.toString())==0)return true;
    else return false;
  }
  public String toString(){
    return s;
  }
  }
