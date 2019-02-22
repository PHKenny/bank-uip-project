package bank;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * @author mochi
 */
public class Bank {
  public static int loginAs, optMenu;
  public static String username, password;
  public static ArrayList users = new ArrayList(),
                          passwords = new ArrayList(),
                          logins = new ArrayList(),
                          credits = new ArrayList<Double>(),
                          baseCredits = new ArrayList<Double>(),
                          payTo = new ArrayList<Double>();
  public static Hashtable<Integer, ArrayList<Double>> balance = new Hashtable<Integer, ArrayList<Double>>();
  
  /**
   * Print  many enter's in terminal
   */
  public static void printSpaces () {
    System.out.printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
  }

  public static void await() {
    System.out.print("Presione cualquier tecla para continuar");
    Scanner in = new Scanner(System.in);
    in.nextLine();
  }

  /**
   * Calculate total balance per user
   * @param balance type ArrayList
   * @return totalBalance
   */
  public static double calculateBalance (ArrayList<Double> balance) {
    double totalBalance = 0.0;
    
    for (int i = 0; i < balance.size(); i++) {
      totalBalance += balance.get(i);
    }

    return totalBalance;
  }

  public static void createSeeds () {
    Bank.users.add("cliente");
    Bank.passwords.add("cliente");
    Bank.logins.add((new Date()).toString());
    Bank.credits.add(0.0);
    Bank.baseCredits.add(0.0);
    Bank.payTo.add(-1);
    ArrayList<Double> movements = new ArrayList<Double>();

    movements.add(15000.00);
    movements.add(1000.00);
    movements.add(-3000.00);
    Bank.balance.put(0, movements);

    Bank.users.add("cliente2");
    Bank.passwords.add("cliente2");
    Bank.logins.add((new Date()).toString());
    Bank.credits.add(0.0);
    Bank.baseCredits.add(0.0);
    Bank.payTo.add(-1);
    movements = new ArrayList<Double>();

    movements.add(12000.00);
    movements.add(10000.00);
    movements.add(-16000.00);
    Bank.balance.put(1, movements);
  }

  /**
   * Startup app
   */
  public static void startup () {
    Bank.createSeeds();

    Bank.printSpaces();
    String exit = "N";
    
    do {
      Scanner in = new Scanner(System.in);
      
      System.out.println("Inicio de sesión");
      System.out.println("Para ingresar como banco, ingrese 1");
      System.out.println("Para ingresar como cliente, ingrese 2");

      System.out.print("¿Cómo desea ingresar? ");
      Bank.loginAs = in.nextInt();

      Bank.printSpaces();

      boolean flag = false;

      do {
        in = new Scanner(System.in);
        System.out.print("Ingrese su nombre de usuario: ");
        Bank.username = in.nextLine();

        System.out.print("Ingrese su contraseña: ");
        Bank.password = in.nextLine();

        if (Bank.loginAs == 1) {
          System.out.println("Ingresando como banco...");
          flag = Bank.loginBank();

          if (flag) {
            Bank.bankHome();
          }
        } else {
          System.out.println("Ingresando como cliente...");
          flag = Bank.loginCustomer();

          if (flag) {
            Bank.customerHome();
          }
        }

        Bank.printSpaces();
      } while (!flag);

      System.out.print("¿Desea salir del sistema? [Y/N] ");
      in = new Scanner(System.in);
      exit = in.nextLine();
    } while (exit.toLowerCase().equals("n"));
  }
  
  public static void main (String[] args) {
    Bank.startup();
  }

  /**
   * Shows statement to customer
   * @param userId type int
   */
  public static void customerStatement (int userId) {
    Bank.printSpaces();

    System.out.println("Saldo disponible: $" + Bank.calculateBalance(Bank.balance.get(userId)));

    double ammount = 0.0;

    System.out.printf("%10s | %30s\n", "Movimiento", "Saldo después de la transacción");

    ArrayList<Double> movements = Bank.balance.get(userId);

    for (int i = 0; i < movements.size(); i++) {
      ammount += movements.get(i);
      System.out.printf("$%9.2f | $%9.2f\n", movements.get(i), ammount);
    }

    Bank.await();
  }

