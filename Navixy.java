
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.List;
import java.util.Scanner;

public class Navixy {

    private String hash = "20202020224444444455555";

    public static Boolean activo = true;

    public Navixy(String hash) {
        this.hash = "?hash=" + hash;

    }

    public String api(String method, String url, String parms) {
        String ruta = url + this.hash;

        if (!parms.isEmpty()) {
            ruta += "&" + parms;
        }

        String salida = "";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ruta))
                .header("Accept", "*/*")
                .header("User-Agent", "Yo mismo")
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            salida = response.body();
            //System.out.println(salida);

        } catch (IOException | InterruptedException ex) {
            System.out.println("Error: " + ex.getMessage());
            salida = "Error";
        }

        return salida;
    }

    public int parser_list(String data, String fecha, boolean deleted, boolean deleted2) {

        int count = 0;
        String salto = "";
        int salida = 0;
        int salidatemp = 0;

        Gson gson = new Gson();

        ApiResponse apiResponse = gson.fromJson(data, ApiResponse.class);

        System.out.println("");

        // Acceder a los elementos de la lista
        for (Elemento elem : apiResponse.getList()) {

            if (count < 2) {

                salto = "";
                count++;
            } else {
                salto = "\n";
                count = 0;
            }

            if (!fecha.isEmpty()) {
                if (elem.getLabel().contains(fecha)) {

                    if (deleted) {
                        if (deleted2) {

                            String isBorrado = api("GET", "https://api.yourDomain.com/v2/map_layer/delete", "id=" + elem.getId());

                            System.out.println(isBorrado);
                            System.out.print("         (GONE)=> " + elem.getLabel() + " " + salto);

                        } else {

                            salida++;

                            System.out.print("        (DELETE)=> " + elem.getLabel() + " " + salto);
                        }

                    } else {
                        salida++;
                        System.out.print("            " + elem.getLabel() + " " + salto);
                    }

                }
            } else {

                salida++;

                //System.out.print( "               ID: " + elem.getId() + ", Label: " + elem.getLabel()+" " +salto);
                System.out.print("              " + elem.getLabel() + " " + salto);
            }

            // if (elem.getLabel().contains("20.09.2024")) {
        }

        return salida;

    }

    public static void main(String[] args) {

        Navixy navixy = new Navixy("2225550055dddd5558877789");

        //System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        Scanner scanner3 = new Scanner(System.in);
        Scanner scanner4 = new Scanner(System.in);

        while (Navixy.activo) {

            System.out.println(" ");
            System.out.println("*******************************************");
            System.out.println("*");
            System.out.println("*     1- Listar Todos ");
            System.out.println("*     2- Buscar ");
            System.out.println("*     3- Eliminar Fecha 'dd.mm.YYYY' ");
            System.out.println("*     4- Exit ");
            System.out.println("*");
            System.out.println("*");
            System.out.println("*******************************************");

            System.out.println(" ");

            System.out.print("Elige un Numero :");

            // int numero = scanner.nextInt();
            String entrada = scanner.nextLine();

            try {
                int numero = Integer.parseInt(entrada);

                switch (numero) {
                    case 1:
                        //System.out.print("\033[H\033[2J");
                        String consultar_todo = navixy.api("GET", "https://api.yourDomain.com/v2/map_layer/list", "");
                        int count_todo = navixy.parser_list(consultar_todo, "", false, false);

                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println("|------------------------------------------------------|");
                        System.out.println("| R: " + count_todo + "                                     ");
                        System.out.println("                          TODO                         |");
                        System.out.println("|                                                      |");
                        System.out.println("|------------------------------------------------------|");

                        break;
                    case 2:
                        //System.out.print("\033[H\033[2J");
                        System.out.println("");
                        System.out.print("Ingresa La fecha 'dd.mm.YYYY' :");

                        String mydate = scanner2.nextLine();

                        String consultar_date = navixy.api("GET", "https://api.yourDomain.com/v2/map_layer/list", "");
                        int count_date = navixy.parser_list(consultar_date, mydate, false, false);

                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println("|------------------------------------------------------|");
                        System.out.println("| R: " + count_date + "                                     ");
                        System.out.println("                 Buscar: " + mydate + "                     ");
                        System.out.println("|                                                      |");
                        System.out.println("|------------------------------------------------------|");

                        break;
                    case 3:
                        //System.out.print("\033[H\033[2J");

                        System.out.println("");
                        System.out.print("Ingresa La fecha a Eliminar 'dd.mm.YYYY' :");

                        String deleteBydate = scanner3.nextLine();

                        String eliminarBy_date = navixy.api("GET", "https://api.yourDomain.com/v2/map_layer/list", "");
                        int count_erraser = navixy.parser_list(eliminarBy_date, deleteBydate, true, false);
                        System.out.println("\n");
                        System.out.println("\n");

                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println("<<<<<<<<<<<<<<<<<<< DANGER ZONE >>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        System.out.println("*                                                          *");
                        System.out.println(" R: " + count_erraser + "                                       ");
                        System.out.println("                 FECHA A ELIMINAR: " + deleteBydate + "         ");
                        System.out.println("*                                                          *");
                        System.out.println("*_________________*< DANGER ZONE >*________________________*");

                        System.out.println(" ");
                        System.out.println(" ");

                        System.out.print("      !Esta Seguro que desea BORRAR este listado? si / no :");
                        String borrado = scanner4.nextLine();

                        if (borrado.equalsIgnoreCase("si") || borrado.equalsIgnoreCase("s")) {
                            navixy.parser_list(eliminarBy_date, deleteBydate, true, true);

                            System.out.println(" ");
                            System.out.println(" ");

                            System.out.println("|------------------------------------------------------|");
                            System.out.println("| R: " + count_erraser + "                                     ");
                            System.out.println("|     <<<<<<<        ! Eliminado !         >>>>>>>     |");
                            System.out.println("|                                                      |");
                            System.out.println("|------------------------------------------------------|");

                        } else {
                            System.out.println(borrado);
                        }

                        break;
                    case 4:

                        Navixy.activo = false;
                        System.out.println("   !!  Hasta Luego !!");
                        scanner.close();

                        break;
                    case 5:
                        System.out.println("Elegiste cinco.");
                        break;
                    default:
                        System.out.println("Número no válido.");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println(" ");
                System.out.println(" ");

                System.out.println("|------------------------------------------------------|");
                System.out.println("| R:                                                   |");
                System.out.println("|        Opcion Invalida Intenta denuevo !             |");
                System.out.println("|                                                      |");
                System.out.println("|------------------------------------------------------|");
            }

            // Cerrar el scanner
        }

        // System.out.println(consultar);
    }

    // Clases para deserializar la respuesta JSON
    class ApiResponse {

        private List<Elemento> list;

        public List<Elemento> getList() {
            return list;
        }
    }

    class Elemento {

        private int id;
        private String label;

        public int getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }
    }

}
