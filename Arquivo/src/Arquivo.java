import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Arquivo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nome;
	private Date ultimaModificacao;
	private Extensao extensao;
	private File arquivoFile;
	private String conteudo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getUltimaModificacao() {
		return ultimaModificacao;
	}
	public void setUltimaModificacao(Date ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
	}
	public Extensao getExtensao() {
		return extensao;
	}
	public void setExtensao(Extensao extensao) {
		this.extensao = extensao;
	}
	
	public File getArquivoFile() {
		return arquivoFile;
	}
	
	public void setArquivoFile(File arquivoFile) {
		this.arquivoFile = arquivoFile;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}