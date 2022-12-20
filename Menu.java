import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu 
{
    private Scanner scanner;
    private UserAPI api;
    //define a map that stores Reserves by user id
    private Map<Integer, Reserve> reserveList;

    public Menu(UserAPI api) {
        this.scanner = new Scanner(System.in);
        this.api = api;
        this.reserveList = new HashMap<>();
    }

    public boolean menuInicial() 
    {
        int res = -1;
        boolean ok = false;
        String username = "";
        String password = "";

        while((res != 1 || res != 2) && (ok == false) && api.isRunning())
        {
            clearWindow();
            System.out.println("------- Menu de login/registro --------");
            System.out.print("  1) Registar Utilizador\n  2) Realizar Login \n  0) Sair\n\n  --> ");
            while ( res>2 || res<0) {
                res = scanner.nextInt();
            }

            switch(res)
            {
                case 1:
                    clearWindow();
                    System.out.println("---------------- Menu de registro -----------------\n\n");
                    System.out.print("  Indique o username: ");
                    username = scanner.next();
                    System.out.print("  Indique o password: ");
                    password = scanner.next();

                    int id = this.api.register(username, password);

                    if (id!=-1){
                        System.out.println(("  \n\n << Você foi registado e o seu ID é: " + id + " >>\n\n"));
                        System.out.println("---------------------------------------------------\n");
                        ok=true;
                    }
                    res=-1;
                    break;

                case 2:
                    clearWindow();
                    System.out.println("---------------- Menu de login ----------------\n\n");
                    System.out.print("  Indique o username: ");
                    username = scanner.next();
                    System.out.print("  Indique o password: ");
                    password = scanner.next();

                    ok = this.api.login(username, password);

                    if(ok == false) {System.out.println("  << Falha no login >>"); res=-1;}
                    else {
                        clearWindow();
                        System.out.println("  << Login efetuado com sucesso! Bem vindo " + username + "! >>\n");
                    }  
                    break;       
                case 0:
                    try {
                        this.api.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
            }

            //press enter to continue
            System.out.println("  Prima enter para continuar...");
            scanner.nextLine();

        }
        return true;
    }

    public void menu()
    {
        while(true && api.isRunning())
        {
            clearWindow();
            StringBuilder sb = new StringBuilder("--------------------------------- MENU ---------------------------------\n\n");
            sb.append("  1) As posições válidas desde a sua posição inicial\n");
            sb.append("  2) Verificar as recompensas disponíveis desde a sua posição inicial.\n");
            sb.append("  3) Reservar uma trotinette elétrica.\n");
            sb.append("  4) Terminar a reserva da trotinette elétrica.\n");
            sb.append("  5) Ativar ou desativar as notificações.\n");
            sb.append("  6) Notificações recebidas.\n");
            sb.append("  0) Sair.\n\n");
            sb.append("  Selecione a opção pretendida: ");
            System.out.print(sb.toString());

            int res = -1;
            while(res < 0 || res > 6) 
            {
                res = scanner.nextInt();
            }

            int x;
            int y;
            int w = -1;

            switch(res)
            {
                case 1:
                    clearWindow();
                    System.out.println("---------------------------------------------\n");
                    System.out.println("  Indique as coordenadas da posição deseja:");
                    System.out.print("    x --> ");
                    x = scanner.nextInt();
                    System.out.print("    y --> ");
                    y = scanner.nextInt();
                    System.out.println("\n");

                    Pos p = new Pos(x, y);
                    List <Pos> Lpos = this.api.get_available(p);

                    for(Pos pos : Lpos) {
                        System.out.println("-> " + pos.toString());
                    }
                    System.out.println("---------------------------------------------\n");

                    while(w != 0)
                    {
                        System.out.print("Prima a tecla '0' para continuar: ");
                        w = scanner.nextInt();
                    }
                    
                    break;
                case 2:
                    clearWindow();
                    System.out.println("------------------------------------------\n");
                    System.out.println("  Indique as coordenadas da posição deseja:");
                    System.out.print("    x --> ");
                    x = scanner.nextInt();
                    System.out.print("    y --> ");
                    y = scanner.nextInt();
                    System.out.println("\n");

                    Pos posR = new Pos(x, y);
                    List <Reward> Lreward = this.api.check_rewards(posR);

                    for(Reward reward : Lreward) {
                        System.out.println("-> " + reward.toString());
                    }
                    System.out.println("------------------------------------------\n");

                    while(w != 0)
                    {
                        System.out.print("Prima a tecla '0' para continuar: ");
                        w = scanner.nextInt();
                    }

                    break;
                case 3:
                    clearWindow();
                    System.out.println("------------------------------------------\n");
                    System.out.println("  Indique as coordenadas da posição inicial:");
                    System.out.print("    x --> ");
                    x = scanner.nextInt();
                    System.out.print("    y --> ");
                    y = scanner.nextInt();
                    System.out.println("\n");

                    Pos posS = new Pos(x, y);

                    Reserve reserve = this.api.reserve_scooter(posS);
                    this.reserveList.put(reserve.getCode(), reserve);
                    System.out.println(reserve.toString());
                    System.out.println("------------------------------------------\n");

                    while(w != 0)
                    {
                        System.out.print("Prima a tecla '0' para continuar: ");
                        w = scanner.nextInt();
                    }

                    break;
                case 4:
                    clearWindow();
                    System.out.println("---------------------------------------------\n");
                    System.out.println("  Indique as coordenadas da sua posição final:");
                    System.out.print("    x --> ");
                    x = scanner.nextInt();
                    System.out.print("    y --> ");
                    y = scanner.nextInt();

                    Pos end = new Pos(x, y);

                    System.out.print(("  Indique o códico da reserva: "));
                    int code = scanner.nextInt();
                    Reserve resv = reserveList.get(code);
                    resv.setEnd(end);

                    Price price = this.api.park_scooter(resv);

                    System.out.println("O preço é: " + price.toString());
                    System.out.println("---------------------------------------------\n");

                    while(w != 0)
                    {
                        System.out.print("Prima a tecla '0' para continuar: ");
                        w = scanner.nextInt();
                    }

                    break;
                case 5:
                    clearWindow();
                    System.out.println("--------------------------------------------------------\n");
                    System.out.println("  Indique as coordenadas da posição desejada:");
                    System.out.print("    x --> ");
                    x = scanner.nextInt();
                    System.out.print("    y --> ");
                    y = scanner.nextInt();

                    Pos desired = new Pos(x, y);
                    boolean onoff = this.api.set_notifications(desired);

                    if(onoff == true) System.out.println("\n  As notificações da posiçåo (" + x + "," + y + ") estão ativadas!\n");
                    else System.out.println("\n  As notificações da posiçåo (" + x + "," + y + ") estão desativadas!\n");
                    System.out.println("--------------------------------------------------------\n");

                    while(w != 0)
                    {
                        System.out.print("Prima a tecla '0' para continuar: ");
                        w = scanner.nextInt();
                    }

                    break;
                case 6:
                    clearWindow();
                    System.out.println("--------------------------------------------\n");
                    Reward reward = this.api.receive_notifications();
                    System.out.println("-> " + reward.toString());
                    System.out.println("--------------------------------------------\n");

                   while(w != 0)
                   {
                       System.out.print("Prima a tecla '0' para continuar: ");
                       w = scanner.nextInt();
                   }

                   break;
                case 0:
                    scanner.close();
                    try {
                        this.api.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                    break;
            }
            
        }
    }

    public static void clearWindow() 
    {
        System.out.println("\033[H\033[2J");
    }
}
