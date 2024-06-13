import java.util.ArrayList;
public class Parqueadero {
    public static final int TAMANO = 40;
    public static final int NO_HAY_PUESTO = -1;
    public static final int PARQUEADERO_CERRADO = -2;
    public static final int CARRO_NO_EXISTE = -3;
    public static final int CARRO_YA_EXISTE = -4;
    public static final int HORA_INICIAL = 6;
    public static final int HORA_CIERRE = 20;
    public static final int TARIFA_INICIAL = 1200;
    private Puesto puestos[];

    private int tarifa;

    private int caja;

    private int horaActual;

    private boolean abierto;

    public Parqueadero() {
        horaActual = HORA_INICIAL;
        abierto = true;
        tarifa = TARIFA_INICIAL;
        caja = 0;
        puestos = new Puesto[TAMANO];
        for (int i = 0; i < TAMANO; i++) {
            puestos[i] = new Puesto(i);
        }
    }

    public String darPlacaCarro(int pPosicion) {
        String respuesta;
        if (estaOcupado(pPosicion)) {
            Carro carro = puestos[pPosicion].darCarro();
            respuesta = "Placa: " + carro.darPlaca();
        } else {
            respuesta = "No hay un carro en esta posición";
        }

        return respuesta;
    }

    public int entrarCarro(String pPlaca) {
        int resultado = 0;
        if (!abierto) {
            resultado = PARQUEADERO_CERRADO;
        } else {
            int numPuestoCarro = buscarPuestoCarro(pPlaca.toUpperCase());
            if (numPuestoCarro != CARRO_NO_EXISTE) {
                resultado = CARRO_YA_EXISTE;
            } else {
                resultado = buscarPuestoLibre();
                if (resultado != NO_HAY_PUESTO) {
                    Carro carroEntrando = new Carro(pPlaca, horaActual);
                    puestos[resultado].parquearCarro(carroEntrando);
                }
            }
        }
        return resultado;
    }

    public int sacarCarro(String pPlaca) {
        int resultado = 0;
        if (!abierto) {
            resultado = PARQUEADERO_CERRADO;
        } else {
            int numPuesto = buscarPuestoCarro(pPlaca.toUpperCase());
            if (numPuesto == CARRO_NO_EXISTE) {
                resultado = CARRO_NO_EXISTE;
            } else {
                Carro carro = puestos[numPuesto].darCarro();
                int nHoras = carro.darTiempoEnParqueadero(horaActual);
                int porPagar = nHoras * tarifa;
                caja = caja + porPagar;
                puestos[numPuesto].sacarCarro();
                resultado = porPagar;
            }
        }

        return resultado;
    }

    public int darMontoCaja() {
        return caja;
    }

    public int calcularPuestosLibres() {
        int puestosLibres = 0;
        for (Puesto puesto : puestos) {
            if (!puesto.estaOcupado()) {
                puestosLibres = puestosLibres + 1;
            }
        }
        return puestosLibres;
    }

    public void cambiarTarifa(int pTarifa) {
        tarifa = pTarifa;
    }

    private int buscarPuestoLibre() {
        int puesto = NO_HAY_PUESTO;
        for (int i = 0; i < TAMANO && puesto == NO_HAY_PUESTO; i++) {
            if (!puestos[i].estaOcupado()) {
                puesto = i;
            }
        }
        return puesto;
    }

    private int buscarPuestoCarro(String pPlaca) {
        int puesto = CARRO_NO_EXISTE;
        for (int i = 0; i < TAMANO && puesto == CARRO_NO_EXISTE; i++) {
            if (puestos[i].tieneCarroConPlaca(pPlaca)) {
                puesto = i;
            }
        }
        return puesto;
    }

    public void avanzarHora() {
        if (horaActual <= HORA_CIERRE) {
            horaActual = (horaActual + 1);
        }
        if (horaActual == HORA_CIERRE) {
            abierto = false;
        }
    }

    public int darHoraActual() {
        return horaActual;
    }

    public boolean estaAbierto() {
        return abierto;
    }

    public int darTarifa() {
        return tarifa;
    }

    public boolean estaOcupado(int pPuesto) {
        boolean ocupado = puestos[pPuesto].estaOcupado();
        return ocupado;
    }

    public double darTiempoPromedio() {
        int totalHoras = 0;
        int cantidadCarros = 0;
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                totalHoras += carro.darTiempoEnParqueadero(horaActual);
                cantidadCarros++;
            }
        }
        if (cantidadCarros == 0) {
            return 0;
        } else {
            return (double) totalHoras / cantidadCarros;
        }
    }

    public Carro darCarroMasTiempoParqueado() {
        Carro carroMasTiempo = null;
        int maxTiempo = 0;
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                int tiempo = carro.darTiempoEnParqueadero(horaActual);
                if (tiempo > maxTiempo) {
                    maxTiempo = tiempo;
                    carroMasTiempo = carro;
                }
            }
        }
        return carroMasTiempo;
    }

    public boolean hayCarroMasDeOchoHoras() {
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                if (carro.darTiempoEnParqueadero(horaActual) > 8) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Carro> darCarrosMasDeTresHorasParqueados() {
        ArrayList<Carro> carros = new ArrayList<Carro>();
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                if (carro.darTiempoEnParqueadero(horaActual) > 3) {
                    carros.add(carro);
                }
            }
        }
        return carros;
    }

    public boolean hayCarrosPlacaIgual() {
        for (int i = 0; i < puestos.length; i++) {
            for (int j = i + 1; j < puestos.length; j++) {
                if (puestos[i].estaOcupado() && puestos[j].estaOcupado()) {
                    Carro carro1 = puestos[i].darCarro();
                    Carro carro2 = puestos[j].darCarro();
                    if (carro1.tienePlaca(carro2.darPlaca())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int contarCarrosQueComienzanConPlacaPB() {
        int cantidad = 0;
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                if (carro.darPlaca().startsWith("PB")) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    public boolean hayCarroCon24Horas() {
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                if (carro.darTiempoEnParqueadero(horaActual) >= 24) {
                    return true;
                }
            }
        }
        return false;
    }

    public String darCarrosParqueados() {
        String listaCarros = "Carros parqueados:\n";
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                listaCarros += "Placa: " + carro.darPlaca() + ", Hora de llegada: " + carro.darHoraLlegada() + "\n";
            }
        }
        return listaCarros;
    }

    public String metodo1() {
        int cantidad = contarCarrosQueComienzanConPlacaPB();
        boolean hayCarro24Horas = hayCarroCon24Horas();
        if (hayCarro24Horas) {
            return "Cantidad de carros con placa PB: " + cantidad;
        } else {
            return "No hay carros con placa PB.";
        }
    }

    public int desocuparParqueadero() {
        int cantidad = 0;
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                puesto.sacarCarro();
                cantidad++;
            }
        }
        return cantidad;
    }

    public String metodo2() {
        int cantidad = desocuparParqueadero();
        return "Cantidad de carros sacados: " + cantidad;
    }
}