  /**
   * Rquest credit
   * @param userId type int
   */
  public static void requestCredit (int userId) {
    Bank.printSpaces();

    Scanner in = new Scanner(System.in);

    if (Bank.users.size() > 1) {
      if (Bank.credits.get(userId).equals(0.0)) {
        double maxAmmmount = 0;
        int uid = -1;

        for (int i = 0; i < Bank.users.size(); i++) {
          if (i == userId) {
            continue;
          } else if (uid == -1) {
            uid = i;
            maxAmmmount = Bank.calculateBalance(Bank.balance.get(i));
          } else if (maxAmmmount < Bank.calculateBalance(Bank.balance.get(i))) {
            uid = i;
            maxAmmmount = Bank.calculateBalance(Bank.balance.get(i));
          }
        }

        Bank.balance.get(uid).add((maxAmmmount * 0.9) * -1);
        Bank.balance.get(userId).add((maxAmmmount * 0.9));
        Bank.credits.set(userId, ((maxAmmmount * 0.9) * 1.3));
        Bank.baseCredits.set(userId, (maxAmmmount * 0.9));
        Bank.payTo.set(userId, uid);

        System.out.println("Se le ha otorgado un crédito de $" + (maxAmmmount * 0.9));
        System.out.println("Se le notifica que para pagar su crédito, deberá abonar un total de $" + ((maxAmmmount * 0.9) * 1.3));
      } else {
        System.out.println("Ya posee un crédito abierto con la entidad bancaria, realice el pago de dicho crédito");
      }
    } else {
      System.out.println("En este momento el Banco XYZ no está emitiendo créditos bancarios");
    }

    Bank.await();
  }

  /**
   * Login as Customer
   */

  public static boolean loginCustomer () {
    if (Bank.users.indexOf(Bank.username) != -1) {
      int userId = Bank.users.indexOf(Bank.username);
      if (Bank.passwords.get(userId).equals(Bank.password)) {
        System.out.println("Inicio de sesión exitoso");
        return true;
      } else {
        System.out.println("Usuario o contraseña inválidos");
        return false;
      }
    } else {
      System.out.println("Usuario o contraseña inválidos");
      return false;
    }
  }

  public static void payCredits (int userId) {
    Bank.printSpaces();
    if (Bank.credits.get(userId).equals(0.0)) {
      System.out.println("No posee pagos pendientes en los créditos bancarios");
    } else {
      Scanner in = new Scanner(System.in);

      System.out.println("Posee un saldo deudor de: $" + Bank.credits.get(userId));
      
      double total = (double)Bank.credits.get(userId);
      double flag = 0;

      while (true) {
        System.out.print("Ingrese la cantidad que va a abonar: ");
        flag = in.nextDouble();

        if (flag <= 0) {
          System.out.println("No puede ingresar una cifra menor a 0");
        } else if (flag > total) {
          System.out.println("No puede ingresar una cifra mayor a su deuda total");
        } else if (flag > Bank.calculateBalance(Bank.balance.get(userId))) {
          System.out.println("No puede ingresar una cifra mayor a su balance total");
        } else {
          Bank.balance.get(userId).add(flag * -1);
          int userToPay = (int)Bank.payTo.get(userId);
          double flag2 = (double)Bank.baseCredits.get(userId);

          if (flag2 >= flag) {
            Bank.baseCredits.set(userId, flag2);
            Bank.credits.set(userId, (total - flag));
            Bank.balance.get(userToPay).add(flag2);
            Bank.payTo.set(userId, -1);
          } else {
            if (userToPay == -1) {
              Bank.credits.set(userId, (total - flag));
            } else {
              Bank.baseCredits.set(userId, (total - flag));
              Bank.credits.set(userId, (total - flag));
              Bank.balance.get(userToPay).add(flag);
            }
          }

          System.out.println("Se ha registrado el pago exitosamente");
          break;
        }
      }
    }
  }

  public static void changePassword (int userId) {
    Bank.printSpaces();
    Scanner in = new Scanner(System.in);
    String password, passwordConfirmation;
    
    while (true) {
      System.out.print("Ingrese su nueva contraseña: ");
      password = in.nextLine();

      System.out.print("Ingrese la confirmación de su nueva contraseña: ");
      passwordConfirmation = in.nextLine();

      if (password.equals(passwordConfirmation)) {
        if (password.equals(Bank.passwords.get(userId))) {
          System.out.println("La nueva contraseña no puede ser igual a la anterior"); 
        } else {
          Bank.passwords.set(userId, password);
          Bank.logins.set(userId, (new Date()).toString());
          System.out.println("La nueva contraseña ha sido guardada exitosamente");

          break;
        }
      } else {
        System.out.println("Las contraseñas no coinciden");
      }
    }
  }

  /**
   * Customer main menu
   */

