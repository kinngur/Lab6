package aed.urgencias;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

  @Test
  public void testAdmitir() throws PacienteExisteException
  {
    Urgencias u = new UrgenciasAED();
    u.admitirPaciente("111", 5, 1);
    Paciente p = u.atenderPaciente(10);

    // Check expected DNI ("111") == observed DNI (p.getDNI())
    assertEquals("111", p.getDNI());
  }

  @Test
  public void testAtender() throws PacienteExisteException {
    Urgencias u = new UrgenciasAED();
    u.admitirPaciente("111", 1, 1);
    u.admitirPaciente("112", 1, 2);
    Paciente p = u.atenderPaciente(10);
    assertEquals("111", p.getDNI());
  }

  @Test
  public void testAteder2() throws PacienteExisteException {
    Urgencias u = new UrgenciasAED();
    u.admitirPaciente("111", 5, 1);
    u.admitirPaciente("112", 5, 2);
    Paciente p = u.atenderPaciente(10);
    assertEquals("111", p.getDNI());
    Paciente p2 = u.atenderPaciente(11);
    assertEquals("112", p2.getDNI());
  }

  @Test
  public void testAtender3() throws PacienteExisteException {
    Urgencias u = new UrgenciasAED();
    u.admitirPaciente("111", 5, 1);
    u.admitirPaciente("112", 1, 2);
    Paciente p = u.atenderPaciente(10);
    assertEquals("112", p.getDNI());
  }

  @Test
  public void testCuatro() throws PacienteExisteException, PacienteNoExisteException
  {
    Urgencias u = new UrgenciasAED();
    u.admitirPaciente("111", 4, 2);
    u.admitirPaciente("213", 4, 5);

    u.salirPaciente("111", 6);
    Paciente p = u.atenderPaciente(12);

    assertEquals("213",p.getDNI());
  }

  @Test
  public void testCinco() throws PacienteExisteException, PacienteNoExisteException
  {
    Urgencias u = new UrgenciasAED();
    u.admitirPaciente("333", 5, 1);
    u.admitirPaciente("444", 5, 5);

    u.cambiarPrioridad("444", 1, 7);
    Paciente p = u.atenderPaciente(10);

    assertEquals("444", p.getDNI());
  }
}

