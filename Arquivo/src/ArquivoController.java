
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
 * Código desenvolvido por Marcos Vinicius André Rocha
 * 
 * Classe controller dos arquivos e metodos
 * 
 */
public class ArquivoController {
	
	private String diretorio = "";
	private List<Arquivo> arquivos = new ArrayList<Arquivo>();
	private List<Arquivo> arquivosOrdenados = new ArrayList<Arquivo>();
	private Arquivo arquivoAberto ;
	private String comando = "";
	
	/*
	 * abre diretório, recebe o caminho por parametro  
	 */
	public void abrirDiretorio(String dir) {
		
		java.io.File diretorio = new java.io.File(dir);
		//verifica se existe o diretorio especificado
		if(diretorio.exists()) {
			//se existir passa para o atributo da classe o caminho do diretorio
			this.diretorio = diretorio.getPath();
			
		}else 
		{
			  throw new IllegalArgumentException("Diretório especificado não existe");
		}
		
	}
	
	@Deprecated
	public void abrirArquivo() {
		
	}
	
	/*
	 * essa metodo lista os arquivos referente ao diretorio especificado, (atributo diretorio)
	 * Listar arquivos do diretorio sem ordenação 
	 */
	public List<Arquivo> listaArquivos(){
		System.out.println();
		System.out.println("Diretório aberto: "+diretorio);
		System.out.println();
		comando = "lst";
		//verifica se o diretorio foi selecionado
		if(diretorio.isEmpty()) {
		
			throw new IllegalArgumentException("Selecione o diretorio para fazer a busca dos arquivos");
		}
		//lista passa para o vetor todos os arquivos do diretorio
		File file = new File(diretorio);
		
		this.arquivos = new ArrayList<>();
		
		File[] fille = file.listFiles(); 
		  
        if(fille != null){ 
            int length = fille.length; 
  
            for(int i = 0; i < length; ++i){ 
                File f = fille[i]; 
                Arquivo arquivo = new Arquivo();
				arquivo.setNome(f.getName());
				arquivo.setExtensao(Extensao.TXT);
				arquivo.setUltimaModificacao(new Date(f.lastModified()));
				
				arquivo.setArquivoFile(f);
				
                if(f.isFile()){ 
                  
                } 
                
                else if(f.isDirectory()){ 
  
                } 
              this.arquivos.add(arquivo);  
            } 
        }
		
		return arquivos;
	}
	/*
	 * esse metodo retorna a lista de arquivos referente a ordenação passada por parametro que no caso é um enumerador(constantes DATA ou NOME)
	 * caso for passado NOME irá retornar uma lista por ordem alfabetica pelo nome, mesma coisa com a data
	 * 
	 * Ordenações: 
	 * 
	 * Data: radix
	 * 
	 * Nome: buble
	 */
	public void listaArquivos(Ordenacao ordenacao){
	
		arquivosOrdenados = new ArrayList<Arquivo>();
		
		listaArquivos();
		
		//verifica se o diretorio foi selecionado
		if(this.arquivos.size() <= 0) {
			
		}else
		//verifica se a ordenação passada por parametro é por data
		if(ordenacao.equals(Ordenacao.DATA)) {
			
			comando = "ordd";
			//passa para um vetor de object todos os arquivos
			Object[] array2 = this.arquivos.toArray();
			//prepara um vetor de arquivos com o tamanho do vetor anterior
			Arquivo[] array3 = new Arquivo[array2.length];
			
			//vetor long com tamanho 10 que armazenara as datas em long
			long array[] = new long [10];
			
			for (int i = 1; i >= 0; i--) {
				
				//percorre todos os arquivos  
				for(int ar = 0; ar < array2.length; ar++) {
					//cria um objeto arquivo e recebe o objeto do vetor
					Arquivo arquivo = (Arquivo) array2[ar];
					//converte data long em String
					SimpleDateFormat format = new SimpleDateFormat("dd");
					
					String dataExtenso = format.format(arquivo.getUltimaModificacao());
					
					
					//pega o digito e passa para variavel inteiro 
					int valorDigito = 0;
					if(i < 1) {
						 valorDigito = Integer.parseInt(dataExtenso.substring(i,(i+1)));
					}else {
						 valorDigito = Integer.parseInt(dataExtenso.substring(i));
					}

					//incrementa no index que é o valor do digito da data
					array[valorDigito] = array[valorDigito] + 1 ;
					
					
				}
				//pega valores do array de digitos e soma com index anterior
				for (int c = 0; c < array.length; c++) {
					
					if( c  > 0 ) {
						array[c] = array[c-1] + array[c];
						//System.out.println("posição: "+c+": "+ array[c]);
					}else {
						//System.out.println("posição: "+c+": "+ array[c]);
						array[c] =  array[c];
					}
					
				}
				//percorre os arquivos de trás para frente e começa a ordenar no terceiro vetor de arquivo 
				for (int ar = array2.length-1; ar >= 0; ar--) {
					
					Arquivo arquivo = (Arquivo) array2[ar];
					SimpleDateFormat format = new SimpleDateFormat("dd");

					String dataExtenso = format.format(arquivo.getUltimaModificacao());
					int valorDigito = 0;

					
					if(i < 1) {
						 valorDigito = Integer.parseInt(dataExtenso.substring(i,(i+1)));						
					}else {
						 valorDigito = Integer.parseInt(dataExtenso.substring(i));
					}
					
					//System.out.println(valorDigito);
					
					long indexOrdenacao = 0;
					
					if(array[valorDigito] == 0) {
						
						array[valorDigito] = 0;
						
					}else {
						array[valorDigito] = array[valorDigito] - 1;
					}					
					
					indexOrdenacao = array[valorDigito]; 
					array3[(int) indexOrdenacao] = arquivo;
					
				}
				
				for (int j = 0; j < array3.length; j++) {
					array2[j] = array3[j];
				}
				
				array = new long[10];
			}
			//como a data tem 7 ou 8 digitos entao vamos percorrer todos os digitos de trás para frente
			for (int i = 4; i >= 0; i--) {
				
				//percorre todos os arquivos  
				for(int ar = 0; ar < array2.length; ar++) {
					//cria um objeto arquivo e recebe o objeto do vetor
					Arquivo arquivo = (Arquivo) array2[ar];
					//converte data long em String
					SimpleDateFormat format = new SimpleDateFormat("MMyyyy");
					
					String dataExtenso = format.format(arquivo.getUltimaModificacao());
					
					
					//pega o digito e passa para variavel inteiro 
					int valorDigito = 0;
					if(i < 7) {
						 valorDigito = Integer.parseInt(dataExtenso.substring(i,(i+1)));
					}else {
						 valorDigito = Integer.parseInt(dataExtenso.substring(i));
					}

					//incrementa no index que é o valor do digito da data
					array[valorDigito] = array[valorDigito] + 1 ;
					
				}
				//pega valores do array de digitos e soma com index anterior
				for (int c = 0; c < array.length; c++) {
					
					if( c  > 0 ) {
						
						array[c] = array[c-1] + array[c];
						//System.out.println("posição: "+c+": "+ array[c]);
					}else {
						//System.out.println("posição: "+c+": "+ array[c]);
						array[c] =  array[c];
					}
					
				}
				//percorre os arquivos de trás para frente e começa a ordenar no terceiro vetor de arquivo 
				for (int ar = array2.length-1; ar >= 0; ar--) {
					
					Arquivo arquivo = (Arquivo) array2[ar];
					SimpleDateFormat format = new SimpleDateFormat("MMyyyy");

					String dataExtenso = format.format(arquivo.getUltimaModificacao());
					int valorDigito = 0;

					
					if(i < 7) {
						 valorDigito = Integer.parseInt(dataExtenso.substring(i,(i+1)));						
					}else {
						 valorDigito = Integer.parseInt(dataExtenso.substring(i));
					}
					
					//System.out.println(valorDigito);
					
					long indexOrdenacao = 0;
					
					if(array[valorDigito] == 0) {
						
						array[valorDigito] = 0;
						
					}else {
						array[valorDigito] = array[valorDigito] - 1;
					}					
					
					indexOrdenacao = array[valorDigito]; 
					array3[(int) indexOrdenacao] = arquivo;
					
				}
				
				for (int j = 0; j < array3.length; j++) {
					array2[j] = array3[j];
				}
				
				array = new long[10];
			}
			
			for (int j = 0; j < array2.length; j++) {
				this.arquivosOrdenados.add((Arquivo) array2[j]);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			int contador = 0;
			for (Arquivo arquivo : this.arquivosOrdenados) {
				
				System.out.println("["+contador+"]"+ arquivo.getNome()+" - "+format.format(arquivo.getUltimaModificacao()));
				contador++;
			}
			
		}else
		if(ordenacao == Ordenacao.NOME)
		{
			
			comando = "ordn";
			
			for (int i = 0; i < this.arquivos.size(); i++) {

			    for (int j = this.arquivos.size() - 1; j > i; j--) {
			        if (this.arquivos.get(i).getNome().compareToIgnoreCase(this.arquivos.get(j).getNome()) > 0) {

			            Arquivo tmp = this.arquivos.get(i);
			            this.arquivos.set(i, this.arquivos.get(j));
			            this.arquivos.set(j, tmp);
			        	
			        }
			    }
			}
			
			for (Arquivo arquivo : this.arquivos) {
				this.arquivosOrdenados.add(arquivo);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			int contador = 0;
			for (Arquivo arquivo : this.arquivosOrdenados) {
				
				System.out.println("["+contador+"]"+ arquivo.getNome()+" - "+format.format(arquivo.getUltimaModificacao()));
				contador++;
			}
		}
	}
	
	/*
	 * esse metodo lista os arquivos de acordo com o nome passado por parametro, 
	 * é verificado percorrendo a lista dos arquivos e com um IF verificando quais arquivos contem o valor passado por parametro como nome
	 * 
	 */
	public void buscarNome(String nome){
	
			int contador = 0;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			for(Arquivo arquivo : this.arquivos) 
			{
				if(arquivo.getNome().contains(nome)) {
					
					
					contador++;
					System.out.println("["+contador+"]"+ arquivo.getNome()+" - "+format.format(arquivo.getUltimaModificacao()));
					
					
				}
				
			}
	}
	
	/*
	 * esse metodo faz uma busca pelo conteudo 
	 * ele lê todos os arquivos e joga para o atributo conteudo da Classe Arquivo e faz uma lista com todos
	 * 
	 * logo, faz a busca pela lista quais os arquivos que contem o conteudo igual o parametro passado
	 */
	public void buscarConteudo(String conteudo) throws IOException {
		
		List<Arquivo> arquivosConteudo = new ArrayList<Arquivo>();
		
		for (Arquivo arquivo : this.listaArquivos()) {
			
		
			String caminhoString = this.diretorio+"\\"+arquivo.getNome();
		
			BufferedReader br = new BufferedReader(new FileReader(caminhoString)); 
			StringBuilder sBuilder = new StringBuilder();
			
			while(br.ready()){ 
				String linha = br.readLine(); 
				sBuilder.append(linha); 
				sBuilder.append("\n");
			} 
			arquivo.setConteudo(sBuilder.toString());
			arquivosConteudo.add(arquivo);
			br.close();
		
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<Arquivo> listaArquivos = new  ArrayList<Arquivo>();
		int contador = 0;
		for (Arquivo arquivo : arquivosConteudo) {
			
			if (arquivo.getConteudo().contains(conteudo)) {
				
				listaArquivos.add(arquivo);
			}
		
		}
		
		if (listaArquivos.size() > 0) {
			for (Arquivo arquivo : listaArquivos) {		
					
					System.out.println("["+contador+"]"+ arquivo.getNome()+" - "+format.format(arquivo.getUltimaModificacao()));
					contador++;
			}
		}else {
			System.out.println("nenhum arquivo contem esse conteudo!");
		}
		
		
	}
	
	
	/*
	 * le o conteudo dos arquivos 
	 * 
	 */
	public String lerArquivo(String caminho) throws IOException {
		
		
		
		String caminhoString = this.diretorio+"\\"+caminho;
		
		BufferedReader br = new BufferedReader(new FileReader(caminhoString)); 
		StringBuilder sBuilder = new StringBuilder();
		
		while(br.ready()){ 
			String linha = br.readLine(); 
			sBuilder.append(linha); 
			sBuilder.append("\n");
		} 
		br.close();
		
		return sBuilder.toString();
	}
	/*
	 * abre o arquivo pelo indice de acordo com a ordenação feita, caso tenha feito
	 * 
	 */
	public void abrirIndice(int i) throws IOException {
		
		
		
		
		if (comando.equals("")) {
			
			comando = "lst";
			listaArquivos();
			
		}
		
		if(comando.equals("ordd")) {
			
			System.out.println(Ordenacao.DATA);
			try {
							
				Arquivo arquivo = this.arquivosOrdenados.get(i);
				this.arquivoAberto = arquivo;
				System.out.println(lerArquivo( arquivo.getNome()));
				
			} catch (NullPointerException e) {}
	
		}else
			
			if(comando.equals("ordn")) {
				System.out.println(Ordenacao.NOME);
				try {
					
					Arquivo arquivo = this.arquivosOrdenados.get(i);
					this.arquivoAberto = arquivo;
					System.out.println(lerArquivo( arquivo.getNome()));
					
				} catch (NullPointerException e) {}
				
			}else
				
				if(comando.equals("lst")) {
					try {
						 System.out.println("Lista");
						 Arquivo arquivo = this.arquivos.get(i);
						 this.arquivoAberto = arquivo;
						 System.out.println(lerArquivo( arquivo.getNome()));	

					} catch (NullPointerException e) {}
					
				}

	}
	
	
	
	public Arquivo getArquivoAberto() {
		return arquivoAberto;
	}
	
	
	/*
	 * edita arquivo
	 * 
	 */
	
	public void editarArquivo(String texto) throws IOException {
		String caminhoString = this.diretorio+"\\"+this.arquivoAberto;
		//BufferedReader br = new BufferedReader(new FileReader(caminhoString)); 
		BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoString));
		writer.write(texto);
		writer.newLine();

		//Criando o conteúdo do arquivo
		writer.flush();
		//Fechando conexão e escrita do arquivo.
		writer.close();
		
		
	}
	
	public void fecharArquivo() {
		
		this.arquivoAberto = null;
		
	}
	
	
	
}