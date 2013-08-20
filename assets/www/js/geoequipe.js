var AppConfig = {
    getUserId: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "getUserId", []);
    }
   ,setUserId: function (userId) {
      return cordova.exec(null, null, "AppConfig", "setUserId", [userId]);
    }
   ,getEnviaSinal: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "getEnviaSinal", []);
    }
   ,setEnviaSinalOn: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "setEnviaSinalOn", []);
    }
   ,setEnviaSinalOff: function (success, fail) {
      return cordova.exec(success, fail, "AppConfig", "setEnviaSinalOff", []);
    }
};

function login() {
  $.ajax({type: 'GET'
         ,url: 'http://geoequipe.aws.af.cm/login/' + $("#usuario").val() + '/' + $("#senha").val()
         ,dataType: 'json'
         ,success: function(data) {
                     if (data) {
                       if (data.erro && data.erro.length > 0) {
                         alert(data.erro);
                       } else {
                         if (data.id_usuario && data.id_usuario.length > 0) {
                           // save in local storage
                           AppConfig.setUserId(data.id_usuario);
                           // perform page transition 
                           $.mobile.changePage("lista_tarefas.html");
                         }
                       }
                     } else {
                       alert("Erro ao fazer login!");
                     }
          }
         ,error: function (xhr, ajaxOptions, thrownError) {
              alert("Erro ao fazer login: " + xhr.status + "\n" +
                     "Mensagem: " + xhr.statusText + "\n" +
                     "Resposta: " + xhr.responseText + "\n" + thrownError);
          }
      });
}

function logout() {
  AppConfig.setUserId("");
  $.mobile.changePage("index.html");
}

function trocaEnvioSinal() {
  
}

$(document).on("pageshow", "#configuracoes", function() {
  $("#enviaSinal").on("change", function() {
    if ($(this).val() == "on")
      AppConfig.setEnviaSinalOn(null, null);
    else
      AppConfig.setEnviaSinalOff(null, null);
  });
  AppConfig.getEnviaSinal(function(retorno) {
    $('#enviaSinal').val(retorno);
    $('#enviaSinal').slider('refresh');
  }, function(){});
});

$(document).on("pageshow", "#lista_tarefas", function() {
  $("#tarefas").html(fnc_tarefa()).trigger('create');
});

function fnc_tarefa() {
listatarefas = '';

$.ajax({type: 'GET'
         ,url: 'http://geoequipe.aws.af.cm/tarefa/consulta/' + AppConfig.getUserId()
         ,dataType: 'json'
		 ,async: false
         ,success: function(data) {
                     if (data) {
                       if (data.erro && data.erro.length > 0) {
                         alert(data.erro);
                       } else {
                         if (data.tarefas.length > 0) { 
						 
							
							//popula variavel com as tarefas
							  for (i=0; i<data.tarefas.length; i++) {
							  
								listatarefas += 
								'<div data-role="collapsible" data-collapsed="false">' +
									'<h3>' +
										data.tarefas[i].local +
									'</h3>' +
									'<font size="2px">' + data.tarefas[i].descricao + '</font>' +
									'<input type="hidden" name="id_tarefa" id="id_tarefa" value="' + data.tarefas[i].id_tarefa + '"' +
									'<br>' +
									'<textarea name="" id="apontamento" placeholder="Apontamento" cols="200">' + data.tarefas[i].apontamento+ '</textarea>' +
									'<div class="ui-grid-a">' +
										'<div class="ui-block-a">';
											if (!data.tarefas[i].apontamento) {
												listatarefas += '<input type="submit" data-inline="true" data-theme="b" data-icon="check" data-iconpos="left" value="Concluir" data-mini="true">';
											}
											listatarefas += 
										'</div>' +
										'<div class="ui-block-b">' +
											'<a href="geo:' + data.tarefas[i].coord.lat + ',' + data.tarefas[i].coord.lng + '">' +
												'<div style=" text-align:right">' +
													'<img style="width: 40px; height: 40px" src="img/google-maps-icone.png">' +
												'</div>' +
											'</a>' +
										'</div>' +
									'</div>' +
								'</div>';
							  }	
                         } else {
							alert('Não há tarefas hoje.');
						 }
                       }
                     } else {
                       alert("Não há dados!");
                     }
          }
         ,error: function (xhr, ajaxOptions, thrownError) {
              alert("Erro ao fazer consulta de tarefa: " + xhr.status + "\n" +
                     "Mensagem: " + xhr.statusText + "\n" +
                     "Resposta: " + xhr.responseText + "\n" + thrownError);
          }
      });
	  
	return listatarefas;
  
}