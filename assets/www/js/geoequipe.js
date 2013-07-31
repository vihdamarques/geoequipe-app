function set_sessao(){
	var login = $("input[id=textinput1]").val();
	localStorage.setItem('usuario',login);
};
		
function get_sessao(){
	alert(localStorage.getItem('usuario'));
};
		
		
function limpa_sessao(){
	localStorage.setItem('usuario',null);
};

function login(){
	set_sessao();
	window.location = "lista_tarefas.html";
};