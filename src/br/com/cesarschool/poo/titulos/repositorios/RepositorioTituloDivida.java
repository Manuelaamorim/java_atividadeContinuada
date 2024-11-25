package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.time.LocalDateTime;

public class RepositorioTituloDivida extends RepositorioGeral<TituloDivida> {

	public RepositorioTituloDivida() {
		super(TituloDivida.class);
	}

	@Override
	public Class<?> getClasseEntidade() {
		return TituloDivida.class;
	}

	public boolean incluir(TituloDivida tituloDivida) {
		if (tituloDivida == null) {
			return false;
		}
		tituloDivida.setDataHoraInclusao(LocalDateTime.now());
		return super.incluir(tituloDivida);
	}

	public boolean alterar(TituloDivida tituloDivida) {
		if (tituloDivida == null) {
			return false;
		}
		tituloDivida.setDataHoraUltimaAlteracao(LocalDateTime.now());
		return super.alterar(tituloDivida);
	}

	public boolean excluir(int identificador) {
		return super.excluir(String.valueOf(identificador));
	}

	public TituloDivida buscar(int identificador) {
		return super.buscar(String.valueOf(identificador));
	}
}
