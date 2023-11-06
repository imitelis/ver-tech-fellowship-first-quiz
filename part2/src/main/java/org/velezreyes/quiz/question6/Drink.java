package org.velezreyes.quiz.question6;

public class Drink {
  public String name;
  public boolean isFizzy;

  public Drink(String name, boolean isFizzy) {
    this.name = name;
    this.isFizzy = isFizzy;
  }

  public String getName() {
    return name;
  }

  public boolean isFizzy() {
    return isFizzy;
  }
}