package com.SistemaDeMatricula.SistemaDeMatricula.Service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaDeMatricula.SistemaDeMatricula.Util.Util;
import com.SistemaDeMatricula.SistemaDeMatricula.models.Aluno;
import com.SistemaDeMatricula.SistemaDeMatricula.repository.AlunoRepository;

@Service
public class AlunoService {
	
	@Autowired
	AlunoRepository alunoRepository;
	@Autowired
	Util util;
	
	public Aluno add(Aluno aluno) throws Exception {
		String senhaS = aluno.getSenha();
		boolean verifica = existe(aluno.getEmail(),aluno.getMatricula());
		aluno.setSenha(util.criptografar(senhaS));
		if(util.validaEmailAluno(aluno.getEmail())
				&& util.validaNomeAluno(aluno.getNome()) 
				&& !verifica) {
			
			return alunoRepository.save(aluno);
			
		}
		return null;
	}
	
	public Collection<Aluno> buscarTodos(){
		return alunoRepository.findAll();
	}
	
	public boolean Login(String email,String senhaInformada) throws Exception{
		Aluno resulAluno = buscaEmail(email);
		String senhaAluno = resulAluno.getSenha();
		if(!util.verificaSenha(senhaAluno, senhaInformada)) {
			throw new Exception("Login ou senha invalido");
		}
		return true;
		
	}
	public Aluno BuscaId(Long matricula) throws Exception {
		Optional<Aluno> optAluno = alunoRepository.findById(matricula);
		if(!optAluno.isPresent()) {
			throw new Exception("ERROR!!");
		}
		Aluno aluno = optAluno.get();
		
		return aluno;
	}
	public Aluno buscaEmail(String email) {
		Collection<Aluno> alunos = alunoRepository.findAll();
		for (Aluno aluno : alunos) {
			if(aluno.getEmail().equalsIgnoreCase(email)) {
				return aluno;
			}
		}
		return null;
		
	}
	public boolean existe(String email ,Long matricula) throws Exception{
		Collection<Aluno> alunos = alunoRepository.findAll();
		boolean result = false;
		for (Aluno aluno : alunos) {
			if(aluno.getEmail().equalsIgnoreCase(email) || aluno.getMatricula().equals(matricula) ) {
				result = true;
			}
		}
		return result;
		
	}
	public Aluno update(Aluno aluno, Long matricula) throws Exception {
		Optional<Aluno> optAluno = alunoRepository.findById(matricula);
		if (!optAluno.isPresent()) {
			throw new Exception("Aluno don't exists");
		}
		Aluno newAluno = optAluno.get();
		newAluno.setNome(aluno.getNome());
		alunoRepository.save(newAluno);
		
		return newAluno;
	}
	public Aluno AddDisciplina(String disciplina, Long matricula) throws Exception {
		Optional<Aluno> optAluno = alunoRepository.findById(matricula);
		if (!optAluno.isPresent()) {
			throw new Exception("Aluno não existe");
		}
		Aluno newAluno = optAluno.get();
		newAluno.getDisciplinasPreMatriculadas().add(disciplina);
		alunoRepository.save(newAluno);
		
		return newAluno;
		
	}
	
	public Aluno excluir(Long matricula) throws Exception{
		Optional<Aluno> optAluno = alunoRepository.findById(matricula);
		if(!optAluno.isPresent()) {
			throw new Exception("ERROR!!");
		}
		Aluno aluno = optAluno.get();
		alunoRepository.delete(aluno);
		
		return aluno;
	}
	
}