package br.imd.profile;

import java.util.HashMap;

/**
 * Classe que contém somente um HashMap que guarda toda a estrutura dos perfis
 * de usuários e manterá essas informações durante todo o funcionamento do
 * programa
 */
public class Database {

	public static HashMap<String, UserProfile> users = new HashMap<>();
}
