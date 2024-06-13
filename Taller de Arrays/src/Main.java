import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Parqueadero parqueadero = new Parqueadero();

        while (true) {
            String[] opciones = {
                    "Ingresar un carro al parqueadero",
                    "Dar salida a un carro del parqueadero",
                    "Informar los ingresos del parqueadero",
                    "Consultar la cantidad de puestos disponibles",
                    "Avanzar el reloj del parqueadero",
                    "Cambiar la tarifa del parqueadero",
                    "Consultar carro con más tiempo parqueado",
                    "Consultar si hay carros de mas de ocho horas",
                    "Dar tiempo promedio",
                    "Consultar carros parqueados más de 3 horas",
                    "Consultar carros con placa que comienzan con PB",
                    "Consultar si hay carros parqueados por 24 horas",
                    "Desocupar parqueadero",
                    "Consultar Carros Parqueados",
                    "Salir"
            };
            String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione una opción", "Parqueadero", JOptionPane.DEFAULT_OPTION, null, opciones, opciones);

            if (seleccion.equals("Ingresar un carro al parqueadero")) {
                String placa = JOptionPane.showInputDialog("Ingrese la placa del carro");
                int resultado = parqueadero.entrarCarro(placa);
                if (resultado == Parqueadero.PARQUEADERO_CERRADO) {
                    JOptionPane.showMessageDialog(null, "El parqueadero está cerrado.");
                } else if (resultado == Parqueadero.CARRO_YA_EXISTE) {
                    JOptionPane.showMessageDialog(null, "El carro ya existe en el parqueadero.");
                } else if (resultado == Parqueadero.NO_HAY_PUESTO) {
                    JOptionPane.showMessageDialog(null, "No hay puestos disponibles.");
                } else {
                    JOptionPane.showMessageDialog(null, "Carro ingresado correctamente.");
                }
            } else if (seleccion.equals("Dar salida a un carro del parqueadero")) {
                String placaSalida = JOptionPane.showInputDialog("Ingrese la placa del carro");
                int pago = parqueadero.sacarCarro(placaSalida);
                if (pago == Parqueadero.PARQUEADERO_CERRADO) {
                    JOptionPane.showMessageDialog(null, "El parqueadero está cerrado.");
                } else if (pago == Parqueadero.CARRO_NO_EXISTE) {
                    JOptionPane.showMessageDialog(null, "El carro no existe en el parqueadero.");
                } else {
                    JOptionPane.showMessageDialog(null, "Carro salido correctamente. Pago: " + pago);
                }
            } else if (seleccion.equals("Informar los ingresos del parqueadero")) {
                JOptionPane.showMessageDialog(null, "Ingresos del parqueadero: " + parqueadero.darMontoCaja());
            } else if (seleccion.equals("Consultar la cantidad de puestos disponibles")) {
                JOptionPane.showMessageDialog(null, "Puestos disponibles: " + parqueadero.calcularPuestosLibres());
            } else if (seleccion.equals("Avanzar el reloj del parqueadero")) {
                parqueadero.avanzarHora();
                JOptionPane.showMessageDialog(null, "Hora actual: " + parqueadero.darHoraActual());
            } else if (seleccion.equals("Cambiar la tarifa del parqueadero")) {
                String tarifaStr = JOptionPane.showInputDialog("Ingrese la nueva tarifa");
                int tarifa = Integer.parseInt(tarifaStr);
                parqueadero.cambiarTarifa(tarifa);
                JOptionPane.showMessageDialog(null, "Tarifa cambiada correctamente.");
            } else if (seleccion.equals("Dar tiempo Promedio")) {

                JOptionPane.showMessageDialog(null, "Tiempo promedio: " + parqueadero.darTiempoPromedio());
            } else if (seleccion.equals("Consultar si hay carros de mas de ocho horas")) {
                boolean carroMasTiempo = parqueadero.hayCarroMasDeOchoHoras();
                if(carroMasTiempo) {
                    JOptionPane.showMessageDialog(null, "Hay carros que han estado más de 8 horas.");
                } else {
                    JOptionPane.showMessageDialog(null, "No hay carros que han estado más de 8 horas.");
                }
            } else if (seleccion.equals("Consultar carro con más tiempo parqueado")) {
                Carro carro = parqueadero.darCarroMasTiempoParqueado();
                if (carro != null) {
                    JOptionPane.showMessageDialog(null, "Carro con más tiempo parqueado: " + carro.darPlaca());
                } else {
                    JOptionPane.showMessageDialog(null, "No hay carros parqueados.");
                }
            } else if (seleccion.equals("Consultar carros parqueados más de 3 horas")) {
                ArrayList<Carro> carros = parqueadero.darCarrosMasDeTresHorasParqueados();
                if (carros.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay carros parqueados más de 3 horas.");
                } else {
                    String mensaje = "Carros parqueados más de 3 horas:\n";
                    for (Carro carro : carros) {
                        mensaje += carro.darPlaca() + "\n";
                    }
                    JOptionPane.showMessageDialog(null, mensaje);
                }
            } else if (seleccion.equals("Consultar carros con placa que comienzan con PB")) {
                int cantidad = parqueadero.contarCarrosQueComienzanConPlacaPB();
                JOptionPane.showMessageDialog(null, "Cantidad de carros con placa que comienzan con PB: " + cantidad);
            } else if (seleccion.equals("Consultar si hay carros parqueados por 24 horas")) {
                boolean hayCarro24Horas = parqueadero.hayCarroCon24Horas();
                if (hayCarro24Horas) {
                    JOptionPane.showMessageDialog(null, "Hay carros parqueados por 24 horas.");
                } else {
                    JOptionPane.showMessageDialog(null, "No hay carros parqueados por 24 horas.");
                }
            } else if (seleccion.equals("Desocupar parqueadero")) {
                int cantidad = parqueadero.desocuparParqueadero();
                JOptionPane.showMessageDialog(null, "Cantidad de carros sacados: " + cantidad);
            } else if (seleccion.equals("Consultar Carros Parqueados")) {
                JOptionPane.showMessageDialog(null, parqueadero.darCarrosParqueados());
            } else if (seleccion.equals("Salir")) {
                JOptionPane.showMessageDialog(null, "Saliendo...");
                return;
            }
        }
    }
}