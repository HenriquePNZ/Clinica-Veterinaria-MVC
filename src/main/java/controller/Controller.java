package controller;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.*;
import view.*;

public class Controller {
    private static Tutor tutorSelecionado = null;
    private static Pet petSelecionado = null;
    private static JTextField tutorSelecionadoTextField = null;
    private static JTextField petSelecionadoTextField = null;
    private static JTextField petEspecieTextField = null;
    

    public static void setTutorSelecionadoTextField(JTextField textField) {
        tutorSelecionadoTextField = textField;
    }

    public static void setPetSelecionadoTextField(JTextField textField) {
        petSelecionadoTextField = textField;
    }

    public static void setPetEspecieTextField(JTextField textField) {
        petEspecieTextField = textField;
    }
    
     public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }

    public static Tutor getTutorSelecionado() {
        return tutorSelecionado;
    }

    public static Pet getPetSelecionado() {
        return petSelecionado;
    }
    
    
    public static int criarTutor(String nome, String telefone, String email, String endereco) {
        return TutorDAO.getInstance().create(nome, telefone, email, endereco);
    }
    
    public static Pet criarPet(String nome, String raca, float peso, Integer idade, String sexo, String corPelagem, String estadoReprodutivo, String especie, int idTutor) {
        return PetDAO.getInstance().create(nome, raca, peso, idade, sexo, corPelagem, estadoReprodutivo, especie, idTutor);
    }

     public static Vacina criarVacina(String medicacao, Date dataVacinacao, String lote, Date datReforco) {
        return VacinaDAO.getInstance().create(medicacao, dataVacinacao, lote, datReforco, petSelecionado.getId());
    }
     
    public static Vet criarVeterinario(String nome, String telefone, String email, String especialidade, Time horarioInicio, Time horarioFim) {
        return VetDAO.getInstance().create(nome, telefone, email, especialidade, horarioInicio, horarioFim);
    }

    public static void setSelected(Object selected) {
        if (selected instanceof Tutor) {
            tutorSelecionado = (Tutor) selected;

            if (tutorSelecionadoTextField != null) {
                tutorSelecionadoTextField.setText(tutorSelecionado.getNome());
            }
        } else if (selected instanceof Pet) {
            petSelecionado = (Pet) selected;

            if (petSelecionadoTextField != null) {
                petSelecionadoTextField.setText(petSelecionado.getNome());
            }
            if (petEspecieTextField != null) {
                petEspecieTextField.setText(petSelecionado.getEspecie());
            }
        }
    }

    public static void atualizarTutorNaLinha(JTable table, int row, int column, Object novoValor) {
        GenericTableModel model = (GenericTableModel) table.getModel();
        
        Tutor tutor = (Tutor) model.getItem(row);

        switch (column) {
            case 0: 
                tutor.setNome((String) novoValor);
                break;
            case 1: 
                tutor.setTelefone((String) novoValor);
                break;
            case 2: 
                tutor.setEmail((String) novoValor);
                break;
            case 3: 
                tutor.setEndereco((String) novoValor);
                break;
        }

        TutorDAO.getInstance().update(tutor);
    }
    
    public static void BuscarClientes(JTable table, String nomeParecido) {
        if (table.getModel() instanceof TutorTableModel) {
            TutorTableModel model = (TutorTableModel) table.getModel();
            
            List<Tutor> tutores = TutorDAO.getInstance().retrieveBySimilarName(nomeParecido);

            model.addListOfItems(new ArrayList<>(tutores));
            model.fireTableDataChanged();
        }
    }
    
      public static void mostrarTutores(JTable table) {
        setTableModel(table, new TutorTableModel(TutorDAO.getInstance().retrieveAll()));
    }
    
    public static void excluirTutorNaLinha(int selectedRow, JTable table) {
        GenericTableModel model = (GenericTableModel) table.getModel();
        Tutor tutorSelecionado = (Tutor) model.getItem(selectedRow);
        
        if(tutorSelecionado != null){
            List<Pet> petsDoTutor = PetDAO.getInstance().retrieveByTutorId(tutorSelecionado.getId());
            
            for (Pet pet: petsDoTutor){
                VacinaDAO.getInstance().deleteByPetId(pet.getId());
                
                PetDAO.getInstance().delete(pet);
            }
            
            TutorDAO.getInstance().delete(tutorSelecionado);
            
            mostrarTutores(table);
        }
    }

    public static void atualizarPetNaLinha(JTable table, int row, int column, Object novoValor) {
        GenericTableModel model = (GenericTableModel) table.getModel();
        
        Pet pet = (Pet) model.getItem(row);
        
        switch(column){
            case 0:
                pet.setNome((String) novoValor);
                break;
            case 1:
                pet.setRaca((String) novoValor);
                break;
            case 2:
                pet.setPeso((Float) novoValor);
                break;
            case 3:
                pet.setIdade((int) novoValor);
                break;
            case 4:
                pet.setSexo((String) novoValor);
                break;
            case 5:
                pet.setCorPelagem((String) novoValor);
                break;
            case 6:
                pet.setEstadoReprodutivo((String) novoValor);
                break;
            case 7:
                pet.setEspecie((String) novoValor);
                break;
        }

        PetDAO.getInstance().update(pet);    
    }
    
     public static void mostrarPetsDoTutor(JTable petsTable) {
        if (tutorSelecionado != null) {
            List<Pet> pets = PetDAO.getInstance().retrieveByTutorId(tutorSelecionado.getId());
            setTableModel(petsTable, new PetTableModel(pets));
        } else {
            setTableModel(petsTable, new PetTableModel(new ArrayList<>()));
        }
    }
    
    
    public static Pet criarPetParaTutorSelecionado(String nome, String raca, float peso, Integer idade, String sexo, String corPelagem, String estadoReprodutivo, String especie) {
        if (tutorSelecionado == null) {
            throw new IllegalStateException("Nenhum tutor selecionado. Por favor, selecione um tutor antes de adicionar um pet.");
        }

        return PetDAO.getInstance().create(nome, raca, peso, idade, sexo, corPelagem, estadoReprodutivo, especie, tutorSelecionado.getId());
    }    
    
    public static void excluirPetNaLinha(int selectedRow, JTable table) {
    GenericTableModel model = (GenericTableModel) table.getModel();
    Pet petSelecionado = (Pet) model.getItem(selectedRow);
    
    if (petSelecionado != null) {
        VacinaDAO.getInstance().deleteByPetId(petSelecionado.getId());

        PetDAO.getInstance().delete(petSelecionado);
    }
    
    mostrarPetsDoTutor(table);
}

    
      public static void atualizarVacinaNaLinha(JTable table, int row, int column, Object novoValor) {
       GenericTableModel model = (GenericTableModel) table.getModel();
       
       Vacina vacina = (Vacina) model.getItem(row);
       
       switch(column){
            case 0:
                vacina.setMedicacao((String) novoValor);
                break;
            case 1:
                vacina.setDataVacinacao((Date) novoValor);
                break;
            case 2:
                vacina.setLote((String) novoValor);
                break;
            case 3:
                vacina.setDataReforco((Date) novoValor);
                break;
        }
        
        VacinaDAO.getInstance().update(vacina);    
      }
      
       public static void mostrarVacinasDoPet(JTable vacinasTable){
        if(petSelecionado != null){
            List<Vacina> vacinas = VacinaDAO.getInstance().retrieveAllByPetId(petSelecionado.getId());
            setTableModel(vacinasTable, new VacinaTableModel(vacinas));   
        } else{
            setTableModel(vacinasTable, new VacinaTableModel(new ArrayList()));
        }
    }
      
     public static void excluirVacinaNaLinha(int selectedRow, JTable table) {
        GenericTableModel model = (GenericTableModel) table.getModel();
        Vacina vacinaSelecionada = (Vacina) model.getItem(selectedRow);
        
        if (vacinaSelecionada != null) {
            VacinaDAO.getInstance().delete(vacinaSelecionada);
        
        }
        
        mostrarVacinasDoPet(table);
    }
    public static String agendarConsulta(String dataConsultaStr, String horarioStr, String categoria) {
    try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        java.util.Date parsedDate = dateFormat.parse(dataConsultaStr);
        Date dataConsulta = new Date(parsedDate.getTime());

        Time horarioConsulta = Time.valueOf(horarioStr);

        Pet petSelecionado = getPetSelecionado();
        if (petSelecionado == null) {
            return "Nenhum pet selecionado. Por favor, selecione um pet antes de agendar a consulta.";
        }

        Vet veterinario = VetDAO.getInstance().buscaVeterinarioDisponivel(categoria, horarioConsulta);
        if (veterinario == null) {
            return "Nenhum veterinário disponível com a especialidade desejada ou no horário selecionado.";
        }

        boolean consultaExiste = ConsultaDAO.getInstance().existeConsulta(dataConsulta, horarioConsulta, categoria, veterinario.getId());
        if (consultaExiste) {
            return "Já existe uma consulta marcada para essa data, horário, categoria e veterinário.";
        }

        Consulta novaConsulta = ConsultaDAO.getInstance().create(dataConsulta, horarioConsulta, categoria, veterinario.getId(), petSelecionado.getId());

        return "Consulta agendada com sucesso!\n\n"
                + "Veterinário: " + veterinario.getNome() + "\n"
                + "Categoria: " + categoria + "\n"
                + "Pet: " + petSelecionado.getNome();

    } catch (ParseException e) {
        return "Formato de data ou horário inválido. Por favor, insira a data no formato DD/MM/YYYY e o horário no formato HH:MM:SS.";
    } catch (IllegalArgumentException e) {
        return "Erro ao processar a data ou horário. Verifique as entradas e tente novamente.";
    }
}

   public static void mostrarConsultas(JTextArea jTextArea){
        Pet petSelecionado = getPetSelecionado();
        
        if(petSelecionado != null){
            List<Consulta> consultas = ConsultaDAO.getInstance().retrieveAllByPetId(petSelecionado.getId());
            StringBuilder consultasTexto = new StringBuilder();
            
            for (Consulta consulta : consultas){
                 Vet veterinario = VetDAO.getInstance().retrieveById(consulta.getIdVet());
                 String nomeVeterinario = (veterinario != null) ? veterinario.getNome() : "Desconhecido";
                 
                 consultasTexto.append("Data: ").append(consulta.getData())
                          .append(", Hora: ").append(consulta.getHorario())
                          .append(", Veterinário: ").append(nomeVeterinario)
                          .append(", Categoria: ").append(consulta.getCategoria())
                          .append("\n");

            }
            
            jTextArea.setText(consultasTexto.toString());
            
        } else{
            
            jTextArea.setText("Nenhum pet selecionado.");
        }
    }
}
