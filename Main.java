public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        String nome = menu.menuInicial();
        menu.menu(nome); //Este menu só é chamado se o utilizador ter feito login no menuInicial
    
    }
}
