package view;

import java.util.List;
import model.Pet;
import model.PetDAO;

public class PetTableModel extends GenericTableModel {

    public PetTableModel(List vDados) {
        super(vDados, new String[]{
            "Nome", "Raça", "Peso", "Idade", "sexo", "Cor da Pelagem", "Estado Reprodutivo", "Especie"
        });
    }
    
   
    @Override
   public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
        case 0: // Nome
            return String.class;
        case 1: // Raça
            return String.class;
        case 2: // Peso
            return Float.class; 
        case 3: // Idade
            return Integer.class;
        case 4: // Sexo
            return String.class;
        case 5: // Cor da Pelagem
            return String.class;
        case 6: // Estado Reprodutivo
            return String.class;
        case 7: // Espécie
            return String.class;
        default:
            throw new IndexOutOfBoundsException("columnIndex out of bounds");
    }
}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pet pet = (Pet) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0: return pet.getNome();
            case 1: return pet.getRaca();
            case 2: return pet.getPeso();
            case 3: return pet.getIdade();
            case 4: return pet.getSexo();
            case 5: return pet.getCorPelagem();
            case 6: return pet.getEstadoReprodutivo();
            case 7: return pet.getEspecie();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Pet pet = (Pet) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                pet.setNome((String) aValue);
                break;
            case 1:
                pet.setRaca((String) aValue);
                break;
            case 2:
                pet.setPeso((float) aValue);
                break;
            case 3:
                pet.setIdade((Integer) aValue);
                break;
            case 4:
                pet.setSexo((String) aValue);
                break;
            case 5:
                pet.setCorPelagem((String) aValue);
                break;
            case 6:
                pet.setEstadoReprodutivo((String) aValue);
                break;
            case 7:
                pet.setEspecie((String) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        // Atualiza o pet no banco de dados
        PetDAO.getInstance().update(pet);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
