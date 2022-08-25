package com.ti2cc;

import java.util.Scanner;

public class Principal {
	
	public static Scanner sc = new Scanner(System.in);  
	
	/**
	 * Metodo para adicionar um novo usuario ao banco de dados
	 * 
	 * @param dao Banco a ser listado
	 */
	public static void adicionar(DAO dao) {
		// Recuperar dados para calcular o novo ID
		Usuario[] usuarios = dao.getUsuarios();
		int novoID = 0;
		if (usuarios != null) {
			novoID = usuarios[usuarios.length-1].getCodigo() + 1;
		}
		else {
			novoID = 1;
		}

		// Gerar novo usuario
		Usuario novoUsuario = new Usuario();
		novoUsuario.setCodigo(novoID);

		System.out.print("Digite o Login:\n->");
		String login;
		login = sc.nextLine();
		novoUsuario.setLogin(login);
		
		System.out.print("Digite a Senha:\n->");
		String senha = sc.nextLine();
		novoUsuario.setSenha(senha);
		
		System.out.print("Digite o Sexo (sera utilizado apenas o primeiro caractere digitado):\n->");
		char sexo = sc.nextLine().charAt(0);
		novoUsuario.setSexo(sexo);
		
		if (dao.inserirUsuario(novoUsuario)) {
			System.out.println("Usuario inserido com sucesso");
		}
	}

	/**
	 * Metodo para mostrar todos os usuarios presentes
	 * dentro do Banco de Dados.
	 * 
	 * @param dao Banco a ser listado
	 */
	public static void listar(DAO dao) {
		// Recuperar usuarios do BD
		Usuario[] usuarios = dao.getUsuarios();
		
		// Mostrar usuarios
		System.out.println("==== Mostrar usuários ==== ");		
		for(int i = 0; i < usuarios.length; i++) {
			System.out.println(usuarios[i].toString());
		}
	}

	/**
	 * Metodo de atualizacao de dado de usuario
	 * ja inserido, a modificacao e feita a partir
	 * do seu ID.
	 * 
	 * @param dao Banco a ser alterado
	 */
	public static void atualizar(DAO dao) {
		// Recuperar dados do usuario
		System.out.println("Digite o codigo do usuario a atualizar:");
		System.out.print("\n->");
		int codigo = sc.nextInt();
		Usuario[] usuarios = dao.getUsuarios();
		Usuario novo = new Usuario();
		for(int i = 0; i < usuarios.length; i++) {
			if(usuarios[i].getCodigo() == codigo) {
				novo.setCodigo(usuarios[i].getCodigo()); 
				novo.setLogin(usuarios[i].getLogin());
				novo.setSenha(usuarios[i].getSenha());
				novo.setSexo(usuarios[i].getSexo());
			}
		}

		// Modificar os atributos do usuario
		int opcao = 2;
		while (opcao < 1 || opcao > 3) {
			System.out.println("Digite o tipo de dado a atualizar:");
			System.out.println("1 - Login");
			System.out.println("2 - Senha");
			System.out.println("3 - Sexo");
			System.out.println("4 - Sair");
			System.out.println("\n->");
			opcao = sc.nextInt();
			switch (opcao) {
			case 1:
				System.out.println("Digite o novo Login:\n->");
				String login = sc.nextLine();
				novo.setLogin(login);
				break;
			case 2:
				System.out.println("Digite a nova Senha:\n->");
				String senha = sc.nextLine();
				novo.setSenha(senha);
				break;
			case 3:
				System.out.println("Digite o novo Sexo (Sera salvo apenas o primeiro caractere digitado):\n->");
				String sexo = sc.nextLine();
				novo.setSexo(sexo.charAt(0));
				break;
			case 4:
				System.out.println("Salvando . . .");
				break;
			default:
				System.out.println("Opcao Invalida, escolha outro valor.\n\n");
			}
		}

		// Salvar atributos atualizados
		dao.atualizarUsuario(novo);
		System.out.println("Usuario cadastrado com sucesso.\n\n");
	}

	/**
	 * Metodo de remocao de usuario do banco de dados,
	 * a remocao e feita pelo codigo/chave primaria
	 * 
	 * @param dao Banco a ser alterado
	 */
	public static void remover(DAO dao) {
		System.out.println("Digite o codigo do usuario a remover:\n->");
		String opcao = sc.nextLine();
		if (dao.excluirUsuario(Integer.parseInt(opcao))) {
			System.out.println("Usuario removido com sucesso");
		}
	}

	// Driver Method
	public static void main(String[] args) {
		// Conectar ao DAO - Data Access Object
		DAO dao = new DAO();
		dao.conectar();

		// Escolher a operacao de CRUD
		int opcao = -1;
		System.out.println("Seja bem vindo ao meu SGBD!");
		while(opcao != 5) {
			System.out.println("\nEscolha a opcao:");
			System.out.println("1 - Adicionar");
			System.out.println("2 - Listar");
			System.out.println("3 - Atualizar");
			System.out.println("4 - Remover");
			System.out.println("5 - Sair");
			System.out.print("\n->");
			opcao = Character.getNumericValue(sc.nextLine().charAt(0));
			switch (opcao) {
				case 1:
					adicionar(dao);
					break;
				case 2:
					listar(dao);
					break;
				case 3:
					atualizar(dao);
					break;
				case 4:
					remover(dao);
					break;
				case 5:
					System.out.println("\nObrigado por usar a minha aplicacao\n\nMade by: Marcos Lommez - 77157");
					break;
				case -1:
					break;
				default:
					System.out.println("Opcao Invalida, escolha outro valor.\n\n");
			}
		}
		dao.close();
	}
}
