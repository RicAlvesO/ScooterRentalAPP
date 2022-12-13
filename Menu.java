import java.security.Principal;
import java.security.cert.CRL;
import java.util.List;
import java.util.Scanner;

public class Menu 
{
    Scanner scanner = new Scanner(System.in);

    public String menuInicial() 
    {
        int res = -1;
        boolean ok = false;
        String username = "";
        String password = "";
        int w = -1;

        while((res != 1 || res != 2) && (ok == false))
        {
            clearWindow();
            System.out.println("------- Menu de login/registro --------");
            System.out.print("  Se você deseja se registar prima '1'\n  Se você deseja fazer login prima '2'\n  --> ");
            res = scanner.nextInt();
        
        switch(res)
        {
            case 1:
                clearWindow();
                System.out.println("---------------- Menu de registro -----------------\n\n");
                System.out.print("  Indique o username: ");
                username = scanner.next();
                System.out.print("  Indique o password: ");
                password = scanner.next();
                
                int id = register(username, password);

                System.out.println(("  \n\n << Você foi registado e o seu ID é: " + id + " >>\n\n"));
                System.out.println("---------------------------------------------------\n");
                break;

            case 2:
                clearWindow();
                System.out.println("---------------- Menu de login ----------------\n\n");
                System.out.print("  Indique o username: ");
                username = scanner.next();
                System.out.print("  Indique o password: ");
                password = scanner.next();

                ok = login(username, password);

                if(ok == false) System.out.println("  << Falha no login >>");
                else {
                    clearWindow();
                    System.out.println("  << Login efetuado com sucesso! Bem vindo " + username + "! >>\n");
                }  
                break;       
        }

        while(w != 0)
        {
            System.out.print("Prima a tecla '0' para continuar: ");
            w = scanner.nextInt();
        }

        }
        return username;
    }

    public void menu(String nome)
    {
        while(true)
        {
            clearWindow();
            StringBuilder sb = new StringBuilder("--------------------------------- MENU ---------------------------------\n\n");
            sb.append("  << Bem vindo " + nome + "! >>\n\n");
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
                    List <Pos> Lpos = get_available(p);

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
                    List <Reward> Lreward = check_reward(posR);

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

                    Reserve reserve = reserve_scooter(posS);

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

                    Price price = park_scooter(end, code);

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
                    boolean onoff = false;

                    System.out.print("  Digite 'T' se você deseja ativar as notificações\n  Digite 'F' se você deseja desativar as notificações\n    --> ");
                    String resB = "";
                    while (!(resB.equals("T") || resB.equals("F")))
                    {
                        resB = scanner.next();
                        if(resB.equals("T")) onoff = set_notification(true, desired);
                        else onoff = set_notification(false, desired);
                    }

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
                    Reward reward = receive_notifications();
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
                    System.exit(0);
                    break;
            }
            
        }
    }

    public static void clearWindow() 
    {
        for (int i = 0;i<100;i++)
        {
            System.out.println();
        }
    }
}
