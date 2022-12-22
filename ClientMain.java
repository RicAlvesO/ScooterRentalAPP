import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        UserAPI api;
        try{
            Socket socket = new Socket("localhost", ConfigGlobal.port);
            api = new UserAPI(socket);
            System.out.println("Conectado com o servidor");
        }catch(Exception e){
            System.err.println("Erro ao conectar com o servidor");
            return;
        }
        Menu menu = new Menu(api);
        boolean auth = menu.menuInicial();
        if(auth){
            menu.menu();
        }
    }
}
