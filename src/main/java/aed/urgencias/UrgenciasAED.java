package aed.urgencias;

import es.upm.aedlib.*;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.priorityqueue.HeapPriorityQueue;
import es.upm.aedlib.priorityqueue.PriorityQueue;
import es.upm.aedlib.priorityqueue.SortedListPriorityQueue;
import es.upm.aedlib.tree.CompleteBinaryTree;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Iterator;

public class UrgenciasAED implements Urgencias {

    PriorityQueue<Paciente,Paciente> pacientesEsperando = new HeapPriorityQueue<>(Paciente::compareTo);
    Map<String, Paciente> pacientesPorDni = new HashTableMap<>();
    Integer tiempoDeEsperaAtendidos = 0;
    Integer numeroPacientesAtendidos = 0;

    @Override
    public Paciente admitirPaciente(String DNI, int prioridad, int hora) throws PacienteExisteException {
        if (pacientesPorDni.containsKey(DNI)) throw new PacienteExisteException();
        else {
            Paciente nuevoPaciente = new Paciente(DNI,prioridad,hora, hora);
            nuevoPaciente.setEnqueuedEntry(pacientesEsperando.enqueue(nuevoPaciente, nuevoPaciente));
            pacientesPorDni.put(DNI, nuevoPaciente);
            return nuevoPaciente;
        }
    }

    @Override
    public Paciente salirPaciente(String DNI, int hora) throws PacienteNoExisteException {
        if (!pacientesPorDni.containsKey(DNI)) throw new PacienteNoExisteException();
        else {
            Paciente paciente = pacientesPorDni.remove(DNI);
            pacientesEsperando.remove(paciente.getEnqueuedEntry());
            return paciente;
        }
    }

    @Override
    public Paciente cambiarPrioridad(String DNI, int nuevaPrioridad, int hora) throws PacienteNoExisteException {
        if (!pacientesPorDni.containsKey(DNI)) throw new PacienteNoExisteException();
        Paciente paciente = pacientesPorDni.get(DNI);
        if (nuevaPrioridad != paciente.getPrioridad()){
            pacientesEsperando.remove(paciente.getEnqueuedEntry());
            paciente.setPrioridad(nuevaPrioridad);
            paciente.setTiempoAdmisionEnPrioridad(hora);
            paciente.setEnqueuedEntry(pacientesEsperando.enqueue(paciente,paciente));
        }
            return paciente;
    }

    @Override
    public Paciente atenderPaciente(int hora) {
        if (pacientesEsperando.isEmpty()) return null;
        else {
            Paciente paciente = pacientesEsperando.dequeue().getValue();
            pacientesPorDni.remove(paciente.getDNI());
            numeroPacientesAtendidos++;
            tiempoDeEsperaAtendidos += (hora - paciente.getTiempoAdmision());
            return paciente;
        }
    }

    @Override
    public void aumentaPrioridad(int maxTiempoEspera, int hora) {
        for(Entry<Paciente, Paciente> entry : pacientesEsperando) {
            Paciente paciente = entry.getValue();
            int tiempoDeEsperaDePaciente = hora - paciente.getTiempoAdmisionEnPrioridad();

            if(tiempoDeEsperaDePaciente > maxTiempoEspera && paciente.getPrioridad() != 0) {
                pacientesEsperando.remove(paciente.getEnqueuedEntry());
                paciente.setPrioridad(paciente.getPrioridad() - 1);
                paciente.setTiempoAdmisionEnPrioridad(hora);
                paciente.setEnqueuedEntry(pacientesEsperando.enqueue(paciente,paciente));
            }
        }
    }


    @Override
    public Iterable<Paciente> pacientesEsperando() {
        PositionList<Paciente> iterableRespuesta = new NodePositionList<>();
        HeapPriorityQueue<Paciente, Paciente> copiaPacientesEsperando = new HeapPriorityQueue<>(Paciente::compareTo, this.pacientesEsperando);
        while(!copiaPacientesEsperando.isEmpty()) {
            iterableRespuesta.addLast(copiaPacientesEsperando.dequeue().getValue());
        }
        return iterableRespuesta;
    }


    @Override
    public Paciente getPaciente(String DNI) {
        if(pacientesPorDni.containsKey(DNI)) {
            return pacientesPorDni.get(DNI);
        } else {
            return null;
        }
    }

    @Override
    public Pair<Integer, Integer> informacionEspera() {
        if(numeroPacientesAtendidos == null) return null;
        return new Pair<>(tiempoDeEsperaAtendidos, numeroPacientesAtendidos);
    }
}
