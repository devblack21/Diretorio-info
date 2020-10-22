import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Menu {

	public static void main(String[] args) throws IOException {
		
		 String diretorio = String.valueOf(JOptionPane.showInputDialog(null, "Digite o endereço do diretório:"));
		 
		ArquivoController arquivoController = new ArquivoController();
		
		
		arquivoController.abrirDiretorio(diretorio);
		
		
		System.out.println("Diretório aberto: "+diretorio);
		
		
		
		while (true) {
			menu();
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String comando = scanner.nextLine();
			switch (splitText(comando)) {
			case "dir":
				try {
					System.out.println("Digite o caminho do diretorio: ");
					String dir = scanner.nextLine();
					arquivoController.abrirDiretorio(dir);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			case "lst":
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					int contador = 0;
					for (Arquivo arquivo : arquivoController.listaArquivos()) {
						System.out.println("["+contador+"] "+arquivo.getNome()+" - "+format.format(arquivo.getUltimaModificacao()));
						contador++;
					}
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}
			
				
				break;
			case "lstord":
				try {
					System.out.println("Digite 'N' para ordenar por nome e 'D' por Data.");
					String vString = scanner.nextLine();

					if (vString.equals("D")) {
						arquivoController.listaArquivos(Ordenacao.DATA);
					} else
					if (vString.equals("N")) {
						arquivoController.listaArquivos(Ordenacao.NOME);
					}
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}

				break;
			case "bn":
				System.out.println("Digite o Nome a para fazer a busc: ");
				String nome = scanner.nextLine();
				arquivoController.buscarNome(nome);
				break;

			case "aa":
				System.out.println("Digite o index do arquivo a ser aberto: ");
				int index = scanner.nextInt();
				arquivoController.abrirIndice(index);
				break;
			case "bc":
				System.out.println("Digite o Conteudo a ser buscado: ");
				String conteudo = scanner.next();
				arquivoController.buscarConteudo(conteudo);
				break;
			case "ea":
				System.out.println("Editar Arquivo: ");
				System.out.println(arquivoController.lerArquivo(arquivoController.getArquivoAberto().getNome()));
				String textoString = scanner.next();
				arquivoController.editarArquivo(textoString);
				break;
			case "fa":
				arquivoController.fecharArquivo();
				break;
			default:
				break;
			}

		}

	}

	private static void menu() {

		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("\n====> Comandos <====");
		sBuilder.append("\n");
		sBuilder.append("\n");
		sBuilder.append("dir -> Configura um diretorio ");
		sBuilder.append("\n");
		sBuilder.append("lst -> Listar todos os arquivos ");
		sBuilder.append("\n");
		sBuilder.append("lst ord -> Ordena a lista 'D' para data ou 'N' para  nome");
		sBuilder.append("\n");
		sBuilder.append("bn -> Buscar por nome");
		sBuilder.append("\n");
		sBuilder.append("bc - Buscar por conteúdo");
		sBuilder.append("\n");
		sBuilder.append("aa -> Abri arquivo do indice em questão, segundo a lista exibida");
		sBuilder.append("\n");
		sBuilder.append("ea -> Editar o arquivo que está aberto");
		sBuilder.append("\n");
		sBuilder.append("fa -> fechar arquivo que está aberto");
		sBuilder.append("\n");

		System.out.println(sBuilder.toString());
	}

	private static String splitText(String texto) {
		String[] array = texto.split(" ");
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sBuilder.append(array[i]);
		}

		return sBuilder.toString();
	}

}
