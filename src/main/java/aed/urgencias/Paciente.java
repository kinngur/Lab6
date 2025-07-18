package aed.urgencias;

import es.upm.aedlib.Entry;

import java.util.Comparator;

/**
 * Un paciente.
 */
public class Paciente implements Comparable<Paciente> {

  // DNI
  private String DNI;
  // Prioridad
  private int prioridad;
  // Tiempo de admision en las urgencias
  private int tiempoAdmision;
  // Tiempo cuando entro en la prioridad
  private int tiempoAdmisionEnPrioridad;
  private Entry<Paciente, Paciente> enqueuedEntry;

  /**
   * Constructor.
   */
  public Paciente(String DNI, int prioridad, int tiempoAdmision, int tiempoAdmisionEnPrioridad) {
    this.DNI = DNI;
    this.prioridad = prioridad;
    this.tiempoAdmision = tiempoAdmision;
    this.tiempoAdmisionEnPrioridad = tiempoAdmisionEnPrioridad;
  }

  /**
   * Devuelve el dni.
   * @return el dni.
   */
  public String getDNI() {
    return DNI;
  }

  /**
   * Devuelve la prioridad.
   * @return la prioridad.
   */
  public int getPrioridad() {
    return prioridad;
  }

  /**
   * Devuelve el tiempo de admision.
   * @return el tiempo de admision.
   */
  public int getTiempoAdmision() {
    return tiempoAdmision;
  }

  /**
   * Devuelve el tiempo de admision en la prioridad actual.
   * @return el tiempo de admision en la prioridad actual.
   */
  public int getTiempoAdmisionEnPrioridad() {
    return tiempoAdmisionEnPrioridad;
  }

  /**
   * Asigna una prioridad nueva.
   * @return la prioridad antigua.
   */
  public int setPrioridad(int prioridadNuevo) {
    int oldPrioridad = prioridad;
    prioridad = prioridadNuevo;
    return oldPrioridad;
  }

  /**
   * Asigna un nuevo tiempo de admision en prioridad.
   * @return el tiempo de admision en prioridad antigua.
   */
  public int setTiempoAdmisionEnPrioridad(int tiempoNuevo) {
    int oldTiempo = tiempoAdmisionEnPrioridad;
    tiempoAdmisionEnPrioridad = tiempoNuevo;
    return oldTiempo;
  }

  @Override
  public String toString() {
     return "<\""+DNI.toString()+"\","+prioridad+","+tiempoAdmision+","+tiempoAdmisionEnPrioridad+">";
  }

  // ----------------------------------------------------------------------
  // Para terminar

  // Hay que definir compareTo:
  // (ve la descripcion en la guia)
  @Override
  public int compareTo(Paciente paciente) {
    int prioridad = 1;
    if (this.prioridad < paciente.prioridad) {
      prioridad = -1;
    } else if (paciente.prioridad == this.prioridad) {
      if (this.tiempoAdmisionEnPrioridad < paciente.tiempoAdmisionEnPrioridad) {
        prioridad = -1;
      } else if (paciente.tiempoAdmisionEnPrioridad == this.tiempoAdmisionEnPrioridad) {
        if (this.tiempoAdmision < paciente.tiempoAdmision) {
          prioridad = -1;
        }
      }
    }
    return prioridad;
  }

  // Hay que definir equals
  // Usad solo el DNI al comparar pacientes
  @Override
  public boolean equals(Object obj) {
    if (this.getClass() != obj.getClass()) return false;
    Paciente objPaciente = (Paciente) obj;
    return this.DNI.equals(objPaciente.DNI);
  }

  // Hay que definit hashCode
  // Usad solo el DNI al calcular el hashCode
  @Override
  public int hashCode() {
    return this.DNI.hashCode();
  }

  public Entry<Paciente, Paciente> getEnqueuedEntry() {
        return enqueuedEntry;
    }

    public void setEnqueuedEntry(Entry<Paciente, Paciente> enqueuedEntry) {
        this.enqueuedEntry = enqueuedEntry;
    }
}
