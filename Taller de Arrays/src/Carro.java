
public class Carro
{
    private String placa;
    private int horaLlegada;

    public Carro(String pPlaca, int pHoraLlegada)
    {
        this.placa = pPlaca;
        this.horaLlegada = pHoraLlegada;
    }

    public String darPlaca()
    {
        return placa;
    }

    public int darHoraLlegada()
    {
        return horaLlegada;
    }

    public boolean tienePlaca(String pPlaca)
    {
        return placa.equalsIgnoreCase(pPlaca);
    }

    public int darTiempoEnParqueadero(int pHoraSalida)
    {
        return pHoraSalida - horaLlegada + 1;
    }
}