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

function fnc_login(){
	var login = $("input[id=textinput1]").val();
	var senha = $("input[id=textinput2]").val();
    if (login == "admin" && senha == "123") {
		window.location = "lista_tarefas.html";
	}else{
		alert('Login ou Senha Invalidos!');
	}
};