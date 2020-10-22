import java.util.Comparator;

public class ComparadorNomeArquivo implements Comparator<Arquivo>{

	@Override
	public int compare(Arquivo o1, Arquivo o2) {
		// TODO Auto-generated method stub
		return o1.getNome().compareTo(o2.getNome());
	}

}