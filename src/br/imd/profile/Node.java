package br.imd.profile;

import br.imd.filedata.Data;

public class Node {
	
	private Data data;
	
	public Node() {
		
	}
	
	public Node(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