  public static void customerHome () {
    Scanner in = new Scanner(System.in);
    int userId = Bank.users.indexOf(Bank.username);

    boolean flag = true;
    Bank.optMenu = 0;

    while (flag) {
      Bank.printSpaces();
      if (Bank.logins.get(userId).equals("")) {
        System.out.println("Primer inicio de sesión, por favor realice un cambio de contraseña");

        Bank.changePassword(userId);

      } else {
        System.out.println("Menú principal");
        System.out.println("Para visualizar su balance de cuenta, ingrese 1");
        System.out.println("Para solicitar un crédito bancario, ingrese 2");
        System.out.println("Para realizar el pago de créditos bancarios, ingrese 3");
        System.out.println("Para realizar un cambio de contraseña, ingrese 4");
        System.out.println("Para finalizar, ingrese cualquier otro número");
        System.out.print("Ingrese una opción: ");

        in = new Scanner(System.in);

        Bank.optMenu = in.nextInt();

        switch(Bank.optMenu) {
          case 1:
            Bank.customerStatement(userId);
            break;

          case 2:
            Bank.requestCredit(userId);
            break;

          case 3:
            Bank.payCredits(userId);
            break;

          case 4:
            Bank.changePassword(userId);
            break;

          default:
            flag = false;
            break;
        }
      }
    };
  }

  /**
   * Login as Bank
   */
  public static boolean loginBank () {
    if (Bank.username.equals("admin") && Bank.password.equals("admin")) {
      System.out.println("Credenciales correctas");
      return true;
    } else {
      System.out.println("Credenciales incorrectas");
      return false;
    } 
  }

  /**
   * Bank main menu
   */
  public static void bankHome() {
    Scanner in;
    boolean exit = false;

    while (!exit) {
      in = new Scanner(System.in);
      double flag = 0;

      System.out.println("Menú de inicio");
      System.out.println("Para registrar un nuevo usuario, ingrese 1");
      System.out.println("Para ver la lista de clientes registrados, ingrese 2");
      System.out.println("Para registrar transacciones de los clientes, ingrese 3");
      System.out.println("Para salir del sistema, ingrese cualquier número");
      System.out.print("Ingrese una opción: ");
      Bank.optMenu = in.nextInt();

      switch (Bank.optMenu) {
        case 1:
          in = new Scanner(System.in);
          System.out.print("Ingrese el usuario del cliente: ");
          Bank.users.add(in.nextLine());

          System.out.print("Ingrese la contraseña inicial del cliente: ");
          Bank.passwords.add(in.nextLine());

          System.out.print("Ingrese el balance del cliente: ");
          flag = in.nextDouble();

          if (flag < 1500) {
            System.out.println("No puede registrarse el usuario, no posee el balance correcto");
            Bank.passwords.remove(Bank.users.size() - 1);
            Bank.users.remove(Bank.users.size() - 1);
            break;
          } else {
              
            ArrayList balance = new ArrayList();
            balance.add(flag);
            
            Bank.balance.put(
                    (Bank.users.size() - 1),
                    balance
            );
          }

          Bank.credits.add(0.0);
          Bank.baseCredits.add(0.0);
          Bank.payTo.add(-1);
          Bank.logins.add("");

          System.out.println("¡Usuario creado exitosamente!");
          Bank.await();
          exit = false;
          break;

        case 2:
          if (Bank.users.size() == 0) {
            System.out.println("Actualmente no hay usuarios registrados en el sistema");
          } else {
            System.out.printf("%20s | %10s | %20s\n", "Usuario", "Balance actual", "Último inicio de sesión");
            System.out.println("-----------------------------------------------------------------");
            for (int i = 0; i < Bank.users.size(); i++) {
              System.out.printf("%20s | $%10.2f    | %20s\n", Bank.users.get(i), Bank.calculateBalance(Bank.balance.get(i)), Bank.logins.get(i));
              System.out.println("-----------------------------------------------------------------");
            }
          }
          exit = false;
          break;

        case 3:
          String user;
          while (true) {
            in = new Scanner(System.in);
            System.out.print("Ingrese el nombre del usuario: ");
            user = in.nextLine();

            if (Bank.users.indexOf(user) != -1) {
              System.out.print("Ingrese el monto de la transacción (Acepta valores positivos y negativos): ");
              Bank.balance.get(Bank.users.indexOf(user)).add(in.nextDouble());
              System.out.println("Transacción registrada exitosamente");
              break;
            } else {
              System.out.println("El usuario ingresado no existe");
            }
          }
          break;

        default:
          System.out.println("Saliendo del sistema");
          exit = true;
          break;
      }
    }
  }
}
