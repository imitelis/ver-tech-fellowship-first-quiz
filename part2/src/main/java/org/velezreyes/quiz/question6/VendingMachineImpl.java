package org.velezreyes.quiz.question6;

public class VendingMachineImpl implements VendingMachine {

  private int totalQuarters;

  private VendingMachineImpl() {
    totalQuarters = 0;
  }

  @Override
  public void insertQuarter() {
    totalQuarters++;
  }

  @Override
  public Drink pressButton(String name) throws NotEnoughMoneyException, UnknownDrinkException {
    if (totalQuarters >= 3 && name.equals("ScottCola")) {
      totalQuarters -= 3;
      return new Drink(name, true);
    } else if (totalQuarters >= 4 && name.equals("KarenTea")) {
      totalQuarters -= 4;
      return new Drink(name, false);
    } else if ((!name.equals("KarenTea") || !name.equals("KarenTea")) && totalQuarters > 0) {
      throw new UnknownDrinkException();
    } else if (totalQuarters <= 0) {
      throw new NotEnoughMoneyException();
    }
    throw new NotEnoughMoneyException();
  }

  public static VendingMachine getInstance() {
    return new VendingMachineImpl();
  }
}
